/**
 * 项目配置管理器类
 * 负责处理项目配置相关的功能
 */
class ProjectConfigManager {
    /**
     * 初始化项目配置管理器
     */
    static initialize() {
        this.loadProjectConfig();
    }

    /**
     * 加载项目配置
     */
    static loadProjectConfig() {
        // 从localStorage加载配置
        const config = this.getProjectConfig();
        if (config) {
            this.fillProjectConfig(config);
        }
    }

    /**
     * 保存项目配置
     */
    static saveConfig() {
        const config = this.getProjectConfigFromForm();
        
        // 保存到localStorage
        localStorage.setItem('projectConfig', JSON.stringify(config));
        
        // 显示成功消息
        AlertManager.success('项目配置保存成功！');
        
        console.log('项目配置已保存:', config);
    }

    /**
     * 从表单获取项目配置
     * @returns {Object} 项目配置对象
     */
    static getProjectConfigFromForm() {
        return {
            projectName: $('#projectNameInput').val(),
            projectVersion: $('#projectVersionInput').val(),
            basePackage: $('#basePackageInput').val(),
            outputPath: $('#outputPathInput').val(),
            projectDescription: $('#projectDescriptionInput').val(),
            enableLombok: $('#enableLombokCheckbox').is(':checked'),
            enableValidation: $('#enableValidationCheckbox').is(':checked'),
            enableSwagger: $('#enableSwaggerCheckbox').is(':checked'),
            enableLogging: $('#enableLoggingCheckbox').is(':checked')
        };
    }

    /**
     * 填充项目配置到表单
     * @param {Object} config - 项目配置对象
     */
    static fillProjectConfig(config) {
        $('#projectNameInput').val(config.projectName || '');
        $('#projectVersionInput').val(config.projectVersion || '');
        $('#basePackageInput').val(config.basePackage || '');
        $('#outputPathInput').val(config.outputPath || '');
        $('#projectDescriptionInput').val(config.projectDescription || '');
        
        $('#enableLombokCheckbox').prop('checked', config.enableLombok !== false);
        $('#enableValidationCheckbox').prop('checked', config.enableValidation !== false);
        $('#enableSwaggerCheckbox').prop('checked', config.enableSwagger !== false);
        $('#enableLoggingCheckbox').prop('checked', config.enableLogging !== false);
    }

    /**
     * 获取项目配置
     * @returns {Object|null} 项目配置对象
     */
    static getProjectConfig() {
        const configStr = localStorage.getItem('projectConfig');
        if (configStr) {
            try {
                return JSON.parse(configStr);
            } catch (error) {
                console.error('解析项目配置失败:', error);
                return null;
            }
        }
        return null;
    }

    /**
     * 重置项目配置
     */
    static resetConfig() {
        // 清空表单
        $('#projectNameInput').val('');
        $('#projectVersionInput').val('');
        $('#basePackageInput').val('');
        $('#outputPathInput').val('');
        $('#projectDescriptionInput').val('');
        
        // 重置复选框为默认状态
        $('#enableLombokCheckbox').prop('checked', true);
        $('#enableValidationCheckbox').prop('checked', true);
        $('#enableSwaggerCheckbox').prop('checked', true);
        $('#enableLoggingCheckbox').prop('checked', true);
        
        // 清除localStorage
        localStorage.removeItem('projectConfig');
        
        AlertManager.info('项目配置已重置');
        console.log('项目配置已重置');
    }

    /**
     * 验证项目配置
     * @returns {boolean} 验证结果
     */
    static validateConfig() {
        const config = this.getProjectConfigFromForm();
        
        if (!config.projectName) {
            AlertManager.error('请输入项目名称');
            return false;
        }
        
        if (!config.basePackage) {
            AlertManager.error('请输入基础包名');
            return false;
        }
        
        if (!config.outputPath) {
            AlertManager.error('请输入输出路径');
            return false;
        }
        
        return true;
    }

    /**
     * 导出项目配置
     */
    static exportConfig() {
        const config = this.getProjectConfig();
        if (!config) {
            AlertManager.warning('没有可导出的配置');
            return;
        }
        
        const configStr = JSON.stringify(config, null, 2);
        const blob = new Blob([configStr], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        
        const a = document.createElement('a');
        a.href = url;
        a.download = 'project-config.json';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
        
        AlertManager.success('项目配置导出成功');
    }

    /**
     * 导入项目配置
     * @param {File} file - 配置文件
     */
    static importConfig(file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            try {
                const config = JSON.parse(e.target.result);
                ProjectConfigManager.fillProjectConfig(config);
                ProjectConfigManager.saveConfig();
                AlertManager.success('项目配置导入成功');
            } catch (error) {
                AlertManager.error('配置文件格式错误');
                console.error('导入配置失败:', error);
            }
        };
        reader.readAsText(file);
    }
} 