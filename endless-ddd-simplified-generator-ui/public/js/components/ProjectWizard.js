/**
 * ProjectWizard.js
 * Endless DDD 项目创建向导
 * 严格按照 ProjectCreateReqCTransfer 字段命名规范
 */

class ProjectWizard {
    constructor() {
        this.currentStep = 1;
        this.totalSteps = 4;
        this.storageManager = null;
        this.init();
    }

    init() {
        this.updateStepIndicators();
        this.updateProgressBar();
        this.setupEventListeners();
        this.setupFolderSelection();
        
        // 检查是否需要清理数据（强制刷新或新建项目）
        this.checkAndClearData();
        
        this.loadSavedProjectPath();
    }

    setupFolderSelection() {
        const selectFolderBtn = document.getElementById('selectFolderBtn');
        const rootPathInput = document.getElementById('rootPath');
        
        if (selectFolderBtn) {
            selectFolderBtn.addEventListener('click', () => this.selectFolder());
        }
        
        // 让整个输入框都可以点击
        if (rootPathInput) {
            rootPathInput.addEventListener('click', () => this.selectFolder());
        }
    }

    async selectFolder() {
        try {
            // 清除之前的提示信息
            this.clearFolderSelectionInfo();
            
            // 调试信息
            console.log('Tauri环境检查:', {
                hasTauri: !!window.__TAURI__,
                hasInternals: !!window.__TAURI_INTERNALS__,
                tauriObject: window.__TAURI__,
                internalsObject: window.__TAURI_INTERNALS__,
                hasInvoke: !!(window.__TAURI__ && window.__TAURI__.tauri && window.__TAURI__.tauri.invoke),
                hasInternalsInvoke: !!(window.__TAURI_INTERNALS__ && window.__TAURI_INTERNALS__.invoke)
            });
            
            // 检查是否在Tauri环境中 - 支持Tauri 1.x和2.x
            let hasTauriAPI = false;
            let invokeFunction = null;
            
            if (window.__TAURI_INTERNALS__ && window.__TAURI_INTERNALS__.invoke) {
                // Tauri 2.x
                hasTauriAPI = true;
                invokeFunction = window.__TAURI_INTERNALS__.invoke;
                console.log('检测到Tauri 2.x API');
            } else if (window.__TAURI__ && window.__TAURI__.tauri && window.__TAURI__.tauri.invoke) {
                // Tauri 1.x
                hasTauriAPI = true;
                invokeFunction = window.__TAURI__.tauri.invoke;
                console.log('检测到Tauri 1.x API');
            }
            
            if (hasTauriAPI && invokeFunction) {
                console.log('使用Tauri API选择文件夹');
                await this.selectFolderWithTauri(invokeFunction);
            } else {
                console.log('使用浏览器API选择文件夹');
                // 使用现代浏览器的 File System Access API
                if ('showDirectoryPicker' in window) {
                    const dirHandle = await window.showDirectoryPicker({
                        mode: 'read'
                    });
                    
                    await this.processSelectedFolder(dirHandle);
                } else {
                    // 降级方案：使用传统文件输入
                    this.showLegacyFolderInput();
                }
            }
        } catch (error) {
            // 检查是否是用户取消操作
            if (error.name === 'AbortError' || error.message.includes('cancel')) {
                console.log('用户取消了文件夹选择');
                return; // 用户取消，不显示错误信息
            }
            console.error('选择文件夹失败:', error);
            this.showError('选择文件夹失败: ' + error.message);
        }
    }

    /**
     * 使用Tauri选择文件夹
     */
    async selectFolderWithTauri(invokeFunction) {
        try {
            this.showLoading('正在选择文件夹...');
            
            console.log('调用Tauri select_folder命令...');
            console.log('invoke函数:', invokeFunction);
            
            const result = await invokeFunction('select_folder');
            console.log('Tauri返回结果:', result);
            
            if (result && result.success === 'true') {
                console.log('Tauri选择成功，处理结果...');
                await this.processSelectedFolderWithTauri(result);
            } else {
                this.hideLoading();
                console.log('Tauri选择失败:', result);
                if (result && result.error_message) {
                    this.showError(result.error_message);
                } else {
                    this.showError('文件夹选择失败');
                }
            }
        } catch (error) {
            console.error('Tauri文件夹选择失败:', error);
            this.hideLoading();
            this.showError('文件夹选择失败: ' + error.message);
        }
    }

    /**
     * 处理Tauri选择的文件夹
     */
    async processSelectedFolderWithTauri(result) {
        try {
            const { folder_name, full_path } = result;
            const rootPathInput = document.getElementById('rootPath');
            
            // 设置真实路径到输入框
            rootPathInput.value = full_path;
            rootPathInput.readOnly = false;
            
            // 保存到存储
            if (this.storageManager) {
                this.storageManager.setSessionData({
                    selectedProjectPath: full_path,
                    selectedFolderName: folder_name,
                    lastSelectedAt: new Date().toISOString()
                });
            }
            
            this.hideLoading();
            this.showSuccess(`已选择文件夹: ${folder_name}`);
            
            // 更新提示信息
            this.updateFolderSelectionInfo(folder_name, full_path, false);
            
            // 清除错误状态
            this.clearFieldError('rootPath');
            
        } catch (error) {
            console.error('处理Tauri文件夹失败:', error);
            this.hideLoading();
            this.showError('处理文件夹失败: ' + error.message);
        }
    }

