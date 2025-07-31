/**
 * 列表管理器类
 * 统一处理列表相关的功能，包括拖拽、添加、删除等
 */
class ListManager {
    
    /**
     * 初始化列表
     * @param {string} containerId - 容器ID
     * @param {Object} options - 配置选项
     */
    static init(containerId, options = {}) {
        const container = document.getElementById(containerId);
        if (!container) {
            console.error(`容器 ${containerId} 不存在`);
            return;
        }
        
        this.container = container;
        this.options = {
            itemSelector: '.list-item',
            dragHandleSelector: '.drag-handle',
            removeButtonSelector: '.btn-remove',
            addButtonSelector: '.btn-add',
            inputSelector: '.list-input',
            ...options
        };
        
        this.setupDragAndDrop();
        this.setupEventListeners();
    }
    
    /**
     * 设置拖拽功能
     */
    static setupDragAndDrop() {
        if (!this.container) return;
        
        // 清除现有的事件监听器
        this.cleanupEventListeners();
        
        // 为容器添加拖拽事件监听器
        this.container._dragOverHandler = (e) => {
            e.preventDefault();
            const draggingItem = document.querySelector('.dragging');
            if (draggingItem) {
                const afterElement = this.getDragAfterElement(this.container, e.clientY);
                if (afterElement) {
                    this.container.insertBefore(draggingItem, afterElement);
                } else {
                    this.container.appendChild(draggingItem);
                }
            }
        };
        
        this.container._dropHandler = (e) => {
            e.preventDefault();
            // 清除所有拖拽状态
            document.querySelectorAll('.list-item').forEach(item => {
                item.classList.remove('drag-enter', 'drag-over');
            });
        };
        
        // 为容器添加拖拽事件监听器
        this.container.addEventListener('dragover', this.container._dragOverHandler);
        this.container.addEventListener('drop', this.container._dropHandler);
        
        // 为每个列表项添加拖拽事件监听器
        this.updateDragListeners();
    }
    
    /**
     * 更新拖拽监听器
     */
    static updateDragListeners() {
        const items = this.container.querySelectorAll(this.options.itemSelector);
        
        items.forEach((item, index) => {
            // 更新data-index属性
            item.setAttribute('data-index', index);
            
            // 移除现有的事件监听器
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
    
    /**
     * 处理拖拽开始
     * @param {DragEvent} e - 拖拽事件
     */
    static handleDragStart(e) {
        e.target.classList.add('dragging');
    }
    
    /**
     * 处理拖拽结束
     * @param {DragEvent} e - 拖拽事件
     */
    static handleDragEnd(e) {
        e.target.classList.remove('dragging');
        this.updateDragListeners();
    }
    
    /**
     * 获取拖拽后的元素位置
     * @param {HTMLElement} container - 容器元素
     * @param {number} y - 鼠标Y坐标
     * @returns {HTMLElement|null} 目标元素
     */
    static getDragAfterElement(container, y) {
        const draggableElements = [...container.querySelectorAll(`${this.options.itemSelector}:not(.dragging)`)];
        
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
    
    /**
     * 添加列表项
     * @param {string} value - 初始值
     * @param {Object} itemData - 项目数据
     */
    static addItem(value = '', itemData = {}) {
        const item = this.createItemElement(value, itemData);
        this.container.appendChild(item);
        this.updateDragListeners();
        
        // 触发自定义事件
        this.container.dispatchEvent(new CustomEvent('itemAdded', {
            detail: { item, value, itemData }
        }));
    }
    
    /**
     * 创建列表项元素
     * @param {string} value - 初始值
     * @param {Object} itemData - 项目数据
     * @returns {HTMLElement} 列表项元素
     */
    static createItemElement(value = '', itemData = {}) {
        const item = document.createElement('div');
        item.className = 'list-item';
        item.setAttribute('draggable', 'true');
        
        item.innerHTML = `
            <div class="drag-handle">
                <i class="bi bi-grip-vertical"></i>
            </div>
            <input type="text" placeholder="${itemData.placeholder || '输入内容'}" class="list-input" value="${value}">
            <button type="button" class="btn-remove" onclick="ListManager.removeItem(this)">
                <i class="bi bi-trash"></i>
            </button>
        `;
        
        return item;
    }
    
    /**
     * 删除列表项
     * @param {HTMLElement} button - 删除按钮
     */
    static removeItem(button) {
        const item = button.closest(this.options.itemSelector);
        if (!item) return;
        
        // 检查最小数量限制
        const currentItems = this.container.querySelectorAll(this.options.itemSelector);
        if (this.options.minItems && currentItems.length <= this.options.minItems) {
            if (typeof AlertManager !== 'undefined') {
                AlertManager.show(`至少需要保留 ${this.options.minItems} 个项目`, 'warning');
            }
            return;
        }
        
        // 清理要删除项目的事件监听器
        if (item._dragStartHandler) {
            item.removeEventListener('dragstart', item._dragStartHandler);
            delete item._dragStartHandler;
        }
        if (item._dragEndHandler) {
            item.removeEventListener('dragend', item._dragEndHandler);
            delete item._dragEndHandler;
        }
        
        item.remove();
        this.updateDragListeners();
        
        // 触发自定义事件
        this.container.dispatchEvent(new CustomEvent('itemRemoved', {
            detail: { item }
        }));
    }
    
    /**
     * 获取所有列表项的值
     * @returns {Array<string>} 值数组
     */
    static getValues() {
        const inputs = this.container.querySelectorAll(this.options.inputSelector);
        return Array.from(inputs).map(input => input.value.trim()).filter(value => value);
    }
    
    /**
     * 设置列表项的值
     * @param {Array<string>} values - 值数组
     */
    static setValues(values) {
        // 清空现有项目
        this.clear();
        
        // 添加新项目
        values.forEach(value => {
            this.addItem(value);
        });
    }
    
    /**
     * 清空列表
     */
    static clear() {
        const items = this.container.querySelectorAll(this.options.itemSelector);
        items.forEach(item => {
            if (item._dragStartHandler) {
                item.removeEventListener('dragstart', item._dragStartHandler);
                delete item._dragStartHandler;
            }
            if (item._dragEndHandler) {
                item.removeEventListener('dragend', item._dragEndHandler);
                delete item._dragEndHandler;
            }
            item.remove();
        });
    }
    
    /**
     * 设置事件监听器
     */
    static setupEventListeners() {
        // 监听输入框变化
        this.container.addEventListener('input', (e) => {
            if (e.target.matches(this.options.inputSelector)) {
                this.container.dispatchEvent(new CustomEvent('itemChanged', {
                    detail: { item: e.target.closest(this.options.itemSelector), value: e.target.value }
                }));
            }
        });
    }
    
    /**
     * 清理事件监听器
     */
    static cleanupEventListeners() {
        if (this.container._dragOverHandler) {
            this.container.removeEventListener('dragover', this.container._dragOverHandler);
            delete this.container._dragOverHandler;
        }
        if (this.container._dropHandler) {
            this.container.removeEventListener('drop', this.container._dropHandler);
            delete this.container._dropHandler;
        }
        
        const items = this.container.querySelectorAll(this.options.itemSelector);
        items.forEach(item => {
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
    
    /**
     * 销毁列表管理器
     */
    static destroy() {
        this.cleanupEventListeners();
        this.container = null;
        this.options = null;
    }
}

// 在浏览器环境中暴露到全局
if (typeof window !== 'undefined') {
    window.ListManager = ListManager;
} 