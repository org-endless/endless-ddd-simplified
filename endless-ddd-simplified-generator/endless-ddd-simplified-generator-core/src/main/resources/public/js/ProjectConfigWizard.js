/**
 * 项目配置向导类
 * 负责管理项目配置的4步引导流程
 */
class ProjectConfigWizard {
    static currentStep = 1;
    static totalSteps = 4;

    /**
     * 显示项目配置向导
     */
    static show() {
        document.getElementById('mainInterface').style.display = 'none';
        document.getElementById('projectConfigWizard').style.display = 'block';
        this.currentStep = 1;
        this.updateStepIndicator();
        this.updateButtons();
    }

    /**
     * 隐藏项目配置向导
     */
    static hide() {
        document.getElementById('projectConfigWizard').style.display = 'none';
        document.getElementById('mainInterface').style.display = 'block';
    }

    /**
     * 下一步
     */
    static nextStep() {
        if (this.currentStep < this.totalSteps) {
            // 在进入下一步前验证当前步骤
            if (!this.validateCurrentStep()) {
                return;
            }
            
            document.getElementById(`step${this.currentStep}`).classList.remove('active');
            this.currentStep++;
            document.getElementById(`step${this.currentStep}`).classList.add('active');
            this.updateStepIndicator();
            this.updateButtons();
            
            // 如果是最后一步，生成配置摘要
            if (this.currentStep === this.totalSteps) {
                this.generateConfigSummary();
            }
        }
    }

    /**
     * 上一步
     */
    static previousStep() {
        if (this.currentStep > 1) {
            document.getElementById(`step${this.currentStep}`).classList.remove('active');
            this.currentStep--;
            document.getElementById(`step${this.currentStep}`).classList.add('active');
            this.updateStepIndicator();
            this.updateButtons();
        }
    }

    /**
     * 验证当前步骤
     */
    static validateCurrentStep() {
        switch (this.currentStep) {
            case 1:
                return this.validateStep1();
            case 2:
                return this.validateStep2();
            case 3:
                return this.validateStep3();
            default:
                return true;
        }
    }

    /**
     * 验证步骤1：项目基本信息
     */
    static validateStep1() {
        const requiredFields = [
            'projectArtifactIdInput',
            'groupIdInput',
            'projectNameInput',
            'projectVersionInput',
            'projectAuthorInput',
            'rootPathInput',
            'basePackageInput'
        ];
        
        for (const fieldId of requiredFields) {
            const field = document.getElementById(fieldId);
            if (!field || !field.value.trim()) {
                AlertManager.show(`请填写${field.previousElementSibling.textContent}`, 'warning');
                field.focus();
                return false;
            }
        }
        
        const description = document.getElementById('projectDescriptionInput');
        if (!description || !description.value.trim()) {
            AlertManager.show('请填写项目描述', 'warning');
            description.focus();
            return false;
        }
        
        return true;
    }

    /**
     * 验证步骤2：技术栈配置
     */
    static validateStep2() {
        const javaVersion = document.querySelector('input[name="javaVersion"]:checked');
        const loggingFramework = document.querySelector('input[name="loggingFramework"]:checked');
        const persistenceFramework = document.querySelector('input[name="persistenceFramework"]:checked');
        
        if (!javaVersion) {
            AlertManager.show('请选择Java版本', 'warning');
            return false;
        }
        
        if (!loggingFramework) {
            AlertManager.show('请选择日志框架', 'warning');
            return false;
        }
        
        if (!persistenceFramework) {
            AlertManager.show('请选择持久化框架', 'warning');
            return false;
        }
        
        return true;
    }

    /**
     * 验证步骤3：服务配置
     */
    static validateStep3() {
        const serviceArtifactIds = this.getServiceArtifactIds();
        if (serviceArtifactIds.length === 0) {
            AlertManager.show('请至少添加一个服务构件ID', 'warning');
            return false;
        }
        
        return true;
    }

    /**
     * 更新步骤指示器
     */
    static updateStepIndicator() {
        const steps = document.querySelectorAll('.step-indicator .step');
        steps.forEach((step, index) => {
            step.classList.remove('active', 'completed');
            if (index + 1 === this.currentStep) {
                step.classList.add('active');
            } else if (index + 1 < this.currentStep) {
                step.classList.add('completed');
            }
        });
    }

