/**
 * 主应用类
 * 负责整合所有模块和初始化应用
 */
class App {
    /**
     * 初始化应用
     */
    static async initialize() {
        try {
            console.log('开始初始化应用...');
            
            // 初始化各个模块
            await this.initializeModules();
            
            // 绑定全局事件
            this.bindGlobalEvents();
            
            // 初始化主题
            this.initializeTheme();
            
            console.log('应用初始化完成');
        } catch (error) {
            console.error('应用初始化失败:', error);
            AlertManager.error('应用初始化失败，请刷新页面重试');
        }
    }

    /**
     * 初始化各个模块
     */
    static async initializeModules() {
        // 先加载组件
        if (typeof ComponentLoader !== 'undefined') {
            try {
                await ComponentLoader.loadAllComponents();
                console.log('所有组件加载完成');
            } catch (error) {
                console.error('组件加载失败:', error);
            }
        }

        // 组件加载完成后再初始化其他模块
        if (typeof DataManager !== 'undefined') {
            DataManager.initialize();
        }

        // 初始化标签页管理器
        if (typeof TabManager !== 'undefined') {
            TabManager.initialize();
        }

        // 初始化项目配置向导
        if (typeof ProjectConfigWizard !== 'undefined') {
            ProjectConfigWizard.initialize();
        }

        // 初始化内容管理器
        if (typeof ContentManager !== 'undefined') {
            ContentManager.initialize();
        }

        // 初始化项目配置管理器
        if (typeof ProjectConfigManager !== 'undefined') {
            ProjectConfigManager.initialize();
        }

        // 初始化新建项目管理器
        if (typeof NewItemManager !== 'undefined') {
            NewItemManager.initialize();
        }
    }

    /**
     * 绑定全局事件
     */
    static bindGlobalEvents() {
        // 表单提交事件
        $('#yamlForm').on('submit', function(e) {
            e.preventDefault();
            App.handleFormSubmit();
        });

        // 代码生成按钮事件
        $(document).on('click', '#generateCodeButton', function() {
            App.handleCodeGeneration();
        });

        // 更新聚合按钮事件
        $(document).on('click', '#updateAggregateButton', function() {
            App.handleUpdateAggregate();
        });

        // 添加字段按钮事件
        $(document).on('click', '.add-domain-field', function() {
            App.handleAddField();
        });

        // 删除字段按钮事件
        $(document).on('click', '.remove-field', function() {
            App.handleRemoveField(this);
        });

        // 添加实体按钮事件
        $(document).on('click', '.add-entities', function() {
            App.handleAddEntity();
        });

        // 添加枚举按钮事件
        $(document).on('click', '.add-enum', function() {
            App.handleAddEnum();
        });

        // 添加值对象按钮事件
        $(document).on('click', '.add-value', function() {
            App.handleAddValueObject();
        });

        // 添加方法按钮事件
        $(document).on('click', '.add-method', function() {
            App.handleAddMethod();
        });

        // 添加传输对象按钮事件
        $(document).on('click', '.add-transfers', function() {
            App.handleAddTransferObject();
        });
    }

    /**
     * 初始化主题
     */
    static initializeTheme() {
        // 主题切换功能已在theme.toggle.js中实现
        console.log('主题初始化完成');
    }

    /**
     * 处理表单提交
     */
    static handleFormSubmit() {
        const formData = DataManager.getFormData();
        const validation = DataTransform.validateFormData(formData);
        
        if (!validation.valid) {
            AlertManager.error('表单验证失败：\n' + validation.errors.join('\n'));
            return;
        }

        const cleanedData = DataTransform.cleanFormData(formData);
        
        DataManager.submitFormData(
            function(response) {
                AlertManager.success('代码生成成功！');
                console.log('生成结果:', response);
            },
            function(error) {
                AlertManager.error('代码生成失败：' + error);
            }
        );
    }

    /**
     * 处理代码生成
     */
    static handleCodeGeneration() {
        AlertManager.confirm(
            '确定要生成代码吗？',
            function() {
                App.handleFormSubmit();
            }
        );
    }

    /**
     * 处理更新聚合
     */
    static handleUpdateAggregate() {
        const formData = DataManager.getFormData();
        const validation = DataTransform.validateFormData(formData);
        
        if (!validation.valid) {
            AlertManager.error('表单验证失败：\n' + validation.errors.join('\n'));
            return;
        }

        const cleanedData = DataTransform.cleanFormData(formData);
        
        $.ajax({
            type: 'POST',
            url: '/aggregate/update',
            data: JSON.stringify(cleanedData),
            contentType: 'application/json;type=utf-8',
            success: function(response) {
                AlertManager.success('聚合信息更新成功！');
                console.log('更新结果:', response);
            },
            error: function(xhr, status, error) {
                AlertManager.error('聚合信息更新失败：' + error);
            }
        });
    }

    /**
     * 处理添加字段
     */
    static handleAddField() {
        const fieldsContainer = $('.field-container .fields');
        const fieldIndex = fieldsContainer.children().length;
        const fieldHtml = DOMCore.generateFieldHtml({}, `fields[${fieldIndex}]`);
        fieldsContainer.append(fieldHtml);
    }

    /**
     * 处理删除字段
     * @param {Element} button - 删除按钮元素
     */
    static handleRemoveField(button) {
        $(button).closest('.field').remove();
        // 重新计算字段索引
        const fieldsContainer = $('.field-container .fields');
        DOMCore.updateIndex(fieldsContainer, 'fields');
    }

    /**
     * 处理添加实体
     */
    static handleAddEntity() {
        const container = '.entities-container';
        const index = $(container + ' .entities').length + 1;
        DOMCore.addDynamicElement(container, 'entities', {}, index);
    }

    /**
     * 处理添加枚举
     */
    static handleAddEnum() {
        const container = '.enums-container';
        const index = $(container + ' .enums').length + 1;
        DOMCore.addDynamicElement(container, 'enums', {}, index);
    }

    /**
     * 处理添加值对象
     */
    static handleAddValueObject() {
        const container = '.values-container';
        const index = $(container + ' .values').length + 1;
        DOMCore.addDynamicElement(container, 'values', {}, index);
    }

    /**
     * 处理添加方法
     */
    static handleAddMethod() {
        const container = '.methods-container';
        const index = $(container + ' .methods').length + 1;
        DOMCore.addDynamicElement(container, 'methods', {}, index);
    }

    /**
     * 处理添加传输对象
     */
    static handleAddTransferObject() {
        const container = '.transfers-container';
        const index = $(container + ' .transfers').length + 1;
        DOMCore.addDynamicElement(container, 'transfers', {}, index);
    }

    /**
     * 获取应用状态
     * @returns {Object} 应用状态对象
     */
    static getAppState() {
        return {
            activeTab: TabManager.getCurrentActiveTab(),
            formData: DataManager.getFormData(),
            componentsLoaded: ComponentLoader.getLoadedComponents()
        };
    }

    /**
     * 重置应用状态
     */
    static resetAppState() {
        // 重置表单
        $('#yamlForm')[0].reset();
        
        // 清空动态内容
        $('.field-container .fields').empty();
        $('.entities-container .entities').empty();
        $('.enums-container .enums').empty();
        $('.values-container .values').empty();
        $('.methods-container .methods').empty();
        $('.transfers-container .transfers').empty();
        
        // 重置标签页
        TabManager.switchTab('domain');
        
        AlertManager.success('应用状态已重置');
    }
}

// 页面加载完成后初始化应用
$(document).ready(function() {
    App.initialize();
}); 
