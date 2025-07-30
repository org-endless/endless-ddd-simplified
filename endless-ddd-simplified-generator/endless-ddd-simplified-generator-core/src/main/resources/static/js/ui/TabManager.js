/**
 * 标签页管理器类
 * 负责标签页切换和状态管理
 */
class TabManager {
    /**
     * 初始化标签页管理器
     */
    static initialize() {
        this.bindTabEvents();
        this.initializeActiveTab();
    }

    /**
     * 绑定标签页事件
     */
    static bindTabEvents() {
        // 使用Bootstrap 5的标签页事件
        $('.nav-tabs .nav-link').on('click', function (e) {
            e.preventDefault();
            const tabId = this.id.split('-')[0];
            TabManager.switchTab(tabId);
        });
    }

    /**
     * 初始化活动标签页
     */
    static initializeActiveTab() {
        // 从URL参数或localStorage获取上次活动的标签页
        const activeTab = this.getActiveTabFromStorage();
        if (activeTab) {
            this.switchTab(activeTab);
        }
    }

    /**
     * 切换标签页
     * @param {string} tabId - 标签页ID
     */
    static switchTab(tabId) {
        // 更新导航状态
        $('.nav-tabs .nav-link').removeClass('active').attr('aria-selected', 'false');
        $(`#${tabId}-tab`).addClass('active').attr('aria-selected', 'true');

        // 更新内容区域
        $('.tab-pane').removeClass('show active');
        $(`#${tabId}`).addClass('show active');

        // 保存活动标签页状态
        this.saveActiveTab(tabId);

        // 触发标签页切换事件
        this.onTabChange(tabId);
    }

    /**
     * 标签页切换事件处理
     * @param {string} tabId - 标签页ID
     */
    static onTabChange(tabId) {
        // 根据标签页类型执行不同的初始化逻辑
        switch (tabId) {
            case 'domain':
                this.initializeAggregateTab();
                break;
            case 'entities':
                this.initializeEntitiesTab();
                break;
            case 'enums':
                this.initializeEnumsTab();
                break;
            case 'values':
                this.initializeValuesTab();
                break;
            case 'methods':
                this.initializeMethodsTab();
                break;
            case 'transfers':
                this.initializeTransfersTab();
                break;
            default:
                console.log(`切换到标签页: ${tabId}`);
        }
    }

    /**
     * 初始化聚合标签页
     */
    static initializeAggregateTab() {
        // 聚合标签页的特殊初始化逻辑
        console.log('初始化聚合标签页');
    }

    /**
     * 初始化实体标签页
     */
    static initializeEntitiesTab() {
        // 实体标签页的特殊初始化逻辑
        console.log('初始化实体标签页');
    }

    /**
     * 初始化枚举标签页
     */
    static initializeEnumsTab() {
        // 枚举标签页的特殊初始化逻辑
        console.log('初始化枚举标签页');
    }

    /**
     * 初始化值对象标签页
     */
    static initializeValuesTab() {
        // 值对象标签页的特殊初始化逻辑
        console.log('初始化值对象标签页');
    }

    /**
     * 初始化方法标签页
     */
    static initializeMethodsTab() {
        // 方法标签页的特殊初始化逻辑
        console.log('初始化方法标签页');
    }

    /**
     * 初始化传输对象标签页
     */
    static initializeTransfersTab() {
        // 传输对象标签页的特殊初始化逻辑
        console.log('初始化传输对象标签页');
    }

    /**
     * 保存活动标签页状态
     * @param {string} tabId - 标签页ID
     */
    static saveActiveTab(tabId) {
        localStorage.setItem('activeTab', tabId);
    }

    /**
     * 从存储中获取活动标签页
     * @returns {string|null} 活动标签页ID
     */
    static getActiveTabFromStorage() {
        return localStorage.getItem('activeTab');
    }

    /**
     * 获取当前活动标签页
     * @returns {string} 当前活动标签页ID
     */
    static getCurrentActiveTab() {
        const activeTab = $('.nav-tabs .nav-link.active');
        return activeTab.length > 0 ? activeTab.attr('id').split('-')[0] : 'domain';
    }

    /**
     * 检查标签页是否存在
     * @param {string} tabId - 标签页ID
     * @returns {boolean} 是否存在
     */
    static tabExists(tabId) {
        return $(`#${tabId}-tab`).length > 0;
    }

    /**
     * 添加新标签页
     * @param {string} tabId - 标签页ID
     * @param {string} tabName - 标签页名称
     * @param {string} iconClass - 图标类名
     */
    static addTab(tabId, tabName, iconClass = 'bi-file-text') {
        const tabHtml = `
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" id="${tabId}-tab" role="tab">
                    <i class="bi ${iconClass}"></i> ${tabName}
                </a>
            </li>
        `;
        
        const contentHtml = `
            <div class="tab-pane fade" id="${tabId}" role="tabpanel">
                <div class="config-section">
                    <h3><i class="bi ${iconClass}"></i> ${tabName}</h3>
                    <div class="content-container"></div>
                </div>
            </div>
        `;

        // 添加到导航
        $('.nav-tabs').append(tabHtml);
        
        // 添加到内容区域
        $('.tab-content').append(contentHtml);
        
        // 重新绑定事件
        this.bindTabEvents();
    }

    /**
     * 移除标签页
     * @param {string} tabId - 标签页ID
     */
    static removeTab(tabId) {
        $(`#${tabId}-tab`).parent().remove();
        $(`#${tabId}`).remove();
        
        // 如果移除的是当前活动标签页，切换到第一个标签页
        if (this.getCurrentActiveTab() === tabId) {
            this.switchTab('domain');
        }
    }

    /**
     * 刷新标签页内容
     * @param {string} tabId - 标签页ID
     */
    static refreshTabContent(tabId) {
        const tabContent = $(`#${tabId}`);
        if (tabContent.length > 0) {
            // 触发标签页刷新事件
            this.onTabChange(tabId);
        }
    }
} 