    async processSelectedFolder(dirHandle) {
        try {
            this.showLoading('正在处理文件夹...');
            
            // 由于浏览器安全限制，无法获取真实路径
            // 我们使用文件夹名称，并让用户手动输入或确认路径
            const folderName = dirHandle.name;
            const rootPathInput = document.getElementById('rootPath');
            
            // 构建一个默认的项目路径
            let projectPath;
            if (navigator.platform.includes('Win')) {
                projectPath = `C:\\Projects\\${folderName}`;
            } else {
                projectPath = `/Users/Projects/${folderName}`;
            }
            
            // 设置路径到输入框，但允许用户编辑
            rootPathInput.value = projectPath;
            rootPathInput.readOnly = false; // 允许用户编辑
            
            // 保存到存储
            if (this.storageManager) {
                this.storageManager.setSessionData({
                    selectedProjectPath: projectPath,
                    selectedFolderName: folderName,
                    lastSelectedAt: new Date().toISOString()
                });
            }
            
            this.hideLoading();
            this.showSuccess(`已选择文件夹: ${folderName}`);
            
            // 更新提示信息，提示用户可以编辑路径
            this.updateFolderSelectionInfo(folderName, projectPath, true);
            
            // 清除错误状态
            this.clearFieldError('rootPath');
            
        } catch (error) {
            console.error('处理文件夹失败:', error);
            this.hideLoading();
            this.showError('处理文件夹失败: ' + error.message);
        }
    }

    /**
     * 更新文件夹选择信息
     * @param {string} folderName - 文件夹名称
     * @param {string} projectPath - 项目路径
     * @param {boolean} editable - 是否可编辑
     */
    updateFolderSelectionInfo(folderName, projectPath, editable = false) {
        try {
            const rootPathInput = document.getElementById('rootPath');
            
            // 在输入框中添加提示信息
            rootPathInput.setAttribute('title', `项目根目录: ${folderName}\n完整路径: ${projectPath}`);
            
            // 查找或创建提示信息容器
            let infoContainer = document.getElementById('folderSelectionInfo');
            if (!infoContainer) {
                // 创建提示信息容器
                infoContainer = document.createElement('div');
                infoContainer.id = 'folderSelectionInfo';
                infoContainer.className = 'alert alert-info mt-2';
                infoContainer.style.fontSize = '0.875rem';
                
                // 插入到输入框组的下方
                const inputGroup = rootPathInput.closest('.input-group');
                if (inputGroup && inputGroup.parentElement) {
                    inputGroup.parentElement.insertBefore(infoContainer, inputGroup.nextSibling);
                }
            }
            
            // 更新提示信息内容
            if (editable) {
                infoContainer.innerHTML = `
                    <i class="bi bi-folder2-open me-2"></i>
                    已选择文件夹: <strong>${folderName}</strong>
                    <br>
                    默认路径: <code>${projectPath}</code>
                    <br>
                    <small class="text-muted">
                        <i class="bi bi-info-circle me-1"></i>
                        由于浏览器安全限制，无法获取真实路径。请手动确认或修改项目路径。
                    </small>
                `;
            } else {
                infoContainer.innerHTML = `
                    <i class="bi bi-folder2-open me-2"></i>
                    已选择文件夹: <strong>${folderName}</strong>
                    <br>
                    项目将创建在: <code>${projectPath}</code>
                `;
            }
            
        } catch (error) {
            console.warn('更新文件夹选择信息失败:', error);
        }
    }

    /**
     * 清除文件夹选择信息
     */
    clearFolderSelectionInfo() {
        try {
            const infoContainer = document.getElementById('folderSelectionInfo');
            if (infoContainer) {
                infoContainer.remove();
            }
        } catch (error) {
            console.warn('清除文件夹选择信息失败:', error);
        }
    }

    async getFullPath(dirHandle) {
        try {
            // 尝试获取完整路径
            const pathParts = [];
            let currentHandle = dirHandle;
            
            // 向上遍历获取完整路径
            while (currentHandle) {
                pathParts.unshift(currentHandle.name);
                try {
                    currentHandle = await currentHandle.getParent();
                } catch (e) {
                    // 到达根目录或无法获取父目录
                    break;
                }
            }
            
            // 如果获取到了多级路径，返回完整路径
            if (pathParts.length > 1) {
                if (navigator.platform.includes('Win')) {
                    return pathParts.join('\\');
                } else {
                    return pathParts.join('/');
                }
            }
            
            // 如果只有一级路径，返回文件夹名称
            return dirHandle.name;
            
        } catch (error) {
            console.warn('无法获取完整路径，使用文件夹名称:', error);
            return dirHandle.name;
        }
    }

    showLegacyFolderInput() {
        const input = document.createElement('input');
        input.type = 'file';
        input.webkitdirectory = true;
        input.multiple = true;
        
        input.addEventListener('change', (event) => {
            const files = event.target.files;
            if (files.length > 0) {
                this.processLegacyFolderSelection(files);
            }
        });
        
        input.click();
    }

    processLegacyFolderSelection(files) {
        try {
            this.showLoading('正在处理文件夹...');
            
            if (files.length === 0) {
                throw new Error('没有选择任何文件');
            }
            
            const firstFile = files[0];
            // 获取完整的文件夹路径
            const fullPath = this.getLegacyFullPath(firstFile);
            const rootPathInput = document.getElementById('rootPath');
            
            // 从路径中提取文件夹名称
            const pathParts = fullPath.split(/[\\/]/);
            const folderName = pathParts[pathParts.length - 1] || 'selected-folder';
            
            // 设置路径到输入框
            rootPathInput.value = fullPath;
            
            // 保存到存储
            if (this.storageManager) {
                this.storageManager.setSessionData({
                    selectedProjectPath: fullPath,
                    lastSelectedAt: new Date().toISOString()
                });
            }
            
            this.hideLoading();
            this.showSuccess(`已选择文件夹: ${folderName}`);
            
            // 设置输入框为可编辑
            rootPathInput.readOnly = false;
            
            // 更新提示信息，提示用户可以编辑路径
            this.updateFolderSelectionInfo(folderName, fullPath, true);
            
            // 清除错误状态
            this.clearFieldError('rootPath');
            
        } catch (error) {
            console.error('处理文件夹失败:', error);
            this.hideLoading();
            this.showError('处理文件夹失败: ' + error.message);
        }
    }

