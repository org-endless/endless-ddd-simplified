/**
 * 数据转换工具类
 * 负责表单数据转换和处理
 */
class DataTransform {
    /**
     * 设置对象值
     * @param {Object} obj - 目标对象
     * @param {Array} keys - 键数组
     * @param {*} value - 值
     */
    static setValue(obj, keys, value) {
        const key = keys.shift(); // 获取当前键

        // 检查是否是数组格式
        const arrayMatch = key.match(/^(\w+)\[(\d+)\]$/);
        if (arrayMatch) {
            const arrayName = arrayMatch[1]; // 数组名
            const arrayIndex = parseInt(arrayMatch[2], 10); // 数组索引

            obj[arrayName] = obj[arrayName] || [];
            obj[arrayName][arrayIndex] = obj[arrayName][arrayIndex] || {};

            // 继续递归调用，如果还有更多的键
            if (keys.length > 0) {
                this.setValue(obj[arrayName][arrayIndex], keys, this.handleValue(value));
            } else {
                obj[arrayName][arrayIndex] = this.handleValue(value); // 最后一个键，赋值
            }
        } else {
            // 如果没有数组，直接赋值或者继续递归
            obj[key] = obj[key] || {};
            if (keys.length > 0) {
                this.setValue(obj[key], keys, this.handleValue(value)); // 继续递归
            } else {
                obj[key] = this.handleValue(value); // 最后一个键，赋值
            }
        }
    }

    /**
     * 处理值
     * @param {*} value - 原始值
     * @returns {*} 处理后的值
     */
    static handleValue(value) {
        if (value === 'on') return true;
        if (value === 'false') return false;
        return value;
    }

    /**
     * 将表单数据转换为对象
     * @param {HTMLFormElement} form - 表单元素
     * @returns {Object} 转换后的对象
     */
    static formToObject(form) {
        const formData = {};
        const formElements = form.elements;

        for (let i = 0; i < formElements.length; i++) {
            const element = formElements[i];
            if (element.name && element.value) {
                this.setValue(formData, element.name.split('.'), element.value);
            }
        }

        return formData;
    }

    /**
     * 将对象转换为表单数据
     * @param {Object} obj - 对象
     * @param {string} prefix - 前缀
     * @returns {Object} 表单数据对象
     */
    static objectToFormData(obj, prefix = '') {
        const formData = {};
        
        for (const [key, value] of Object.entries(obj)) {
            const currentKey = prefix ? `${prefix}.${key}` : key;
            
            if (Array.isArray(value)) {
                value.forEach((item, index) => {
                    if (typeof item === 'object' && item !== null) {
                        Object.assign(formData, this.objectToFormData(item, `${currentKey}[${index}]`));
                    } else {
                        formData[`${currentKey}[${index}]`] = item;
                    }
                });
            } else if (typeof value === 'object' && value !== null) {
                Object.assign(formData, this.objectToFormData(value, currentKey));
            } else {
                formData[currentKey] = value;
            }
        }
        
        return formData;
    }

    /**
     * 验证表单数据
     * @param {Object} formData - 表单数据
     * @returns {Object} 验证结果 {valid: boolean, errors: Array}
     */
    static validateFormData(formData) {
        const errors = [];
        
        // 验证必填字段
        const requiredFields = ['author', 'version', 'serviceName', 'rootPath', 'serviceSubPackage', 'contextName', 'domainName', 'aggregateName', 'description'];
        
        requiredFields.forEach(field => {
            if (!formData[field] || formData[field].trim() === '') {
                errors.push(`${field} 是必填字段`);
            }
        });
        
        // 验证字段数组
        if (formData.fields && Array.isArray(formData.fields)) {
            formData.fields.forEach((field, index) => {
                if (!field.name || field.name.trim() === '') {
                    errors.push(`字段 ${index + 1} 的名称是必填的`);
                }
                if (!field.type || field.type.trim() === '') {
                    errors.push(`字段 ${index + 1} 的类型是必填的`);
                }
            });
        }
        
        return {
            valid: errors.length === 0,
            errors: errors
        };
    }

    /**
     * 清理表单数据
     * @param {Object} formData - 表单数据
     * @returns {Object} 清理后的数据
     */
    static cleanFormData(formData) {
        const cleaned = {};
        
        for (const [key, value] of Object.entries(formData)) {
            if (value !== null && value !== undefined && value !== '') {
                if (typeof value === 'string') {
                    cleaned[key] = value.trim();
                } else if (Array.isArray(value)) {
                    cleaned[key] = value.filter(item => item !== null && item !== undefined && item !== '');
                } else if (typeof value === 'object') {
                    cleaned[key] = this.cleanFormData(value);
                } else {
                    cleaned[key] = value;
                }
            }
        }
        
        return cleaned;
    }
} 