    /**
     * 更新按钮状态
     */
    static updateButtons() {
        const prevBtn = document.getElementById('prevStepButton');
        const nextBtn = document.getElementById('nextStepButton');
        const finishBtn = document.getElementById('finishWizardButton');
        
        prevBtn.style.display = this.currentStep === 1 ? 'none' : 'inline-block';
        nextBtn.style.display = this.currentStep === this.totalSteps ? 'none' : 'inline-block';
        finishBtn.style.display = this.currentStep === this.totalSteps ? 'inline-block' : 'none';
    }

    /**
     * 完成配置
     */
    static finish() {
        this.saveProjectConfig();
        this.hide();
        AlertManager.show('项目配置已保存！', 'success');
    }

    /**
     * 保存项目配置
     */
    static saveProjectConfig() {
        const config = {
            projectArtifactId: document.getElementById('projectArtifactIdInput').value,
            groupId: document.getElementById('groupIdInput').value,
            name: document.getElementById('projectNameInput').value,
            description: document.getElementById('projectDescriptionInput').value,
            version: document.getElementById('projectVersionInput').value,
            author: document.getElementById('projectAuthorInput').value,
            rootPath: document.getElementById('rootPathInput').value,
            basePackage: document.getElementById('basePackageInput').value,
            enableSpringDoc: document.getElementById('enableSpringDocCheckbox').checked ? 'true' : 'false',
            javaVersion: document.querySelector('input[name="javaVersion"]:checked').value,
            loggingFramework: document.querySelector('input[name="loggingFramework"]:checked').value,
            persistenceFramework: document.querySelector('input[name="persistenceFramework"]:checked').value,
            serviceArtifactIds: this.getServiceArtifactIds()
        };
        
        localStorage.setItem('projectConfig', JSON.stringify(config));
        console.log('项目配置已保存:', config);
        
        // 发送到后端API
        this.sendToBackend(config);
    }