    getLegacyFullPath(file) {
        try {
            // 从webkitRelativePath中提取完整路径
            const relativePath = file.webkitRelativePath;
            const pathParts = relativePath.split('/');
            
            // 移除文件名，只保留文件夹路径
            pathParts.pop();
            
            // 如果路径部分存在，返回完整路径
            if (pathParts.length > 0) {
                // 根据操作系统返回不同格式的路径
                if (navigator.platform.includes('Win')) {
                    // Windows 系统使用反斜杠
                    return pathParts.join('\\');
                } else {
                    // Unix/Linux/Mac 系统使用正斜杠
                    return pathParts.join('/');
                }
            }
            
            // 如果无法获取完整路径，构建一个合理的项目路径
            const folderName = pathParts[0] || 'selected-folder';
            if (navigator.platform.includes('Win')) {
                return `C:\\Projects\\${folderName}`;
            } else {
                return `/Users/Projects/${folderName}`;
            }
            
        } catch (error) {
            console.warn('无法获取完整路径，使用默认名称:', error);
            const defaultName = 'selected-folder';
            if (navigator.platform.includes('Win')) {
                return `C:\\Projects\\${defaultName}`;
            } else {
                return `/Users/Projects/${defaultName}`;
            }
        }
    }

    loadSavedProjectPath() {
        try {
            // 尝试加载StorageManager
            if (typeof StorageManager !== 'undefined') {
                this.storageManager = new StorageManager();
                
                // 从存储中加载上次选择的路径
                const sessionData = this.storageManager.getSessionData();
                if (sessionData.selectedProjectPath) {
                    const rootPathInput = document.getElementById('rootPath');
                    rootPathInput.value = sessionData.selectedProjectPath;
                }
            }
        } catch (error) {
            console.warn('无法加载存储管理器:', error);
        }
    }

    /**
     * 清理保存的项目数据
     */
    clearSavedProjectData() {
        try {
            if (this.storageManager) {
                // 清理项目信息
                this.storageManager.setProjectInfo({
                    name: '',
                    path: '',
                    lastOpened: null,
                    isOpen: false
                });
                
                // 清理文件路径
                this.storageManager.setFilePaths([]);
                
                // 清理会话数据
                this.storageManager.setSessionData({});
                
                console.log('已清理保存的项目数据');
            }
        } catch (error) {
            console.warn('清理项目数据失败:', error);
        }
    }

    /**
     * 检查并清理数据
     */
    checkAndClearData() {
        try {
            // 检查URL参数是否包含清理标记
            const urlParams = new URLSearchParams(window.location.search);
            const shouldClear = urlParams.get('clear') === 'true';
            
            // 检查sessionStorage中是否有强制刷新标记
            const forceRefresh = sessionStorage.getItem('force_refresh');
            
            if (shouldClear || forceRefresh === 'true') {
                // 初始化StorageManager
                if (typeof StorageManager !== 'undefined') {
                    this.storageManager = new StorageManager();
                    this.clearSavedProjectData();
                }
                
                // 清理URL参数
                if (shouldClear) {
                    const newUrl = window.location.pathname;
                    window.history.replaceState({}, '', newUrl);
                }
                
                // 清理sessionStorage标记
                if (forceRefresh) {
                    sessionStorage.removeItem('force_refresh');
                }
                
                console.log('已清理项目数据（强制刷新或新建项目）');
            }
        } catch (error) {
            console.warn('检查清理数据失败:', error);
        }
    }

    showLoading(message) {
        // 显示加载状态
        const selectFolderBtn = document.getElementById('selectFolderBtn');
        if (selectFolderBtn) {
            const originalText = selectFolderBtn.innerHTML;
            selectFolderBtn.innerHTML = '<i class="bi bi-hourglass-split"></i> 处理中...';
            selectFolderBtn.disabled = true;
            
            // 保存原始文本，以便恢复
            selectFolderBtn._originalText = originalText;
        }
    }

    hideLoading() {
        // 隐藏加载状态
        const selectFolderBtn = document.getElementById('selectFolderBtn');
        if (selectFolderBtn && selectFolderBtn._originalText) {
            selectFolderBtn.innerHTML = selectFolderBtn._originalText;
            selectFolderBtn.disabled = false;
            delete selectFolderBtn._originalText;
        }
    }

    setupEventListeners() {
        // 设置拖拽事件监听器
        this.setupDragAndDrop();
        
        // 监听所有input和textarea的输入事件
        document.addEventListener('input', (e) => {
            if (e.target.matches('input, textarea')) {
                this.handleInputChange(e.target);
            }
        });
        
        // 监听所有input和textarea的blur事件
        document.addEventListener('blur', (e) => {
            if (e.target.matches('input, textarea')) {
                this.handleInputBlur(e.target);
            }
        }, true);
        
        // 监听单选按钮的change事件
        document.addEventListener('change', (e) => {
            if (e.target.matches('input[type="radio"]')) {
                this.handleRadioChange(e.target);
            }
        });
    }

    nextStep() {
        if (this.validateCurrentStep()) {
            if (this.currentStep < this.totalSteps) {
                this.currentStep++;
                this.showStep(this.currentStep);
                this.updateStepIndicators();
                this.updateProgressBar();
                this.updateButtons();

                if (this.currentStep === this.totalSteps) {
                    this.generateSummary();
                }
            }
        }
    }

