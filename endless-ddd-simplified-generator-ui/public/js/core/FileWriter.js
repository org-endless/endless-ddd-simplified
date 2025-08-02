/**
 * FileWriter.js
 * 文件写入管理器
 * 处理项目创建后的文件写入逻辑
 */

class FileWriter {
    constructor() {
        this.supportedAPIs = this.checkSupportedAPIs();
    }

    /**
     * 检查浏览器支持的文件系统API
     */
    checkSupportedAPIs() {
        return {
            fileSystemAccess: 'showDirectoryPicker' in window,
            fileSystem: 'FileSystemWritableFileStream' in window,
            download: 'download' in document.createElement('a')
        };
    }

    /**
     * 写入项目文件
     * @param {Object} projectResponse - 项目创建响应
     * @param {string} projectResponse.rootPath - 项目根路径
     * @param {string} projectResponse.packageName - 项目包名
     * @param {Array} projectResponse.files - 文件列表
     */
    async writeProjectFiles(projectResponse) {
        try {
            console.log('开始写入项目文件:', projectResponse);
            console.log('响应类型:', typeof projectResponse);
            console.log('响应是否为数组:', Array.isArray(projectResponse));
            if (projectResponse && typeof projectResponse === 'object') {
                console.log('响应键:', Object.keys(projectResponse));
            }
            
            // 处理不同的响应格式
            let files = [];
            let rootPath = '';
            
            if (projectResponse && Array.isArray(projectResponse)) {
                // 如果响应直接是文件数组
                console.log('检测到文件数组格式');
                files = projectResponse;
                // 从第一个文件的rootPath推断项目根路径
                if (files.length > 0 && files[0].rootPath) {
                    const pathParts = files[0].rootPath.split(/[\\/]/);
                    rootPath = pathParts.slice(0, -1).join('/');
                }
            } else if (projectResponse && projectResponse.files && Array.isArray(projectResponse.files)) {
                // 标准格式：{rootPath: "...", files: [...]}
                console.log('检测到标准格式');
                files = projectResponse.files;
                rootPath = projectResponse.rootPath || '';
            } else if (projectResponse && projectResponse.fileList && Array.isArray(projectResponse.fileList)) {
                // 可能的另一种格式
                console.log('检测到fileList格式');
                files = projectResponse.fileList;
                rootPath = projectResponse.rootPath || '';
            } else if (projectResponse && projectResponse.data && projectResponse.data.files && Array.isArray(projectResponse.data.files)) {
                // 嵌套格式：{data: {rootPath: "...", files: [...]}}
                console.log('检测到嵌套data格式');
                files = projectResponse.data.files;
                rootPath = projectResponse.data.rootPath || '';
            } else {
                console.error('无法识别的响应格式:', projectResponse);
                throw new Error('项目响应数据无效或没有文件需要写入');
            }
            
            if (files.length === 0) {
                throw new Error('没有文件需要写入');
            }

            // 获取项目根目录句柄
            const rootDirHandle = await this.getProjectRootDirectory(rootPath);
            
            // 写入所有文件
            const writePromises = files.map(file => 
                this.writeFile(rootDirHandle, file)
            );
            
            await Promise.all(writePromises);
            
            console.log('所有文件写入完成');
            return true;
            
        } catch (error) {
            console.error('写入项目文件失败:', error);
            throw error;
        }
    }

    /**
     * 获取项目根目录句柄
     * @param {string} rootPath - 项目根路径
     */
    async getProjectRootDirectory(rootPath) {
        if (this.supportedAPIs.fileSystemAccess) {
            // 使用现代File System Access API
            return await this.getDirectoryHandleByPath(rootPath);
        } else {
            // 降级方案：提示用户手动选择目录
            throw new Error('当前浏览器不支持文件系统访问，请手动选择项目目录');
        }
    }

    /**
     * 通过路径获取目录句柄
     * @param {string} path - 目录路径
     */
    async getDirectoryHandleByPath(path) {
        try {
            // 提示用户选择项目根目录
            const dirHandle = await window.showDirectoryPicker({
                mode: 'readwrite',
                startIn: 'desktop'
            });
            
            // 验证选择的目录是否匹配项目路径
            if (dirHandle.name !== this.getDirectoryNameFromPath(path)) {
                console.warn('选择的目录名称与项目路径不匹配，但继续执行');
            }
            
            return dirHandle;
        } catch (error) {
            if (error.name === 'AbortError') {
                throw new Error('用户取消了目录选择');
            }
            throw error;
        }
    }

    /**
     * 从路径中提取目录名称
     * @param {string} path - 完整路径
     */
    getDirectoryNameFromPath(path) {
        const pathParts = path.split(/[\\/]/);
        return pathParts[pathParts.length - 1] || pathParts[pathParts.length - 2] || 'project';
    }