    /**
     * 发送配置到后端
     */
    static async sendToBackend(config) {
        try {
            const response = await fetch('/api/generator/project', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(config)
            });
            
            if (response.ok) {
                const result = await response.json();
                AlertManager.show('项目创建成功！', 'success');
                console.log('项目创建结果:', result);
            } else {
                const error = await response.text();
                AlertManager.show(`项目创建失败: ${error}`, 'error');
            }
        } catch (error) {
            console.error('发送配置到后端失败:', error);
            AlertManager.show('网络错误，请稍后重试', 'error');
        }
    }

    /**
     * 获取服务构件ID列表
     */
    static getServiceArtifactIds() {
        const inputs = document.querySelectorAll('#serviceArtifactIdsContainer input');
        return Array.from(inputs).map(input => input.value).filter(value => value.trim() !== '');
    }

    /**
     * 生成配置摘要
     */
    static generateConfigSummary() {
        const summary = document.getElementById('configSummary');
        const data = {
            projectArtifactId: document.getElementById('projectArtifactIdInput').value,
            groupId: document.getElementById('groupIdInput').value,
            name: document.getElementById('projectNameInput').value,
            description: document.getElementById('projectDescriptionInput').value,
            version: document.getElementById('projectVersionInput').value,
            author: document.getElementById('projectAuthorInput').value,
            rootPath: document.getElementById('rootPathInput').value,
            basePackage: document.getElementById('basePackageInput').value,
            enableSpringDoc: document.getElementById('enableSpringDocCheckbox').checked ? 'true' : 'false',
            javaVersion: document.querySelector('input[name="javaVersion"]:checked').value,
            loggingFramework: document.querySelector('input[name="loggingFramework"]:checked').value,
            persistenceFramework: document.querySelector('input[name="persistenceFramework"]:checked').value,
            serviceArtifactIds: this.getServiceArtifactIds()
        };
        
        summary.innerHTML = `
            <div class="row">
                <div class="col-md-6">
                    <p><strong>项目构件ID:</strong> ${data.projectArtifactId}</p>
                    <p><strong>组织ID:</strong> ${data.groupId}</p>
                    <p><strong>项目名称:</strong> ${data.name}</p>
                    <p><strong>项目版本:</strong> ${data.version}</p>
                    <p><strong>项目作者:</strong> ${data.author}</p>
                </div>
                <div class="col-md-6">
                    <p><strong>项目根路径:</strong> ${data.rootPath}</p>
                    <p><strong>基础包名:</strong> ${data.basePackage}</p>
                    <p><strong>Java版本:</strong> ${data.javaVersion}</p>
                    <p><strong>日志框架:</strong> ${data.loggingFramework}</p>
                    <p><strong>持久化框架:</strong> ${data.persistenceFramework}</p>
                    <p><strong>启用Spring Doc:</strong> ${data.enableSpringDoc}</p>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <p><strong>项目描述:</strong> ${data.description}</p>
                    <p><strong>服务构件ID列表:</strong></p>
                    <ul>
                        ${data.serviceArtifactIds.map(id => `<li>${id}</li>`).join('')}
                    </ul>
                </div>
            </div>
        `;
    }

    /**
     * 加载项目配置
     */
    static loadProjectConfig() {
        const savedConfig = localStorage.getItem('projectConfig');
        if (savedConfig) {
            const config = JSON.parse(savedConfig);
            this.fillFormWithConfig(config);
        }
    }

    /**
     * 用配置数据填充表单
     */
    static fillFormWithConfig(config) {
        // 填充基本信息
        if (document.getElementById('projectArtifactIdInput')) {
            document.getElementById('projectArtifactIdInput').value = config.projectArtifactId || '';
        }
        if (document.getElementById('groupIdInput')) {
            document.getElementById('groupIdInput').value = config.groupId || '';
        }
        if (document.getElementById('projectNameInput')) {
            document.getElementById('projectNameInput').value = config.name || '';
        }
        if (document.getElementById('projectVersionInput')) {
            document.getElementById('projectVersionInput').value = config.version || '1.0.0';
        }
        if (document.getElementById('projectAuthorInput')) {
            document.getElementById('projectAuthorInput').value = config.author || '';
        }
        if (document.getElementById('rootPathInput')) {
            document.getElementById('rootPathInput').value = config.rootPath || '';
        }
        if (document.getElementById('basePackageInput')) {
            document.getElementById('basePackageInput').value = config.basePackage || '';
        }
        if (document.getElementById('projectDescriptionInput')) {
            document.getElementById('projectDescriptionInput').value = config.description || '';
        }
        
        // 设置技术栈选项
        if (config.javaVersion) {
            const javaVersionRadio = document.querySelector(`input[name="javaVersion"][value="${config.javaVersion}"]`);
            if (javaVersionRadio) {
                javaVersionRadio.checked = true;
            }
        }
        
        if (config.loggingFramework) {
            const loggingFrameworkRadio = document.querySelector(`input[name="loggingFramework"][value="${config.loggingFramework}"]`);
            if (loggingFrameworkRadio) {
                loggingFrameworkRadio.checked = true;
            }
        }
        
        if (config.persistenceFramework) {
            const persistenceFrameworkRadio = document.querySelector(`input[name="persistenceFramework"][value="${config.persistenceFramework}"]`);
            if (persistenceFrameworkRadio) {
                persistenceFrameworkRadio.checked = true;
            }
        }
        
        if (config.enableSpringDoc) {
            const enableSpringDocCheckbox = document.getElementById('enableSpringDocCheckbox');
            if (enableSpringDocCheckbox) {
                enableSpringDocCheckbox.checked = config.enableSpringDoc === 'true';
            }
        }
        
        // 设置服务构件ID列表
        if (config.serviceArtifactIds && config.serviceArtifactIds.length > 0) {
            this.fillServiceArtifactIds(config.serviceArtifactIds);
        }
    }

    /**
     * 填充服务构件ID列表
     */
    static fillServiceArtifactIds(serviceArtifactIds) {
        const container = document.getElementById('serviceArtifactIdsContainer');
        if (!container) return;
        
        // 清空现有内容
        container.innerHTML = '';
        
        // 添加服务构件ID
        serviceArtifactIds.forEach((id, index) => {
            if (index === 0) {
                // 第一个输入框
                const firstInput = container.querySelector('input');
                if (firstInput) {
                    firstInput.value = id;
                }
            } else {
                // 添加新的输入框
                ProjectConfigWizard.addServiceArtifactId(id);
            }
        });
    }

    /**
     * 添加服务构件ID输入框
     */
    static addServiceArtifactId(value = '') {
        const container = document.getElementById('serviceArtifactIdsContainer');
        if (!container) return;
        
        const newInput = document.createElement('div');
        newInput.className = 'input-group mb-2';
        newInput.innerHTML = `
            <input class="form-control" type="text" placeholder="输入服务构件ID" value="${value}">
            <button class="btn btn-outline-danger" type="button" onclick="ProjectConfigWizard.removeServiceArtifactId(this)">
                <i class="bi bi-trash"></i>
            </button>
        `;
        container.appendChild(newInput);
    }

    /**
     * 移除服务构件ID输入框
     */
    static removeServiceArtifactId(button) {
        button.parentElement.remove();
    }

    /**
     * 初始化向导
     */
    static initialize() {
        this.updateStepIndicator();
        this.updateButtons();
        this.loadProjectConfig();
    }
} 