    prevStep() {
        if (this.currentStep > 1) {
            this.currentStep--;
            this.showStep(this.currentStep);
            this.updateStepIndicators();
            this.updateProgressBar();
            this.updateButtons();
        }
    }

    showStep(step) {
        // 隐藏所有步骤
        document.querySelectorAll('.wizard-step').forEach(stepEl => {
            stepEl.classList.remove('active');
        });

        // 显示当前步骤
        document.getElementById(`step${step}`).classList.add('active');
    }

    updateStepIndicators() {
        document.querySelectorAll('.step-number').forEach((indicator, index) => {
            const stepNumber = index + 1;
            indicator.classList.remove('active', 'completed');

            if (stepNumber === this.currentStep) {
                indicator.classList.add('active');
            } else if (stepNumber < this.currentStep) {
                indicator.classList.add('completed');
            }
        });
    }

    updateProgressBar() {
        const progress = (this.currentStep / this.totalSteps) * 100;
        document.getElementById('progressBar').style.width = `${progress}%`;
    }

    updateButtons() {
        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        const createBtn = document.getElementById('createBtn');
        const buttonContainer = prevBtn.parentElement;

        // 更新按钮显示状态
        prevBtn.style.display = this.currentStep > 1 ? 'block' : 'none';
        nextBtn.style.display = this.currentStep < this.totalSteps ? 'block' : 'none';
        createBtn.style.display = this.currentStep === this.totalSteps ? 'block' : 'none';

        // 动态调整布局
        if (this.currentStep === 1) {
            // 第一步：只有下一步按钮，显示在右边
            buttonContainer.className = 'd-flex justify-content-end mt-4';
        } else {
            // 其他步骤：上一步和下一步/创建按钮，左右分布
            buttonContainer.className = 'd-flex justify-content-between mt-4';
        }
    }

    validateCurrentStep() {
        const errors = [];

        switch (this.currentStep) {
            case 1:
                this.validateStep1(errors);
                break;
            case 2:
                this.validateStep2(errors);
                break;
            case 3:
                this.validateStep3(errors);
                break;
        }

        // 如果有任何错误，都阻止进入下一步
        if (errors.length > 0) {
            return false;
        }

        return true;
    }


    validateStep1(errors) {
        const requiredFields = [
            'projectArtifactId',
            'groupId',
            'name',
            'description',
            'version',
            'author',
            'rootPath',
            'basePackage'
        ];

        // 清除所有错误状态
        this.clearAllErrors();

        requiredFields.forEach(field => {
            const element = document.getElementById(field);
            const value = element.value.trim();
            if (!value) {
                const label = document.querySelector(`label[for="${field}"]`).textContent.replace(' *', '');
                // 必输项为空只显示输入框错误，不弹出提示
                errors.push(`${label}不能为空`);
                this.showFieldError(field, `${label}不能为空`);
            } else {
                // 格式验证错误直接在字段上显示，并收集到errors数组
                if (field === 'projectArtifactId' && !/^[a-z][a-z0-9-]*$/.test(value)) {
                    this.showFieldError(field, '项目构件ID只能包含小写字母、数字和连字符，且必须以字母开头');
                    errors.push('项目构件ID格式不正确');
                }
                if (field === 'groupId' && !/^[a-z][a-z0-9.]*$/.test(value)) {
                    this.showFieldError(field, '组织ID只能包含小写字母、数字和点号，且必须以字母开头');
                    errors.push('组织ID格式不正确');
                }
                if (field === 'version' && !/^\d+\.\d+\.\d+$/.test(value)) {
                    this.showFieldError(field, '版本号格式不正确，请使用 x.y.z 格式，例如：1.0.0');
                    errors.push('版本号格式不正确');
                }
            }
        });
    }

    validateStep2(errors) {
        // 验证Java版本
        const javaVersion = document.querySelector('input[name="javaVersion"]:checked');
        if (!javaVersion) {
            errors.push('请选择Java版本');
            this.showRadioError('javaVersion', '请选择Java版本');
        }

        // 验证日志框架
        const loggingFramework = document.querySelector('input[name="loggingFramework"]:checked');
        if (!loggingFramework) {
            errors.push('请选择日志框架');
            this.showRadioError('loggingFramework', '请选择日志框架');
        }

        // 验证持久化框架
        const persistenceFramework = document.querySelector('input[name="persistenceFramework"]:checked');
        if (!persistenceFramework) {
            errors.push('请选择持久化框架');
            this.showRadioError('persistenceFramework', '请选择持久化框架');
        }
    }

    validateStep3(errors) {
        const serviceArtifactIds = this.getServiceArtifactIds();

        if (serviceArtifactIds.length === 0) {
            errors.push('请至少添加一个服务构件ID');
            this.showServiceError('请至少添加一个服务构件ID');
        } else {
            // 如果有服务ID，清除错误状态
            const container = document.getElementById('serviceArtifactIdsContainer');
            if (container) {
                container.classList.remove('is-invalid');
                const errorDiv = container.parentElement.querySelector('.invalid-feedback');
                if (errorDiv) {
                    errorDiv.style.display = 'none';
                }
            }
        }

        serviceArtifactIds.forEach((id, index) => {
            if (!id.trim()) {
                errors.push(`第${index + 1}个服务构件ID不能为空`);
                // 在对应的输入框上显示错误
                const inputs = document.querySelectorAll('.service-artifact-id');
                if (inputs[index]) {
                    inputs[index].classList.add('is-invalid');
                    inputs[index].style.borderColor = '#dc3545';
                    inputs[index].style.boxShadow = '0 0 0 0.2rem rgba(220, 53, 69, 0.25)';
                }
            }
        });
    }

