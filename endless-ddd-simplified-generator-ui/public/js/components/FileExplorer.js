/**
 * 文件浏览器组件
 * 支持选择本地文件夹并读取文件结构
 */
class FileExplorer {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.storageManager = new StorageManager();
        this.fileTree = [];
        this.currentPath = '';
        
        this.init();
    }

    /**
     * 初始化文件浏览器
     */
    init() {
        this.render();
        this.bindEvents();
        this.loadSavedProject();
    }

    /**
     * 渲染文件浏览器界面
     */
    render() {
        if (!this.container) return;

        this.container.innerHTML = `
            <div class="file-explorer">
                <div class="file-explorer-header">
                    <div class="d-flex align-items-center mb-3">
                        <h5 class="mb-0 me-3">
                            <i class="bi bi-folder2-open"></i> 文件浏览器
                        </h5>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-outline-primary btn-sm" id="openFolderBtn">
                                <i class="bi bi-folder-plus"></i> 打开文件夹
                            </button>
                            <button type="button" class="btn btn-outline-secondary btn-sm" id="refreshBtn">
                                <i class="bi bi-arrow-clockwise"></i> 刷新
                            </button>
                        </div>
                    </div>
                    
                    <div class="current-project-info" id="currentProjectInfo" style="display: none;">
                        <div class="alert alert-info py-2">
                            <small>
                                <i class="bi bi-folder"></i> 
                                <strong id="projectName">项目名称</strong>
                                <br>
                                <span id="projectPath" class="text-muted">项目路径</span>
                            </small>
                        </div>
                    </div>
                </div>

                <div class="file-explorer-content">
                    <div class="file-tree-container" id="fileTreeContainer">
                        <div class="text-center text-muted py-4">
                            <i class="bi bi-folder2" style="font-size: 3rem;"></i>
                            <p class="mt-2">请选择一个文件夹开始浏览</p>
                            <button type="button" class="btn btn-primary" id="selectFolderBtn">
                                <i class="bi bi-folder-plus"></i> 选择文件夹
                            </button>
                        </div>
                    </div>
                </div>

                <div class="file-explorer-footer">
                    <div class="recent-projects" id="recentProjects" style="display: none;">
                        <h6><i class="bi bi-clock-history"></i> 最近项目</h6>
                        <div class="recent-projects-list" id="recentProjectsList"></div>
                    </div>
                </div>
            </div>
        `;
    }

    /**
     * 绑定事件
     */
    bindEvents() {
        // 选择文件夹按钮
        const selectFolderBtn = document.getElementById('selectFolderBtn');
        if (selectFolderBtn) {
            selectFolderBtn.addEventListener('click', () => this.selectFolder());
        }

        // 打开文件夹按钮
        const openFolderBtn = document.getElementById('openFolderBtn');
        if (openFolderBtn) {
            openFolderBtn.addEventListener('click', () => this.selectFolder());
        }

        // 刷新按钮
        const refreshBtn = document.getElementById('refreshBtn');
        if (refreshBtn) {
            refreshBtn.addEventListener('click', () => this.refresh());
        }
    }

    /**
     * 选择文件夹
     */
    async selectFolder() {
        try {
            // 使用现代浏览器的 File System Access API
            if ('showDirectoryPicker' in window) {
                const dirHandle = await window.showDirectoryPicker({
                    mode: 'read'
                });
                
                await this.processDirectory(dirHandle);
            } else {
                // 降级方案：使用传统文件输入
                this.showLegacyFileInput();
            }
        } catch (error) {
            console.error('选择文件夹失败:', error);
            this.showError('选择文件夹失败: ' + error.message);
        }
    }

    /**
     * 处理目录
     * @param {DirectoryHandle} dirHandle 目录句柄
     */
    async processDirectory(dirHandle) {
        try {
            this.showLoading('正在读取文件夹结构...');
            
            const projectName = dirHandle.name;
            const projectPath = await this.getDirectoryPath(dirHandle);
            
            // 读取文件结构
            const fileTree = await this.readDirectoryStructure(dirHandle);
            
            // 保存项目信息
            this.storageManager.openProject(projectPath, projectName);
            this.storageManager.setFilePaths(fileTree);
            
            // 更新界面
            this.currentPath = projectPath;
            this.fileTree = fileTree;
            this.updateUI();
            
            this.hideLoading();
            this.showSuccess('文件夹加载成功');
            
        } catch (error) {
            console.error('处理目录失败:', error);
            this.hideLoading();
            this.showError('读取文件夹失败: ' + error.message);
        }
    }

    /**
     * 获取目录路径
     * @param {DirectoryHandle} dirHandle 目录句柄
     * @returns {Promise<string>} 目录路径
     */
    async getDirectoryPath(dirHandle) {
        // 由于安全限制，无法直接获取完整路径
        // 使用目录名称作为标识
        return dirHandle.name;
    }

    /**
     * 读取目录结构
     * @param {DirectoryHandle} dirHandle 目录句柄
     * @param {string} basePath 基础路径
     * @returns {Promise<Array>} 文件树结构
     */
    async readDirectoryStructure(dirHandle, basePath = '') {
        const fileTree = [];
        
        try {
            for await (const [name, handle] of dirHandle.entries()) {
                const path = basePath ? `${basePath}/${name}` : name;
                
                if (handle.kind === 'directory') {
                    // 递归读取子目录
                    const children = await this.readDirectoryStructure(handle, path);
                    fileTree.push({
                        name: name,
                        path: path,
                        type: 'directory',
                        children: children
                    });
                } else {
                    // 文件
                    fileTree.push({
                        name: name,
                        path: path,
                        type: 'file',
                        size: await this.getFileSize(handle)
                    });
                }
            }
        } catch (error) {
            console.error('读取目录结构失败:', error);
        }
        
        return fileTree;
    }

    /**
     * 获取文件大小
     * @param {FileHandle} fileHandle 文件句柄
     * @returns {Promise<number>} 文件大小
     */
    async getFileSize(fileHandle) {
        try {
            const file = await fileHandle.getFile();
            return file.size;
        } catch (error) {
            return 0;
        }
    }

    /**
     * 显示传统文件输入
     */
    showLegacyFileInput() {
        const input = document.createElement('input');
        input.type = 'file';
        input.webkitdirectory = true;
        input.multiple = true;
        
        input.addEventListener('change', (event) => {
            const files = event.target.files;
            if (files.length > 0) {
                this.processLegacyFiles(files);
            }
        });
        
        input.click();
    }

    /**
     * 处理传统文件输入
     * @param {FileList} files 文件列表
     */
    processLegacyFiles(files) {
        try {
            this.showLoading('正在处理文件...');
            
            const fileTree = this.buildFileTreeFromFiles(files);
            const projectName = this.extractProjectNameFromFiles(files);
            
            // 保存项目信息
            this.storageManager.openProject(projectName, projectName);
            this.storageManager.setFilePaths(fileTree);
            
            // 更新界面
            this.currentPath = projectName;
            this.fileTree = fileTree;
            this.updateUI();
            
            this.hideLoading();
            this.showSuccess('文件夹加载成功');
            
        } catch (error) {
            console.error('处理文件失败:', error);
            this.hideLoading();
            this.showError('处理文件失败: ' + error.message);
        }
    }

    /**
     * 从文件列表构建文件树
     * @param {FileList} files 文件列表
     * @returns {Array} 文件树结构
     */
    buildFileTreeFromFiles(files) {
        const fileTree = [];
        const fileMap = new Map();
        
        // 构建文件映射
        for (let file of files) {
            const pathParts = file.webkitRelativePath.split('/');
            let currentLevel = fileTree;
            
            for (let i = 0; i < pathParts.length; i++) {
                const part = pathParts[i];
                const isFile = i === pathParts.length - 1;
                
                if (isFile) {
                    // 文件
                    currentLevel.push({
                        name: part,
                        path: file.webkitRelativePath,
                        type: 'file',
                        size: file.size
                    });
                } else {
                    // 目录
                    let dirNode = currentLevel.find(node => 
                        node.type === 'directory' && node.name === part
                    );
                    
                    if (!dirNode) {
                        dirNode = {
                            name: part,
                            path: pathParts.slice(0, i + 1).join('/'),
                            type: 'directory',
                            children: []
                        };
                        currentLevel.push(dirNode);
                    }
                    
                    currentLevel = dirNode.children;
                }
            }
        }
        
        return fileTree;
    }

    /**
     * 从文件列表提取项目名称
     * @param {FileList} files 文件列表
     * @returns {string} 项目名称
     */
    extractProjectNameFromFiles(files) {
        if (files.length === 0) return '未命名项目';
        
        const firstFile = files[0];
        const pathParts = firstFile.webkitRelativePath.split('/');
        return pathParts[0] || '未命名项目';
    }

    /**
     * 更新界面
     */
    updateUI() {
        this.updateProjectInfo();
        this.updateFileTree();
        this.updateRecentProjects();
    }

    /**
     * 更新项目信息
     */
    updateProjectInfo() {
        const projectInfo = this.storageManager.getProjectInfo();
        const currentProjectInfo = document.getElementById('currentProjectInfo');
        const projectName = document.getElementById('projectName');
        const projectPath = document.getElementById('projectPath');
        
        if (projectInfo && projectInfo.isOpen) {
            currentProjectInfo.style.display = 'block';
            projectName.textContent = projectInfo.name;
            projectPath.textContent = projectInfo.path;
        } else {
            currentProjectInfo.style.display = 'none';
        }
    }

    /**
     * 更新文件树
     */
    updateFileTree() {
        const container = document.getElementById('fileTreeContainer');
        if (!container) return;
        
        if (this.fileTree.length === 0) {
            container.innerHTML = `
                <div class="text-center text-muted py-4">
                    <i class="bi bi-folder2" style="font-size: 3rem;"></i>
                    <p class="mt-2">请选择一个文件夹开始浏览</p>
                    <button type="button" class="btn btn-primary" id="selectFolderBtn">
                        <i class="bi bi-folder-plus"></i> 选择文件夹
                    </button>
                </div>
            `;
            
            // 重新绑定事件
            const selectFolderBtn = document.getElementById('selectFolderBtn');
            if (selectFolderBtn) {
                selectFolderBtn.addEventListener('click', () => this.selectFolder());
            }
            return;
        }
        
        container.innerHTML = `
            <div class="file-tree">
                ${this.renderFileTree(this.fileTree)}
            </div>
        `;
    }

    /**
     * 渲染文件树
     * @param {Array} fileTree 文件树
     * @param {number} level 层级
     * @returns {string} HTML字符串
     */
    renderFileTree(fileTree, level = 0) {
        let html = '';
        
        for (const item of fileTree) {
            const indent = '  '.repeat(level);
            const icon = item.type === 'directory' ? 'bi-folder' : 'bi-file-earmark';
            const size = item.type === 'file' ? this.formatFileSize(item.size) : '';
            
            html += `
                <div class="file-tree-item" data-path="${item.path}" data-type="${item.type}">
                    <div class="d-flex align-items-center py-1">
                        <span class="me-2" style="width: ${level * 20}px;"></span>
                        <i class="bi ${icon} me-2"></i>
                        <span class="flex-grow-1">${item.name}</span>
                        ${size ? `<small class="text-muted">${size}</small>` : ''}
                    </div>
                    ${item.type === 'directory' && item.children ? 
                        `<div class="file-tree-children ms-3">
                            ${this.renderFileTree(item.children, level + 1)}
                        </div>` : ''
                    }
                </div>
            `;
        }
        
        return html;
    }

    /**
     * 格式化文件大小
     * @param {number} bytes 字节数
     * @returns {string} 格式化的大小
     */
    formatFileSize(bytes) {
        if (bytes === 0) return '0 B';
        
        const k = 1024;
        const sizes = ['B', 'KB', 'MB', 'GB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        
        return parseFloat((bytes / Math.pow(k, i)).toFixed(1)) + ' ' + sizes[i];
    }

    /**
     * 更新最近项目
     */
    updateRecentProjects() {
        const recentProjects = this.storageManager.getRecentProjects();
        const container = document.getElementById('recentProjects');
        const list = document.getElementById('recentProjectsList');
        
        if (recentProjects.length > 0) {
            container.style.display = 'block';
            list.innerHTML = recentProjects.map(project => `
                <div class="recent-project-item p-2 border-bottom">
                    <div class="d-flex align-items-center">
                        <i class="bi bi-folder me-2"></i>
                        <div class="flex-grow-1">
                            <div class="fw-bold">${project.name}</div>
                            <small class="text-muted">${project.path}</small>
                        </div>
                        <button type="button" class="btn btn-sm btn-outline-primary" 
                                onclick="fileExplorer.openRecentProject('${project.path}')">
                            打开
                        </button>
                    </div>
                </div>
            `).join('');
        } else {
            container.style.display = 'none';
        }
    }

    /**
     * 打开最近项目
     * @param {string} projectPath 项目路径
     */
    openRecentProject(projectPath) {
        // 由于安全限制，无法直接重新打开目录
        // 这里可以显示项目信息或提示用户重新选择
        this.showInfo('请重新选择文件夹以打开项目');
    }

    /**
     * 加载保存的项目
     */
    loadSavedProject() {
        const projectInfo = this.storageManager.getProjectInfo();
        const filePaths = this.storageManager.getFilePaths();
        
        if (projectInfo && projectInfo.isOpen && filePaths.length > 0) {
            this.currentPath = projectInfo.path;
            this.fileTree = filePaths;
            this.updateUI();
        }
    }

    /**
     * 刷新
     */
    refresh() {
        this.loadSavedProject();
        this.showSuccess('已刷新');
    }

    /**
     * 显示加载状态
     * @param {string} message 消息
     */
    showLoading(message) {
        // 这里可以显示加载指示器
        console.log('Loading:', message);
    }

    /**
     * 隐藏加载状态
     */
    hideLoading() {
        // 隐藏加载指示器
        console.log('Loading completed');
    }

    /**
     * 显示成功消息
     * @param {string} message 消息
     */
    showSuccess(message) {
        // 这里可以使用AlertManager显示成功消息
        console.log('Success:', message);
    }

    /**
     * 显示错误消息
     * @param {string} message 消息
     */
    showError(message) {
        // 这里可以使用AlertManager显示错误消息
        console.error('Error:', message);
    }

    /**
     * 显示信息消息
     * @param {string} message 消息
     */
    showInfo(message) {
        // 这里可以使用AlertManager显示信息消息
        console.log('Info:', message);
    }
}

// 导出到全局
window.FileExplorer = FileExplorer; 