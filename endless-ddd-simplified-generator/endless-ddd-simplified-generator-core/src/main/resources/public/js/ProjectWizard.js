/**
 * ProjectWizard.js
 * Endless DDD 项目创建向导
 * 严格按照 ProjectCreateReqCTransfer 字段命名规范
 */

class ProjectWizard {
    constructor() {
        this.currentStep = 1;
        this.totalSteps = 4;
        this.init();
    }

    init() {
        this.updateStepIndicators();
        this.updateProgressBar();
        this.setupEventListeners();
    }

    setupEventListeners() {
        // 监听表单变化
        document.querySelectorAll('input, textarea').forEach(input => {
            input.addEventListener('change', () => this.validateCurrentStep());
            input.addEventListener('input', () => this.clearFieldError(input.id));
        });
        
        // 监听单选按钮变化
        document.querySelectorAll('input[type="radio"]').forEach(radio => {
            radio.addEventListener('change', () => this.clearRadioError(radio.name));
        });
        
        // 监听服务ID输入框变化
        document.addEventListener('input', (e) => {
            if (e.target.classList.contains('service-artifact-id')) {
                this.validateServiceInput();
            }
        });
        
        // 设置拖拽事件监听器
        this.setupDragAndDrop();
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
        
        prevBtn.style.display = this.currentStep > 1 ? 'block' : 'none';
        nextBtn.style.display = this.currentStep < this.totalSteps ? 'block' : 'none';
        createBtn.style.display = this.currentStep === this.totalSteps ? 'block' : 'none';
    }

    validateCurrentStep() {
        const errors = [];
        const emptyFieldErrors = [];
        
        switch (this.currentStep) {
            case 1:
                this.validateStep1(errors, emptyFieldErrors);
                break;
            case 2:
                this.validateStep2(errors, emptyFieldErrors);
                break;
            case 3:
                this.validateStep3(errors, emptyFieldErrors);
                break;
        }
        
        // 如果有任何错误（包括必输项为空），都阻止进入下一步
        if (errors.length > 0 || emptyFieldErrors.length > 0) {
            // 只显示非空字段错误，不显示必输项为空的错误
            if (errors.length > 0) {
                this.showError(errors.join('<br>'));
            }
            return false;
        }
        
        return true;
    }

    validateStep1(errors, emptyFieldErrors) {
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
                emptyFieldErrors.push(`${label}不能为空`);
                this.showFieldError(field, `${label}不能为空`);
            } else {
                // 其他格式验证错误会显示弹出框
                if (field === 'projectArtifactId' && !/^[a-z][a-z0-9-]*$/.test(value)) {
                    errors.push('项目构件ID只能包含小写字母、数字和连字符，且必须以字母开头');
                }
                if (field === 'groupId' && !/^[a-z][a-z0-9.]*$/.test(value)) {
                    errors.push('组织ID只能包含小写字母、数字和点号，且必须以字母开头');
                }
                if (field === 'version' && !/^\d+\.\d+\.\d+$/.test(value)) {
                    errors.push('版本号格式不正确，请使用 x.y.z 格式（如：1.0.0）');
                }
            }
        });
    }

    validateStep2(errors, emptyFieldErrors) {
        // 验证Java版本
        const javaVersion = document.querySelector('input[name="javaVersion"]:checked');
        if (!javaVersion) {
            emptyFieldErrors.push('请选择Java版本');
            this.showRadioError('javaVersion', '请选择Java版本');
        }
        
        // 验证日志框架
        const loggingFramework = document.querySelector('input[name="loggingFramework"]:checked');
        if (!loggingFramework) {
            emptyFieldErrors.push('请选择日志框架');
            this.showRadioError('loggingFramework', '请选择日志框架');
        }
        
        // 验证持久化框架
        const persistenceFramework = document.querySelector('input[name="persistenceFramework"]:checked');
        if (!persistenceFramework) {
            emptyFieldErrors.push('请选择持久化框架');
            this.showRadioError('persistenceFramework', '请选择持久化框架');
        }
    }

    validateStep3(errors, emptyFieldErrors) {
        const serviceArtifactIds = this.getServiceArtifactIds();
        
        if (serviceArtifactIds.length === 0) {
            emptyFieldErrors.push('请至少添加一个服务构件ID');
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
                emptyFieldErrors.push(`第${index + 1}个服务构件ID不能为空`);
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
            const result = await HttpClient.post('/generator/project/command/create', data);
            console.log('项目创建结果:', result);
            
            // 检查结果是否为空或无效
            if (result && (typeof result === 'object' && Object.keys(result).length > 0 || typeof result === 'string')) {
                this.showSuccess('项目创建成功！');
            } else {
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
        }
    }
    


    showSuccess(message) {
        if (typeof AlertManager !== 'undefined') {
            AlertManager.success(message);
        } else {
            // 备用方案
            const alert = document.getElementById('successAlert');
            if (alert) {
                alert.classList.add('show');
                
                setTimeout(() => {
                    alert.classList.remove('show');
                }, 5000);
            }
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
        } else {
            // 备用方案
            const alert = document.getElementById('errorAlert');
            if (alert) {
                const errorMessage = document.getElementById('errorMessage');
                if (errorMessage) {
                    errorMessage.innerHTML = message;
                }
                alert.classList.add('show');
                
                setTimeout(() => {
                    alert.classList.remove('show');
                }, 5000);
            }
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
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        }, { offset: Number.NEGATIVE_INFINITY }).element;
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