    generateSummary() {
        const summary = document.getElementById('projectSummary');
        const data = this.collectFormData();

        let html = '<div class="row">';

        // 基本信息
        html += '<div class="col-md-6"><h5>基本信息</h5>';
        html += `<div class="summary-item"><span>项目构件ID:</span><span>${data.projectArtifactId}</span></div>`;
        html += `<div class="summary-item"><span>组织ID:</span><span>${data.groupId}</span></div>`;
        html += `<div class="summary-item"><span>项目名称:</span><span>${data.name}</span></div>`;
        html += `<div class="summary-item"><span>项目版本:</span><span>${data.version}</span></div>`;
        html += `<div class="summary-item"><span>项目作者:</span><span>${data.author}</span></div>`;
        html += `<div class="summary-item"><span>项目根路径:</span><span>${data.rootPath}</span></div>`;
        html += `<div class="summary-item"><span>基础包名:</span><span>${data.basePackage}</span></div>`;
        html += `<div class="summary-item"><span>项目描述:</span><span>${data.description}</span></div>`;
        html += '</div>';

        // 技术配置
        html += '<div class="col-md-6"><h5>技术配置</h5>';
        html += `<div class="summary-item"><span>Java版本:</span><span>${data.javaVersion}</span></div>`;
        html += `<div class="summary-item"><span>日志框架:</span><span>${data.loggingFramework}</span></div>`;
        html += `<div class="summary-item"><span>持久化框架:</span><span>${data.persistenceFramework}</span></div>`;
        html += `<div class="summary-item"><span>Spring Doc:</span><span>${data.enableSpringDoc ? '启用' : '禁用'}</span></div>`;
        html += '</div>';

        html += '</div>';

        // 服务配置
        html += '<div class="row mt-3"><div class="col-12"><h5>服务配置</h5>';
        if (data.serviceArtifactIds.length > 0) {
            data.serviceArtifactIds.forEach((id, index) => {
                html += `<div class="summary-item"><span>服务${index + 1}:</span><span>${id}</span></div>`;
            });
        } else {
            html += '<div class="summary-item"><span>服务构件ID:</span><span>无</span></div>';
        }
        html += '</div></div>';

        summary.innerHTML = html;
    }

    collectFormData() {
        return {
            projectArtifactId: document.getElementById('projectArtifactId').value.trim(),
            groupId: document.getElementById('groupId').value.trim(),
            name: document.getElementById('name').value.trim(),
            description: document.getElementById('description').value.trim(),
            version: document.getElementById('version').value.trim(),
            author: document.getElementById('author').value.trim(),
            rootPath: document.getElementById('rootPath').value.trim(),
            basePackage: document.getElementById('basePackage').value.trim(),
            enableSpringDoc: document.getElementById('enableSpringDoc').checked ? 'true' : 'false',
            javaVersion: document.querySelector('input[name="javaVersion"]:checked')?.value || '',
            loggingFramework: document.querySelector('input[name="loggingFramework"]:checked')?.value || '',
            persistenceFramework: document.querySelector('input[name="persistenceFramework"]:checked')?.value || '',
            serviceArtifactIds: this.getServiceArtifactIds()
        };
    }

    getServiceArtifactIds() {
        const inputs = document.querySelectorAll('.service-artifact-id');
        return Array.from(inputs).map(input => input.value.trim()).filter(value => value);
    }

    async createProject() {
        if (!this.validateCurrentStep()) {
            return;
        }

        const data = this.collectFormData();
        console.log('发送的数据:', data);

        try {
            // 显示创建进度
            this.showProjectCreationProgress('正在创建项目...');
            
            const result = await HttpClient.post('/generator/project/command/generate', data);
            console.log('项目创建结果:', result);
            console.log('结果类型:', typeof result);
            console.log('结果是否为数组:', Array.isArray(result));
            if (result && typeof result === 'object') {
                console.log('结果键:', Object.keys(result));
                if (result.files) {
                    console.log('文件数量:', result.files.length);
                    console.log('第一个文件:', result.files[0]);
                }
                if (result.data) {
                    console.log('data字段:', result.data);
                    console.log('data类型:', typeof result.data);
                    if (result.data && typeof result.data === 'object') {
                        console.log('data键:', Object.keys(result.data));
                        if (result.data.files) {
                            console.log('data.files数量:', result.data.files.length);
                            console.log('data.files第一个:', result.data.files[0]);
                        }
                    }
                }
            }

            // 检查结果是否为空或无效
            if (result && (typeof result === 'object' && Object.keys(result).length > 0 || typeof result === 'string')) {
                // 项目创建成功后，清理保存的信息
                this.clearSavedProjectData();
                
                // 尝试写入文件
                await this.handleProjectFileWriting(result);
            } else {
                // 项目创建成功后，清理保存的信息
                this.clearSavedProjectData();
                this.showSuccess('项目创建成功！');
            }
        } catch (error) {
            console.error('创建项目失败:', error);
            console.error('错误类型:', typeof error);
            console.error('错误构造函数:', error.constructor.name);
            if (error instanceof HttpError) {
                console.error('HTTP状态:', error.status);
                console.error('HTTP状态文本:', error.statusText);
                console.error('错误消息:', error.message);
            }
            const errorMessage = ErrorHandler.handleHttpError(error);
            console.error('处理后的错误消息:', errorMessage);
            this.showError(errorMessage);
        } finally {
            this.hideProjectCreationProgress();
        }
    }

