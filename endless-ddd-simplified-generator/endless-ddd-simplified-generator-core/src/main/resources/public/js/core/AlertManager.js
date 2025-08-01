/**
 * 提示管理器类
 * 负责显示各种类型的提示信息
 */
class AlertManager {
    static messageQueue = [];
    static isInitialized = false;

    /**
     * 初始化提示容器
     */
    static initialize() {
        if (this.isInitialized) return;

        // 创建右侧提示容器
        const container = document.createElement('div');
        container.id = 'alertContainer';
        container.className = 'alert-container';
        document.body.appendChild(container);

        this.isInitialized = true;
    }

    /**
     * 显示提示信息
     * @param {string} message - 提示消息
     * @param {string} type - 提示类型 (success, info, warning, danger)
     * @param {number} duration - 自动关闭时间（毫秒），默认20秒
     */
    static show(message, type = 'info', duration = 20000) {
        this.initialize();

        // 解析异常消息
        const parsedMessage = this.parseMessage(message);

        // 创建消息元素
        const messageId = 'alert-' + Date.now() + '-' + Math.random().toString(36).substr(2, 9);
        const alertElement = document.createElement('div');
        alertElement.id = messageId;
        alertElement.className = `alert-item alert-${type} fade-in`;

        const alertClass = this.getAlertClass(type);
        const iconClass = this.getIconClass(type);

        alertElement.innerHTML = `
            <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                <span class="alert-message">${parsedMessage}</span>
                <button type="button" class="btn-close" onclick="AlertManager.removeMessage('${messageId}')" aria-label="Close" title="关闭"></button>
            </div>
        `;

        // 添加到容器
        const container = document.getElementById('alertContainer');
        container.appendChild(alertElement);

        // 添加到队列
        this.messageQueue.push({
            id: messageId,
            element: alertElement,
            timer: null
        });

        // 设置自动关闭
        if (duration > 0) {
            const timer = setTimeout(() => {
                this.removeMessage(messageId);
            }, duration);

            // 更新队列中的timer
            const queueItem = this.messageQueue.find(item => item.id === messageId);
            if (queueItem) {
                queueItem.timer = timer;
            }
        }

        // 限制最大显示数量（最多5条）
        this.limitMessageCount();
    }

    /**
     * 移除指定消息
     * @param {string} messageId - 消息ID
     */
    static removeMessage(messageId) {
        const element = document.getElementById(messageId);
        if (element) {
            element.classList.add('fade-out');
            setTimeout(() => {
                if (element.parentNode) {
                    element.parentNode.removeChild(element);
                }
            }, 300);
        }

        // 从队列中移除
        const queueIndex = this.messageQueue.findIndex(item => item.id === messageId);
        if (queueIndex > -1) {
            const item = this.messageQueue[queueIndex];
            if (item.timer) {
                clearTimeout(item.timer);
            }
            this.messageQueue.splice(queueIndex, 1);
        }
    }

    /**
     * 限制消息数量
     */
    static limitMessageCount() {
        const maxMessages = 5;
        if (this.messageQueue.length > maxMessages) {
            const oldestMessage = this.messageQueue.shift();
            if (oldestMessage) {
                this.removeMessage(oldestMessage.id);
            }
        }
    }

    /**
     * 解析异常消息
     * @param {string} message - 原始消息
     * @returns {string} 解析后的消息
     */
    static parseMessage(message) {
        // 优先使用ExceptionMessageParser
        if (typeof ExceptionMessageParser !== 'undefined') {
            return ExceptionMessageParser.parse(message);
        }

        // 备用方案：直接返回原0始消息
        if (!message || typeof message !== 'string') {
            return '未知错误';
        }

        return message.trim();
    }

    /**
     * 显示成功提示
     * @param {string} message - 提示消息
     */
    static success(message) {
        this.show(message, 'success');
    }

    /**
     * 显示信息提示
     * @param {string} message - 提示消息
     */
    static info(message) {
        this.show(message, 'info');
    }

    /**
     * 显示警告提示
     * @param {string} message - 提示消息
     */
    static warning(message) {
        this.show(message, 'warning');
    }

    /**
     * 显示错误提示
     * @param {string} message - 提示消息
     */
    static error(message) {
        this.show(message, 'danger');
    }

    /**
     * 显示确认对话框（使用模态框）
     * @param {string} message - 确认消息
     * @param {Function} onConfirm - 确认回调函数
     * @param {Function} onCancel - 取消回调函数
     */
    static confirm(message, onConfirm, onCancel = null) {
        const modal = document.getElementById('alertModal');
        if (!modal) {
            console.error('提示模态框不存在');
            return;
        }

        const modalBody = modal.querySelector('.modal-body');
        const modalFooter = modal.querySelector('.modal-footer');

        modalBody.innerHTML = `
            <div class="alert alert-info">
                <i class="bi bi-question-circle me-2"></i>
                ${message}
            </div>
        `;

        modalFooter.innerHTML = `
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" id="confirmButton">确认</button>
        `;

        const bootstrapModal = new bootstrap.Modal(modal);
        bootstrapModal.show();

        // 绑定确认按钮事件
        document.getElementById('confirmButton').addEventListener('click', () => {
            bootstrapModal.hide();
            if (onConfirm) {
                onConfirm();
            }
        });

        // 绑定取消事件
        modal.addEventListener('hidden.bs.modal', () => {
            if (onCancel) {
                onCancel();
            }
        });
    }

    /**
     * 获取提示样式类
     * @param {string} type - 提示类型
     * @returns {string} CSS类名
     */
    static getAlertClass(type) {
        const alertClasses = {
            'success': 'alert-success',
            'info': 'alert-info',
            'warning': 'alert-warning',
            'danger': 'alert-danger'
        };
        return alertClasses[type] || 'alert-info';
    }

    /**
     * 获取图标样式类
     * @param {string} type - 提示类型
     * @returns {string} 图标类名
     */
    static getIconClass(type) {
        const iconClasses = {
            'success': 'bi-check-circle',
            'info': 'bi-info-circle',
            'warning': 'bi-exclamation-triangle',
            'danger': 'bi-x-circle'
        };
        return iconClasses[type] || 'bi-info-circle';
    }

    /**
     * 显示加载提示（使用模态框）
     * @param {string} message - 加载消息
     */
    static showLoading(message = '加载中...') {
        const modal = document.getElementById('alertModal');
        if (!modal) {
            console.error('提示模态框不存在');
            return;
        }

        const modalBody = modal.querySelector('.modal-body');
        const modalFooter = modal.querySelector('.modal-footer');

        modalBody.innerHTML = `
            <div class="text-center">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <p class="mt-2">${message}</p>
            </div>
        `;

        modalFooter.innerHTML = ''; // 清空底部按钮

        const bootstrapModal = new bootstrap.Modal(modal);
        bootstrapModal.show();
        return bootstrapModal;
    }

    /**
     * 隐藏加载提示
     */
    static hideLoading() {
        const modal = document.getElementById('alertModal');
        if (modal) {
            const bootstrapModal = bootstrap.Modal.getInstance(modal);
            if (bootstrapModal) {
                bootstrapModal.hide();
            }
        }
    }
}

// 在浏览器环境中暴露到全局
if (typeof window !== 'undefined') {
    window.AlertManager = AlertManager;
} 
