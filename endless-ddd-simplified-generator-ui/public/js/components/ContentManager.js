/**
 * 内容管理器类
 * 负责管理右侧动态内容的切换
 */
class ContentManager {
    /**
     * 初始化内容管理器
     */
    static initialize() {
        // 默认显示聚合设计页面
        this.showAggregateDesign();
    }

    /**
     * 显示项目配置页面
     */
    static showProjectConfig() {
        // 跳转到项目创建页面
        window.location.href = '../../components/ProjectWizard.html';
    }

    /**
     * 显示聚合设计页面
     */
    static showAggregateDesign() {
        // 隐藏项目配置内容
        $('#project-config-content').hide();

        // 显示聚合设计内容
        $('#domain-design-content').show();

        // 更新按钮状态
        this.updateButtonStates('domain-design');

        console.log('切换到聚合设计页面');
    }

    /**
     * 更新按钮状态
     * @param {string} activeContent - 当前活动的内容类型
     */
    static updateButtonStates(activeContent) {
        if (activeContent === 'project-config') {
            // 项目配置页面激活
            $('#project-config-content .btn-group .btn').removeClass('btn-primary btn-outline-primary');
            $('#project-config-content .btn-group .btn:first').addClass('btn-primary');
            $('#project-config-content .btn-group .btn:last').addClass('btn-outline-primary');

            $('#domain-design-content .btn-group .btn').removeClass('btn-primary btn-outline-primary');
            $('#domain-design-content .btn-group .btn:first').addClass('btn-outline-primary');
            $('#domain-design-content .btn-group .btn:last').addClass('btn-primary');
        } else {
            // 聚合设计页面激活
            $('#domain-design-content .btn-group .btn').removeClass('btn-primary btn-outline-primary');
            $('#domain-design-content .btn-group .btn:first').addClass('btn-outline-primary');
            $('#domain-design-content .btn-group .btn:last').addClass('btn-primary');

            $('#project-config-content .btn-group .btn').removeClass('btn-primary btn-outline-primary');
            $('#project-config-content .btn-group .btn:first').addClass('btn-primary');
            $('#project-config-content .btn-group .btn:last').addClass('btn-outline-primary');
        }
    }

    /**
     * 根据左侧选择自动切换内容
     * @param {string} selectionType - 选择类型 ('project-config' | 'domain-design')
     */
    static switchContentBySelection(selectionType) {
        switch (selectionType) {
            case 'project-config':
                this.showProjectConfig();
                break;
            case 'domain-design':
                this.showAggregateDesign();
                break;
            default:
                console.log('未知的选择类型:', selectionType);
        }
    }

    /**
     * 获取当前活动的内容类型
     * @returns {string} 当前活动的内容类型
     */
    static getCurrentContentType() {
        if ($('#project-config-content').is(':visible')) {
            return 'project-config';
        } else if ($('#domain-design-content').is(':visible')) {
            return 'domain-design';
        }
        return 'domain-design'; // 默认返回聚合设计
    }

    /**
     * 刷新当前内容
     */
    static refreshCurrentContent() {
        const currentType = this.getCurrentContentType();
        this.switchContentBySelection(currentType);
    }
} 