    /**
     * 处理项目文件写入
     * @param {Object} projectResponse - 项目创建响应
     */
    async handleProjectFileWriting(projectResponse) {
        try {
            // 检查是否在Tauri环境中
            if (window.__TAURI__) {
                await this.writeFilesWithTauri(projectResponse);
            } else {
                // 检查是否有FileWriter支持
                if (typeof FileWriter === 'undefined') {
                    console.warn('FileWriter未加载，跳过文件写入');
                    this.showSuccess('项目创建成功！文件写入功能未启用');
                    return;
                }

                const fileWriter = new FileWriter();
                const features = fileWriter.getSupportedFeatures();
                console.log('文件写入功能支持:', features);

                if (features.fileSystemAccess && features.fileSystem) {
                    // 尝试直接写入文件系统
                    await this.writeFilesToFileSystem(fileWriter, projectResponse);
                } else if (features.download) {
                    // 降级到文件下载
                    await this.downloadProjectFiles(fileWriter, projectResponse);
                } else {
                    // 不支持文件操作
                    this.showSuccess('项目创建成功！当前浏览器不支持文件操作');
                }
            }

        } catch (error) {
            console.error('文件写入失败:', error);
            
            // 尝试降级到下载方式
            try {
                if (window.__TAURI__) {
                    // Tauri环境下重试
                    await this.writeFilesWithTauri(projectResponse);
                } else if (typeof FileWriter !== 'undefined') {
                    const fileWriter = new FileWriter();
                    await this.downloadProjectFiles(fileWriter, projectResponse);
                } else {
                    this.showSuccess('项目创建成功！文件写入失败，请手动保存文件');
                }
            } catch (downloadError) {
                console.error('下载也失败了:', downloadError);
                this.showSuccess('项目创建成功！文件写入和下载都失败，请手动保存文件');
            }
        }
    }

    /**
     * 使用Tauri写入文件
     * @param {Object} projectResponse - 项目响应
     */
    async writeFilesWithTauri(projectResponse) {
        try {
            this.updateProjectCreationProgress('正在使用Tauri写入文件...');
            
            const { invoke } = window.__TAURI__.tauri;
            
            // 解析项目响应数据
            let files = [];
            if (projectResponse && Array.isArray(projectResponse)) {
                files = projectResponse;
            } else if (projectResponse && projectResponse.files && Array.isArray(projectResponse.files)) {
                files = projectResponse.files;
            } else if (projectResponse && projectResponse.fileList && Array.isArray(projectResponse.fileList)) {
                files = projectResponse.fileList;
            } else if (projectResponse && projectResponse.data && projectResponse.data.files && Array.isArray(projectResponse.data.files)) {
                files = projectResponse.data.files;
            } else {
                throw new Error('无法识别的项目响应格式');
            }
            
            // 调用Tauri的文件写入命令
            await invoke('write_files_to_disk', { files });
            
            this.showSuccess('项目创建成功！文件已写入到本地目录');
            
        } catch (error) {
            console.error('Tauri文件写入失败:', error);
            throw error;
        }
    }

    /**
     * 写入文件到文件系统
     * @param {FileWriter} fileWriter - 文件写入器
     * @param {Object} projectResponse - 项目响应
     */
    async writeFilesToFileSystem(fileWriter, projectResponse) {
        try {
            this.updateProjectCreationProgress('正在写入文件到本地...');
            
            await fileWriter.writeProjectFiles(projectResponse);
            
            this.showSuccess('项目创建成功！文件已写入到本地目录');
            
        } catch (error) {
            console.error('写入文件系统失败:', error);
            throw error;
        }
    }

    /**
     * 下载项目文件
     * @param {FileWriter} fileWriter - 文件写入器
     * @param {Object} projectResponse - 项目响应
     */
    async downloadProjectFiles(fileWriter, projectResponse) {
        try {
            this.updateProjectCreationProgress('正在准备文件下载...');
            
            await fileWriter.downloadProjectFiles(projectResponse);
            
            this.showSuccess('项目创建成功！文件已下载到本地');
            
        } catch (error) {
            console.error('下载文件失败:', error);
            throw error;
        }
    }

    /**
     * 显示项目创建进度
     * @param {string} message - 进度消息
     */
    showProjectCreationProgress(message) {
        // 创建进度提示
        const progressHtml = `
            <div class="alert alert-info d-flex align-items-center" role="alert">
                <div class="spinner-border spinner-border-sm me-2" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <span id="projectCreationProgressMessage">${message}</span>
            </div>
        `;
        
        // 插入到页面顶部
        const container = document.querySelector('.wizard-container');
        if (container) {
            const progressDiv = document.createElement('div');
            progressDiv.id = 'projectCreationProgress';
            progressDiv.innerHTML = progressHtml;
            container.insertBefore(progressDiv, container.firstChild);
        }
    }

    /**
     * 更新项目创建进度消息
     * @param {string} message - 新的进度消息
     */
    updateProjectCreationProgress(message) {
        const progressMessage = document.getElementById('projectCreationProgressMessage');
        if (progressMessage) {
            progressMessage.textContent = message;
        }
    }

    /**
     * 隐藏项目创建进度
     */
    hideProjectCreationProgress() {
        const progressDiv = document.getElementById('projectCreationProgress');
        if (progressDiv) {
            progressDiv.remove();
        }
    }


    showSuccess(message) {
        if (typeof AlertManager !== 'undefined') {
            AlertManager.success(message);
        }
    }

