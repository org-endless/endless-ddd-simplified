/**
 * HTTP客户端工具类
 * 统一处理所有HTTP请求
 */
class HttpClient {
    
    /**
     * 基础配置
     */
    static config = {
        baseURL: '',
        timeout: 30000,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    
    /**
     * 发送GET请求
     * @param {string} url - 请求URL
     * @param {Object} options - 请求选项
     * @returns {Promise<Object>} 响应结果
     */
    static async get(url, options = {}) {
        return this.request(url, {
            method: 'GET',
            ...options
        });
    }
    
    /**
     * 发送POST请求
     * @param {string} url - 请求URL
     * @param {Object} data - 请求数据
     * @param {Object} options - 请求选项
     * @returns {Promise<Object>} 响应结果
     */
    static async post(url, data = null, options = {}) {
        return this.request(url, {
            method: 'POST',
            body: data ? JSON.stringify(data) : null,
            ...options
        });
    }
    
    /**
     * 发送PUT请求
     * @param {string} url - 请求URL
     * @param {Object} data - 请求数据
     * @param {Object} options - 请求选项
     * @returns {Promise<Object>} 响应结果
     */
    static async put(url, data = null, options = {}) {
        return this.request(url, {
            method: 'PUT',
            body: data ? JSON.stringify(data) : null,
            ...options
        });
    }
    
    /**
     * 发送DELETE请求
     * @param {string} url - 请求URL
     * @param {Object} options - 请求选项
     * @returns {Promise<Object>} 响应结果
     */
    static async delete(url, options = {}) {
        return this.request(url, {
            method: 'DELETE',
            ...options
        });
    }
    
    /**
     * 统一请求方法
     * @param {string} url - 请求URL
     * @param {Object} options - 请求选项
     * @returns {Promise<Object>} 响应结果
     */
    static async request(url, options = {}) {
        const fullUrl = this.config.baseURL + url;
        
        // 合并配置
        const requestOptions = {
            headers: {
                ...this.config.headers,
                ...options.headers
            },
            ...options
        };
        
        // 设置超时
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), this.config.timeout);
        requestOptions.signal = controller.signal;
        
        try {
            const response = await fetch(fullUrl, requestOptions);
            clearTimeout(timeoutId);
            
            // 处理响应
            return await this.handleResponse(response);
        } catch (error) {
            clearTimeout(timeoutId);
            throw this.handleError(error);
        }
    }
    
    /**
     * 处理响应
     * @param {Response} response - HTTP响应对象
     * @returns {Promise<Object>} 处理后的响应
     */
    static async handleResponse(response) {
        const contentType = response.headers.get('content-type');
        
        // 检查响应状态
        if (!response.ok) {
            const error = await this.parseErrorResponse(response);
            throw error;
        }
        
        // 解析响应内容
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    }
    
    /**
     * 解析错误响应
     * @param {Response} response - HTTP响应对象
     * @returns {Promise<Error>} 错误对象
     */
    static async parseErrorResponse(response) {
        try {
            const contentType = response.headers.get('content-type');
            let errorData;
            let errorMessage;
            
            if (contentType && contentType.includes('application/json')) {
                errorData = await response.json();
                // 从JSON对象中提取错误消息
                errorMessage = this.extractErrorMessage(errorData);
            } else {
                errorData = await response.text();
                errorMessage = errorData;
            }
            
            return new HttpError(
                response.status,
                response.statusText,
                errorMessage,
                response.url
            );
        } catch (error) {
            return new HttpError(
                response.status,
                response.statusText,
                '解析错误响应失败',
                response.url
            );
        }
    }
    
    /**
     * 从错误数据中提取错误消息
     * @param {Object|string} errorData - 错误数据
     * @returns {string} 错误消息
     */
    static extractErrorMessage(errorData) {
        if (typeof errorData === 'string') {
            return errorData;
        }
        
        if (typeof errorData === 'object' && errorData !== null) {
            // 检查常见的错误消息字段
            if (errorData.message) {
                return errorData.message;
            } else if (errorData.error) {
                return errorData.error;
            } else if (errorData.msg) {
                return errorData.msg;
            } else if (errorData.detail) {
                return errorData.detail;
            } else if (errorData.reason) {
                return errorData.reason;
            } else {
                // 如果没有明确的错误消息字段，返回JSON字符串
                try {
                    return JSON.stringify(errorData);
                } catch (e) {
                    return '未知错误';
                }
            }
        }
        
        return '未知错误';
    }
    
    /**
     * 处理请求错误
     * @param {Error} error - 错误对象
     * @returns {Error} 处理后的错误
     */
    static handleError(error) {
        if (error.name === 'AbortError') {
            return new HttpError(408, '请求超时', '请求超时，请稍后重试');
        }
        
        if (error.name === 'TypeError' && error.message.includes('fetch')) {
            return new HttpError(0, '网络错误', '网络连接失败，请检查网络设置');
        }
        
        return error;
    }
    
    /**
     * 设置基础URL
     * @param {string} baseURL - 基础URL
     */
    static setBaseURL(baseURL) {
        this.config.baseURL = baseURL;
    }
    
    /**
     * 设置请求头
     * @param {Object} headers - 请求头
     */
    static setHeaders(headers) {
        this.config.headers = {
            ...this.config.headers,
            ...headers
        };
    }
    
    /**
     * 设置超时时间
     * @param {number} timeout - 超时时间（毫秒）
     */
    static setTimeout(timeout) {
        this.config.timeout = timeout;
    }
}

/**
 * HTTP错误类
 */
class HttpError extends Error {
    constructor(status, statusText, message, url = '') {
        super(message);
        this.name = 'HttpError';
        this.status = status;
        this.statusText = statusText;
        this.url = url;
        this.timestamp = new Date();
    }
    
    toString() {
        return `HttpError ${this.status}: ${this.message}`;
    }
}

// 在浏览器环境中暴露到全局
if (typeof window !== 'undefined') {
    window.HttpClient = HttpClient;
    window.HttpError = HttpError;
} 