/**
 * 异常消息解析工具类
 * 统一处理异常消息格式，解析<>和[]中的内容
 */
class ExceptionMessageParser {

    /**
     * 解析异常消息
     * @param {string} message - 原始异常消息
     * @returns {string} 解析后的消息
     */
    static parse(message) {
        if (!message || typeof message !== 'string') {
            return message || '未知错误';
        }

        // 检查是否同时包含[]和<>
        const hasSquareBrackets = /\[([^\]]+)\]/.test(message);
        const hasAngleBrackets = /<([^>]+)>/.test(message);
        console.log('hasSquareBrackets:', hasSquareBrackets)
        console.log('hasSquareBrackets:', hasAngleBrackets)
        if (hasSquareBrackets && hasAngleBrackets) {
            // 同时有[]和<>的情况，直接返回<>中的内容
            const angleBracketMatch = message.match(/<([^>]+)>/);
            console.log('angleBracketMatch:', angleBracketMatch)
            return angleBracketMatch[1].trim();
        }

        // 只有<>的情况
        const angleBracketMatch = message.match(/<([^>]+)>/);
        if (angleBracketMatch) {
            return angleBracketMatch[1].trim();
        }

        // 只有[]的情况
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


    /**
     * 解析异常响应对象
     * @param {Object} response - 异常响应对象
     * @returns {string} 解析后的消息
     */
    static parseResponse(response) {
        if (!response) {
            return '未知错误';
        }

        // 如果是字符串，直接解析
        if (typeof response === 'string') {
            return this.parse(response);
        }

        // 如果是对象，尝试获取message字段
        if (typeof response === 'object') {
            const message = response.message || response.error || response.msg || response.toString();
            return this.parse(message);
        }

        return '未知错误';
    }

    /**
     * 解析HTTP错误响应
     * @param {Response} response - HTTP响应对象
     * @returns {Promise<string>} 解析后的消息
     */
    static async parseHttpResponse(response) {
        try {
            const text = await response.text();

            // 尝试解析JSON
            try {
                const json = JSON.parse(text);
                return this.parseResponse(json);
            } catch (e) {
                // 如果不是JSON，直接解析文本
                return this.parse(text);
            }
        } catch (error) {
            return '网络错误，请稍后重试';
        }
    }
}