    showFieldError(fieldId, message) {
        const element = document.getElementById(fieldId);
        const formControl = element.closest('.form-floating');

        // 添加错误样式
        element.classList.add('is-invalid');
        element.style.borderColor = '#dc3545';
        element.style.boxShadow = '0 0 0 0.2rem rgba(220, 53, 69, 0.25)';

        // 创建或更新错误提示
        let errorDiv = formControl.querySelector('.invalid-feedback');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            formControl.appendChild(errorDiv);
        }
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    }

    showRadioError(name, message) {
        const radioGroup = document.querySelector(`input[name="${name}"]`).closest('.col-md-6');

        // 添加错误样式到整个组
        radioGroup.classList.add('is-invalid');

        // 创建或更新错误提示
        let errorDiv = radioGroup.querySelector('.invalid-feedback');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            errorDiv.style.display = 'block';
            radioGroup.appendChild(errorDiv);
        }
        errorDiv.textContent = message;
        errorDiv.style.color = '#dc3545';
        errorDiv.style.fontSize = '0.875rem';
        errorDiv.style.marginTop = '0.25rem';
    }

    showServiceError(message) {
        const container = document.getElementById('serviceArtifactIdsContainer');

        // 添加错误样式
        container.classList.add('is-invalid');

        // 创建或更新错误提示
        let errorDiv = container.parentElement.querySelector('.invalid-feedback');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            errorDiv.style.display = 'block';
            container.parentElement.appendChild(errorDiv);
        }
        errorDiv.textContent = message;
        errorDiv.style.color = '#dc3545';
        errorDiv.style.fontSize = '0.875rem';
        errorDiv.style.marginTop = '0.25rem';
    }

    clearFieldError(fieldId) {
        const element = document.getElementById(fieldId);
        if (element) {
            element.classList.remove('is-invalid');
            element.style.borderColor = '';
            element.style.boxShadow = '';

            const formControl = element.closest('.form-floating');
            if (formControl) {
                const errorDiv = formControl.querySelector('.invalid-feedback');
                if (errorDiv) {
                    errorDiv.style.display = 'none';
                }
            }
        }
    }

    clearRadioError(name) {
        const radioGroup = document.querySelector(`input[name="${name}"]`).closest('.col-md-6');
        if (radioGroup) {
            radioGroup.classList.remove('is-invalid');
            const errorDiv = radioGroup.querySelector('.invalid-feedback');
            if (errorDiv) {
                errorDiv.style.display = 'none';
            }
        }
    }

    clearAllErrors() {
        // 清除所有输入框错误
        document.querySelectorAll('.form-control.is-invalid').forEach(element => {
            element.classList.remove('is-invalid');
            element.style.borderColor = '';
            element.style.boxShadow = '';
        });

        // 清除所有错误提示
        document.querySelectorAll('.invalid-feedback').forEach(element => {
            element.style.display = 'none';
        });

        // 清除单选按钮组错误
        document.querySelectorAll('.col-md-6.is-invalid').forEach(element => {
            element.classList.remove('is-invalid');
        });

        // 清除服务容器错误
        const serviceContainer = document.getElementById('serviceArtifactIdsContainer');
        if (serviceContainer) {
            serviceContainer.classList.remove('is-invalid');
        }
    }

    showError(message) {
        if (typeof AlertManager !== 'undefined') {
            AlertManager.error(message);
        }
    }

    setupDragAndDrop() {
        const container = document.getElementById('serviceArtifactIdsContainer');

        // 清除现有的事件监听器
        if (container._dragOverHandler) {
            container.removeEventListener('dragover', container._dragOverHandler);
        }
        if (container._dropHandler) {
            container.removeEventListener('drop', container._dropHandler);
        }

        // 创建新的事件监听器并保存引用
        container._dragOverHandler = (e) => {
            e.preventDefault();
            const draggingItem = document.querySelector('.dragging');
            if (draggingItem) {
                const afterElement = this.getDragAfterElement(container, e.clientY);
                if (afterElement) {
                    container.insertBefore(draggingItem, afterElement);
                } else {
                    container.appendChild(draggingItem);
                }
            }
        };

        container._dropHandler = (e) => {
            e.preventDefault();
            // 清除所有拖拽状态
            document.querySelectorAll('.service-item').forEach(item => {
                item.classList.remove('drag-enter', 'drag-over');
            });
        };

        // 为容器添加拖拽事件监听器
        container.addEventListener('dragover', container._dragOverHandler);
        container.addEventListener('drop', container._dropHandler);

        // 为每个服务项添加拖拽事件监听器
        this.updateDragListeners();
    }

    updateDragListeners() {
        const serviceItems = document.querySelectorAll('.service-item');

        serviceItems.forEach((item, index) => {
            // 更新data-index属性
            item.setAttribute('data-index', index);

            // 移除现有的事件监听器（使用匿名函数引用）
            if (item._dragStartHandler) {
                item.removeEventListener('dragstart', item._dragStartHandler);
            }
            if (item._dragEndHandler) {
                item.removeEventListener('dragend', item._dragEndHandler);
            }

            // 创建新的事件监听器并保存引用
            item._dragStartHandler = this.handleDragStart.bind(this);
            item._dragEndHandler = this.handleDragEnd.bind(this);

            // 添加新的事件监听器
            item.addEventListener('dragstart', item._dragStartHandler);
            item.addEventListener('dragend', item._dragEndHandler);
        });
    }

    handleDragStart(e) {
        const item = e.target.closest('.service-item');
        if (item) {
            item.classList.add('dragging');
            e.dataTransfer.effectAllowed = 'move';
            e.dataTransfer.setData('text/html', item.outerHTML);
        }
    }

    handleDragEnd(e) {
        const item = e.target.closest('.service-item');
        if (item) {
            setTimeout(() => {
                item.classList.remove('dragging');
            }, 100);

            // 更新所有项的索引
            this.updateDragListeners();
        }
    }

    getDragAfterElement(container, y) {
        const draggableElements = [...container.querySelectorAll('.service-item:not(.dragging)')];

        return draggableElements.reduce((closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;

            if (offset < 0 && offset > closest.offset) {
                return {offset: offset, element: child};
            } else {
                return closest;
            }
        }, {offset: Number.NEGATIVE_INFINITY}).element;
    }

    // 清理所有事件监听器
    cleanupEventListeners() {
        const container = document.getElementById('serviceArtifactIdsContainer');
        if (container) {
            // 清理容器事件监听器
            if (container._dragOverHandler) {
                container.removeEventListener('dragover', container._dragOverHandler);
                delete container._dragOverHandler;
            }
            if (container._dropHandler) {
                container.removeEventListener('drop', container._dropHandler);
                delete container._dropHandler;
            }

            // 清理所有服务项的事件监听器
            const serviceItems = document.querySelectorAll('.service-item');
            serviceItems.forEach(item => {
                if (item._dragStartHandler) {
                    item.removeEventListener('dragstart', item._dragStartHandler);
                    delete item._dragStartHandler;
                }
                if (item._dragEndHandler) {
                    item.removeEventListener('dragend', item._dragEndHandler);
                    delete item._dragEndHandler;
                }
            });
        }
    }

    // 调试方法：检查事件监听器状态
    debugEventListeners() {
        const container = document.getElementById('serviceArtifactIdsContainer');
        const serviceItems = document.querySelectorAll('.service-item');

        console.log('容器事件监听器:', {
            hasDragOver: !!container._dragOverHandler,
            hasDrop: !!container._dropHandler
        });

        console.log('服务项事件监听器数量:', serviceItems.length);
        serviceItems.forEach((item, index) => {
            console.log(`项目${index}:`, {
                hasDragStart: !!item._dragStartHandler,
                hasDragEnd: !!item._dragEndHandler
            });
        });
    }

        validateServiceInput() {
        const serviceArtifactIds = this.getServiceArtifactIds();
        const container = document.getElementById('serviceArtifactIdsContainer');
        
        if (serviceArtifactIds.length > 0 && serviceArtifactIds.some(id => id.trim())) {
            // 如果有服务ID且至少有一个不为空，清除错误状态
            if (container) {
                container.classList.remove('is-invalid');
                const errorDiv = container.parentElement.querySelector('.invalid-feedback');
                if (errorDiv) {
                    errorDiv.style.display = 'none';
                }
            }
        }
    }
    
    /**
     * 处理输入框输入事件
     * @param {HTMLElement} input - 输入框元素
     */
    handleInputChange(input) {
        const value = input.value.trim();
        if (value) {
            // 如果有输入内容，清除错误状态
            if (input.id) {
                this.clearFieldError(input.id);
            } else if (input.classList.contains('service-artifact-id')) {
                // 清除服务构件ID输入框的错误状态
                input.classList.remove('is-invalid');
                input.style.borderColor = '';
                input.style.boxShadow = '';
            }
        }
    }
    
    /**
     * 处理输入框失去焦点事件
     * @param {HTMLElement} input - 输入框元素
     */
    handleInputBlur(input) {
        const value = input.value.trim();
        if (!value) {
            // 如果没有内容，显示错误
            const label = document.querySelector(`label[for="${input.id}"]`);
            const fieldName = label ? label.textContent.replace(' *', '') : input.id;
            this.showFieldError(input.id, `${fieldName}不能为空`);
        } else {
            // 如果有内容，进行格式验证
            this.validateFieldFormat(input);
        }
    }
    
    /**
     * 处理单选按钮变化事件
     * @param {HTMLElement} radio - 单选按钮元素
     */
    handleRadioChange(radio) {
        // 清除对应字段的错误状态
        this.clearRadioError(radio.name);
    }
    
    /**
     * 验证字段格式
     * @param {HTMLElement} input - 输入框元素
     */
    validateFieldFormat(input) {
        const value = input.value.trim();
        
        switch (input.id) {
            case 'projectArtifactId':
                if (!/^[a-z][a-z0-9-]*$/.test(value)) {
                    this.showFieldError(input.id, '项目构件ID只能包含小写字母、数字和连字符，且必须以字母开头');
                }
                break;
            case 'groupId':
                if (!/^[a-z][a-z0-9.]*$/.test(value)) {
                    this.showFieldError(input.id, '组织ID只能包含小写字母、数字和点号，且必须以字母开头');
                }
                break;
            case 'version':
                if (!/^\d+\.\d+\.\d+$/.test(value)) {
                    this.showFieldError(input.id, '版本号格式不正确，请使用 x.y.z 格式（如：1.0.0）');
                }
                break;
        }
    }


}

