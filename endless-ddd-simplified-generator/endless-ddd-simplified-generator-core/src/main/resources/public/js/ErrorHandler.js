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
            const message = error.message || error.error || error.msg || error.toString();
            return this.parseErrorMessage(message);
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
        
        // 使用ExceptionMessageParser解析消息
        if (typeof ExceptionMessageParser !== 'undefined') {
            return ExceptionMessageParser.parse(message);
        }
        
        // 简单的解析逻辑作为备用
        return this.simpleParse(message);
    }
    
    /**
     * 简单解析错误消息（备用方案）
     * @param {string} message - 原始错误消息
     * @returns {string} 解析后的错误消息
     */
    static simpleParse(message) {
        // 首先尝试解析<>中的内容
        const angleBracketMatch = message.match(/<([^>]+)>/);
        if (angleBracketMatch) {
            return angleBracketMatch[1].trim();
        }
        
        // 如果没有<>，则解析最后一个[]中的内容
        const squareBracketMatches = message.match(/\[([^\]]+)\]/g);
        if (squareBracketMatches && squareBracketMatches.length > 0) {
            const lastMatch = squareBracketMatches[squareBracketMatches.length - 1];
            const content = lastMatch.match(/\[([^\]]+)\]/);
            if (content) {
                return content[1].trim();
            }
        }
        
        // 如果都没有，返回原始消息
        return message.trim();
    }
}

// 在浏览器环境中暴露到全局
if (typeof window !== 'undefined') {
    window.ErrorHandler = ErrorHandler;
} 