/**
 * FileProcessor - 文件处理器
 * 基于DDDSimplifiedGeneratorFileDrivenAdapter接口实现前端文件读写
 */
class FileProcessor {
    constructor() {
        this.selectedFiles = [];
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.setupDragAndDrop();
    }

    setupEventListeners() {
        const fileInput = document.getElementById('fileInput');
        const uploadArea = document.getElementById('uploadArea');

        fileInput.addEventListener('change', (e) => {
            this.handleFileSelection(e.target.files);
        });

        uploadArea.addEventListener('click', () => {
            fileInput.click();
        });
    }

    setupDragAndDrop() {
        const uploadArea = document.getElementById('uploadArea');

        uploadArea.addEventListener('dragover', (e) => {
            e.preventDefault();
            uploadArea.classList.add('drag-over');
        });

        uploadArea.addEventListener('dragleave', (e) => {
            e.preventDefault();
            uploadArea.classList.remove('drag-over');
        });

        uploadArea.addEventListener('drop', (e) => {
            e.preventDefault();
            uploadArea.classList.remove('drag-over');
            this.handleFileSelection(e.dataTransfer.files);
        });
    }

    handleFileSelection(files) {
        if (!files || files.length === 0) return;

        for (let file of files) {
            if (this.isValidFile(file)) {
                this.addFile(file);
            } else {
                AlertManager.warning(`不支持的文件格式: ${file.name}`);
            }
        }

        this.updateUI();
    }

    isValidFile(file) {
        const validExtensions = ['.json', '.xml', '.csv', '.txt'];
        const fileName = file.name.toLowerCase();
        return validExtensions.some(ext => fileName.endsWith(ext));
    }

    addFile(file) {
        const existingFile = this.selectedFiles.find(f => f.name === file.name);
        if (existingFile) {
            AlertManager.warning(`文件 ${file.name} 已存在，已跳过`);
            return;
        }
        this.selectedFiles.push(file);
    }

    updateUI() {
        const fileListSection = document.getElementById('fileListSection');
        const aggregateConfigSection = document.getElementById('aggregateConfigSection');
        const actionSection = document.getElementById('actionSection');

        if (this.selectedFiles.length > 0) {
            fileListSection.style.display = 'block';
            aggregateConfigSection.style.display = 'block';
            actionSection.style.display = 'block';
            this.renderFileList();
        } else {
            fileListSection.style.display = 'none';
            aggregateConfigSection.style.display = 'none';
            actionSection.style.display = 'none';
        }
    }

    renderFileList() {
        const fileList = document.getElementById('fileList');
        fileList.innerHTML = '';
        
        this.selectedFiles.forEach((file, index) => {
            const fileItem = document.createElement('div');
            fileItem.className = 'file-item d-flex align-items-center justify-content-between p-2 border rounded mb-2';
            fileItem.innerHTML = `
                <div class="file-info">
                    <i class="bi bi-file-earmark-text text-primary me-2"></i>
                    <span class="file-name">${file.name}</span>
                    <small class="text-muted ms-2">(${this.formatFileSize(file.size)})</small>
                </div>
                <button class="btn btn-sm btn-outline-danger" onclick="FileProcessor.removeFile(${index})">
                    <i class="bi bi-trash"></i>
                </button>
            `;
            fileList.appendChild(fileItem);
        });
    }