    /**
     * 写入单个文件
     * @param {FileSystemDirectoryHandle} rootDirHandle - 根目录句柄
     * @param {Object} fileInfo - 文件信息
     */
    async writeFile(rootDirHandle, fileInfo) {
        try {
            console.log('写入文件:', fileInfo.fileName, '到路径:', fileInfo.rootPath);
            
            // 创建目录结构
            const targetDirHandle = await this.createDirectoryStructure(rootDirHandle, fileInfo.rootPath);
            
            // 创建文件
            const fileHandle = await targetDirHandle.getFileHandle(fileInfo.fileName, { create: true });
            
            // 写入文件内容
            const writable = await fileHandle.createWritable();
            await writable.write(fileInfo.content);
            await writable.close();
            
            console.log('文件写入成功:', fileInfo.fileName);
            return true;
            
        } catch (error) {
            console.error('写入文件失败:', fileInfo.fileName, error);
            throw new Error(`写入文件 ${fileInfo.fileName} 失败: ${error.message}`);
        }
    }

    /**
     * 创建目录结构
     * @param {FileSystemDirectoryHandle} rootDirHandle - 根目录句柄
     * @param {string} targetPath - 目标路径
     */
    async createDirectoryStructure(rootDirHandle, targetPath) {
        try {
            // 解析路径
            const pathParts = targetPath.split(/[\\/]/).filter(part => part.trim());
            
            let currentDirHandle = rootDirHandle;
            
            // 逐级创建目录
            for (const dirName of pathParts) {
                try {
                    currentDirHandle = await currentDirHandle.getDirectoryHandle(dirName, { create: true });
                } catch (error) {
                    console.error('创建目录失败:', dirName, error);
                    throw new Error(`创建目录 ${dirName} 失败: ${error.message}`);
                }
            }
            
            return currentDirHandle;
            
        } catch (error) {
            console.error('创建目录结构失败:', error);
            throw error;
        }
    }

    /**
     * 降级方案：生成文件下载
     * @param {Object} projectResponse - 项目创建响应
     */
    downloadProjectFiles(projectResponse) {
        try {
            console.log('开始下载项目文件:', projectResponse);
            
            // 处理不同的响应格式
            let files = [];
            
            if (projectResponse && Array.isArray(projectResponse)) {
                // 如果响应直接是文件数组
                files = projectResponse;
            } else if (projectResponse && projectResponse.files && Array.isArray(projectResponse.files)) {
                // 标准格式：{rootPath: "...", files: [...]}
                files = projectResponse.files;
            } else if (projectResponse && projectResponse.fileList && Array.isArray(projectResponse.fileList)) {
                // 可能的另一种格式
                files = projectResponse.fileList;
            } else {
                throw new Error('项目响应数据无效或没有文件需要下载');
            }
            
            if (files.length === 0) {
                throw new Error('没有文件需要下载');
            }
            
            // 下载所有文件
            files.forEach((file, index) => {
                this.downloadFile(file, index);
            });
            
            console.log('所有文件下载完成');
            return true;
            
        } catch (error) {
            console.error('下载项目文件失败:', error);
            throw error;
        }
    }

    /**
     * 下载单个文件
     * @param {Object} fileInfo - 文件信息
     * @param {number} index - 文件索引
     */
    downloadFile(fileInfo, index) {
        try {
            // 创建Blob对象
            const blob = new Blob([fileInfo.content], { type: 'text/plain;charset=utf-8' });
            
            // 创建下载链接
            const url = URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = fileInfo.fileName;
            
            // 触发下载
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            
            // 清理URL对象
            setTimeout(() => URL.revokeObjectURL(url), 100);
            
            console.log('文件下载成功:', fileInfo.fileName);
            
        } catch (error) {
            console.error('下载文件失败:', fileInfo.fileName, error);
            throw new Error(`下载文件 ${fileInfo.fileName} 失败: ${error.message}`);
        }
    }

    /**
     * 检查文件写入权限
     */
    async checkWritePermission(dirHandle) {
        try {
            const permission = await dirHandle.queryPermission({ mode: 'readwrite' });
            if (permission === 'granted') {
                return true;
            }
            
            // 请求权限
            const newPermission = await dirHandle.requestPermission({ mode: 'readwrite' });
            return newPermission === 'granted';
            
        } catch (error) {
            console.error('检查写入权限失败:', error);
            return false;
        }
    }

    /**
     * 获取支持的功能信息
     */
    getSupportedFeatures() {
        return {
            ...this.supportedAPIs,
            description: this.getFeatureDescription()
        };
    }

    /**
     * 获取功能描述
     */
    getFeatureDescription() {
        if (this.supportedAPIs.fileSystemAccess && this.supportedAPIs.fileSystem) {
            return '支持直接写入文件系统';
        } else if (this.supportedAPIs.download) {
            return '支持文件下载';
        } else {
            return '不支持文件操作';
        }
    }
}

// 导出到全局
window.FileWriter = FileWriter; 