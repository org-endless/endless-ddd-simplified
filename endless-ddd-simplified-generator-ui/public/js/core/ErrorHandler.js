/**
 * 统一异常处理类
 * 处理所有后端REST返回的异常信息
 */
class ErrorHandler {
    
    /**
     * 处理HTTP错误
     * @param {HttpError|Error} error - 错误对象
     * @returns {string} 处理后的错误消息
     */
    static handleHttpError(error) {
        console.error('HTTP错误:', error);
        
        // 如果是HttpError，使用其消息
        if (error instanceof HttpError) {
            return this.parseErrorMessage(error.message);
        }
        
        // 如果是普通Error，尝试解析消息
        if (error instanceof Error) {
            return this.parseErrorMessage(error.message);
        }
        
        // 如果是字符串，直接解析
        if (typeof error === 'string') {
            return this.parseErrorMessage(error);
        }
        
        // 如果是对象，尝试获取message字段
        if (typeof error === 'object' && error !== null) {
            // 尝试从对象中提取错误消息
            let message = null;
            
            // 检查常见的错误消息字段
            if (error.message) {
                message = error.message;
            } else if (error.error) {
                message = error.error;
            } else if (error.msg) {
                message = error.msg;
            } else if (error.detail) {
                message = error.detail;
            } else if (error.reason) {
                message = error.reason;
            } else if (typeof error === 'object') {
                // 如果是对象但没有明确的message字段，尝试JSON序列化
                try {
                    const jsonStr = JSON.stringify(error);
                    if (jsonStr !== '{}' && jsonStr !== '[]') {
                        message = jsonStr;
                    } else {
                        message = '未知错误';
                    }
                } catch (e) {
                    message = '未知错误';
                }
            }
            
            if (message) {
                return this.parseErrorMessage(message);
            }
        }
        
        return '未知错误';
    }
    
    /**
     * 解析错误消息
     * @param {string} message - 原始错误消息
     * @returns {string} 解析后的错误消息
     */
    static parseErrorMessage(message) {
        if (!message || typeof message !== 'string') {
            return '未知错误';
        }
        
        // 优先使用ExceptionMessageParser
        if (typeof ExceptionMessageParser !== 'undefined') {
            return ExceptionMessageParser.parse(message);
        }
        
        // 备用方案：直接返回原始消息
        return message.trim();
    }
}

// 在浏览器环境中暴露到全局
if (typeof window !== 'undefined') {
    window.ErrorHandler = ErrorHandler;
} 