    formatFileSize(bytes) {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const sizes = ['Bytes', 'KB', 'MB', 'GB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    removeFile(index) {
        this.selectedFiles.splice(index, 1);
        this.updateUI();
    }

    async processFiles() {
        if (this.selectedFiles.length === 0) {
            AlertManager.error('请先选择要处理的文件');
            return;
        }

        const config = this.getConfig();
        if (!this.validateConfig(config)) {
            return;
        }

        try {
            AlertManager.info('开始处理文件...');
            
            // 读取文件内容
            const fileContents = await this.readFiles();
            
            // 发送到后端生成代码
            const generatedFiles = await this.generateCodeFromBackend(fileContents, config);
            
            // 写入本地文件
            await this.writeFilesToLocal(generatedFiles, config);
            
            AlertManager.success('文件处理完成！');
            this.showResults(generatedFiles);
            
        } catch (error) {
            ErrorHandler.handleHttpError(error);
        }
    }

    getConfig() {
        return {
            projectPath: document.getElementById('projectPath').value.trim(),
            servicePath: document.getElementById('servicePath').value.trim(),
            basePackage: document.getElementById('basePackage').value.trim(),
            aggregateName: document.getElementById('aggregateName').value.trim()
        };
    }

    validateConfig(config) {
        if (!config.projectPath) {
            AlertManager.error('请输入项目路径');
            return false;
        }
        if (!config.basePackage) {
            AlertManager.error('请输入基础包名');
            return false;
        }
        if (!config.aggregateName) {
            AlertManager.error('请输入聚合名称');
            return false;
        }
        return true;
    }

    async readFiles() {
        const fileContents = [];
        
        for (let file of this.selectedFiles) {
            try {
                const content = await this.readFileAsText(file);
                fileContents.push({
                    name: file.name,
                    content: content,
                    type: this.getFileType(file.name)
                });
            } catch (error) {
                AlertManager.error(`读取文件 ${file.name} 失败: ${error.message}`);
            }
        }
        
        return fileContents;
    }

    readFileAsText(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = (e) => resolve(e.target.result);
            reader.onerror = (e) => reject(new Error('文件读取失败'));
            reader.readAsText(file);
        });
    }

    getFileType(fileName) {
        const ext = fileName.toLowerCase().split('.').pop();
        switch (ext) {
            case 'json': return 'json';
            case 'xml': return 'xml';
            case 'csv': return 'csv';
            case 'txt': return 'text';
            default: return 'unknown';
        }
    }

    async generateCodeFromBackend(fileContents, config) {
        try {
            const response = await HttpClient.post('/api/generator/aggregate/generate', {
                files: fileContents,
                config: config
            });
            
            return response.data || [];
        } catch (error) {
            throw new Error(`代码生成失败: ${error.message}`);
        }
    }

    async writeFilesToLocal(generatedFiles, config) {
        for (let file of generatedFiles) {
            try {
                await this.writeFile(file, config);
                AlertManager.success(`文件 ${file.fileName} 写入成功`);
            } catch (error) {
                AlertManager.error(`文件 ${file.fileName} 写入失败: ${error.message}`);
            }
        }
    }

    async writeFile(file, config) {
        // 使用File System Access API
        if ('showSaveFilePicker' in window) {
            await this.writeFileWithFileSystemAPI(file, config);
        } else {
            // 降级到下载方式
            this.downloadFile(file.content, file.fileName, 'text/plain');
        }
    }

    async writeFileWithFileSystemAPI(file, config) {
        try {
            const fullPath = `${config.projectPath}/${config.servicePath}/${file.fileName}`;
            const pathParts = fullPath.split('/');
            const fileName = pathParts.pop();
            const directoryPath = pathParts.join('/');
            
            const dirHandle = await window.showDirectoryPicker({
                mode: 'readwrite'
            });
            
            let currentHandle = dirHandle;
            for (let part of directoryPath.split('/')) {
                if (part) {
                    currentHandle = await currentHandle.getDirectoryHandle(part, { create: true });
                }
            }
            
            const fileHandle = await currentHandle.getFileHandle(fileName, { create: true });
            const writable = await fileHandle.createWritable();
            await writable.write(file.content);
            await writable.close();
            
        } catch (error) {
            throw new Error(`文件写入失败: ${error.message}`);
        }
    }

    downloadFile(content, fileName, mimeType) {
        const blob = new Blob([content], {type: mimeType});
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = fileName;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(url);
    }

    showResults(generatedFiles) {
        const resultSection = document.getElementById('resultSection');
        resultSection.style.display = 'block';
        
        const fileList = generatedFiles.map(file => file.fileName).join('</li><li>');
        
        document.getElementById('resultContent').innerHTML = `
            <div class="alert alert-success">
                <h6><i class="bi bi-check-circle"></i> 代码生成成功</h6>
                <p>已生成以下文件：</p>
                <ul>
                    <li>${fileList}</li>
                </ul>
                <p class="mb-0">文件已写入本地文件系统。</p>
            </div>
        `;
    }

    clearAll() {
        this.selectedFiles = [];
        this.updateUI();
        
        document.getElementById('projectPath').value = '';
        document.getElementById('servicePath').value = '';
        document.getElementById('basePackage').value = '';
        document.getElementById('aggregateName').value = '';
        
        document.getElementById('resultSection').style.display = 'none';
        
        AlertManager.info('已清空所有内容');
    }
}

// 全局实例
window.FileProcessor = new FileProcessor();
