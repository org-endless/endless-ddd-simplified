/**
 * 提示管理器类
 * 负责显示各种类型的提示信息
 */
class AlertManager {
    /**
     * 显示提示信息
     * @param {string} message - 提示消息
     * @param {string} type - 提示类型 (success, info, warning, danger)
     */
    static show(message, type = 'info') {
        const modal = document.getElementById('alertModal');
        if (!modal) {
            console.error('提示模态框不存在');
            return;
        }

        // 解析异常消息
        const parsedMessage = this.parseMessage(message);
        
        const modalBody = modal.querySelector('.modal-body');
        const alertClass = this.getAlertClass(type);
        
        modalBody.innerHTML = `
            <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                <i class="bi ${this.getIconClass(type)}"></i>
                ${parsedMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;

        const bootstrapModal = new bootstrap.Modal(modal);
        bootstrapModal.show();
    }
    
    /**
     * 解析异常消息
     * @param {string} message - 原始消息
     * @returns {string} 解析后的消息
     */
    static parseMessage(message) {
        // 如果ExceptionMessageParser存在，使用它来解析
        if (typeof ExceptionMessageParser !== 'undefined') {
            return ExceptionMessageParser.parse(message);
        }
        
        // 简单的解析逻辑作为备用
        if (!message || typeof message !== 'string') {
            return message || '未知错误';
        }
        
        // 首先尝试解析<>中的内容
        const angleBracketMatch = message.match(/<([^>]+)>/);
        if (angleBracketMatch) {
            return angleBracketMatch[1].trim();
        }
        
        // 如果没有<>，则解析最后一个[]中的内容
        const squareBracketMatches = message.match(/\[([^\]]+)\]/g);
        if (squareBracketMatches && squareBracketMatches.length > 0) {
            // 获取最后一个[]中的内容
            const lastMatch = squareBracketMatches[squareBracketMatches.length - 1];
            const content = lastMatch.match(/\[([^\]]+)\]/);
            if (content) {
                return content[1].trim();
            }
        }
        
        // 如果都没有，返回原始消息
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
     * 显示确认对话框
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
                <i class="bi bi-question-circle"></i>
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
     * 显示加载提示
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