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
            document.getElementById(`step${this.currentStep}`).classList.remove('active');
            this.currentStep++;
            document.getElementById(`step${this.currentStep}`).classList.add('active');
            this.updateStepIndicator();
            this.updateButtons();
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
            projectName: document.getElementById('projectNameInput').value,
            projectVersion: document.getElementById('projectVersionInput').value,
            groupId: document.getElementById('groupIdInput').value,
            artifactId: document.getElementById('artifactIdInput').value,
            projectDescription: document.getElementById('projectDescriptionInput').value,
            serviceName: document.getElementById('serviceNameInput').value,
            contextName: document.getElementById('contextNameInput').value,
            domainName: document.getElementById('domainNameInput').value,
            aggregateName: document.getElementById('aggregateNameInput').value,
            rootPath: document.getElementById('rootPathInput').value,
            frameworks: {
                springBoot: document.getElementById('springBootCheckbox').checked,
                mybatisPlus: document.getElementById('mybatisPlusCheckbox').checked,
                redis: document.getElementById('redisCheckbox').checked
            },
            database: document.querySelector('input[name="database"]:checked')?.id,
            codeGeneration: {
                entity: document.getElementById('enableEntityCheckbox').checked,
                value: document.getElementById('enableValueCheckbox').checked,
                enum: document.getElementById('enableEnumCheckbox').checked,
                repository: document.getElementById('enableRepositoryCheckbox').checked,
                commandHandler: document.getElementById('enableCommandHandlerCheckbox').checked,
                queryHandler: document.getElementById('enableQueryHandlerCheckbox').checked,
                transfer: document.getElementById('enableTransferCheckbox').checked
            }
        };
        
        localStorage.setItem('projectConfig', JSON.stringify(config));
        console.log('项目配置已保存:', config);
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
        document.getElementById('projectNameInput').value = config.projectName || '';
        document.getElementById('projectVersionInput').value = config.projectVersion || '1.0.0';
        document.getElementById('groupIdInput').value = config.groupId || '';
        document.getElementById('artifactIdInput').value = config.artifactId || '';
        document.getElementById('projectDescriptionInput').value = config.projectDescription || '';
        document.getElementById('serviceNameInput').value = config.serviceName || '';
        document.getElementById('contextNameInput').value = config.contextName || '';
        document.getElementById('domainNameInput').value = config.domainName || '';
        document.getElementById('aggregateNameInput').value = config.aggregateName || '';
        document.getElementById('rootPathInput').value = config.rootPath || '';
        
        // 设置复选框
        if (config.frameworks) {
            document.getElementById('springBootCheckbox').checked = config.frameworks.springBoot;
            document.getElementById('mybatisPlusCheckbox').checked = config.frameworks.mybatisPlus;
            document.getElementById('redisCheckbox').checked = config.frameworks.redis;
        }
        
        // 设置单选按钮
        if (config.database) {
            document.getElementById(config.database).checked = true;
        }
        
        // 设置代码生成选项
        if (config.codeGeneration) {
            document.getElementById('enableEntityCheckbox').checked = config.codeGeneration.entity;
            document.getElementById('enableValueCheckbox').checked = config.codeGeneration.value;
            document.getElementById('enableEnumCheckbox').checked = config.codeGeneration.enum;
            document.getElementById('enableRepositoryCheckbox').checked = config.codeGeneration.repository;
            document.getElementById('enableCommandHandlerCheckbox').checked = config.codeGeneration.commandHandler;
            document.getElementById('enableQueryHandlerCheckbox').checked = config.codeGeneration.queryHandler;
            document.getElementById('enableTransferCheckbox').checked = config.codeGeneration.transfer;
        }
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