// 服务管理函数
function addService() {
    const container = document.getElementById('serviceArtifactIdsContainer');
    const serviceItem = document.createElement('div');
    serviceItem.className = 'service-item';
    serviceItem.setAttribute('draggable', 'true');
    serviceItem.innerHTML = `
        <div class="drag-handle">
            <i class="bi bi-grip-vertical"></i>
        </div>
        <input type="text" placeholder="输入服务构件ID" class="service-artifact-id">
        <button type="button" class="btn-remove" onclick="removeService(this)">
            <i class="bi bi-trash"></i>
        </button>
    `;
    container.appendChild(serviceItem);

    // 更新拖拽监听器
    if (projectWizard) {
        projectWizard.updateDragListeners();
    }
}

function removeService(button) {
    const container = document.getElementById('serviceArtifactIdsContainer');
    if (container.children.length > 1) {
        button.parentElement.remove();

        // 更新拖拽监听器
        if (projectWizard) {
            projectWizard.updateDragListeners();
        }
    }
}

// 全局函数
function nextStep() {
    projectWizard.nextStep();
}

function prevStep() {
    projectWizard.prevStep();
}

function createProject() {
    projectWizard.createProject();
}

// 初始化
let projectWizard;
document.addEventListener('DOMContentLoaded', () => {
    projectWizard = new ProjectWizard();
});

// 页面卸载时清理事件监听器
window.addEventListener('beforeunload', () => {
    if (projectWizard) {
        projectWizard.cleanupEventListeners();
    }
});
