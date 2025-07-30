/**
 * DOM核心工具类
 * 负责动态DOM元素操作和生成
 */
class DOMCore {
    static typeNameMap = {
        entities: '实体名', 
        enums: '枚举名', 
        values: '值对象名', 
        methods: '方法名', 
        transfers: '传输对象名'
    };

    /**
     * 动态添加元素
     * @param {string} container - 容器选择器
     * @param {string} type - 元素类型
     * @param {Object} data - 元素数据
     * @param {number} index - 元素索引
     */
    static addDynamicElement(container, type, data = {}, index) {
        data = {
            name: data.name || '',
            description: data.description || '',
            fields: data.fields || [],
            enumValues: data.enumValues || [],
            returnType: data.returnType || '',
            valueType: data.valueType || '',
            adapterType: data.adapterType || '',
            cqrsType: data.cqrsType || '',
            messageType: data.messageType || ''
        };

        const pKey = `${type}[${index}]`;
        const elementHtml = this.generateElementHtml(type, data, pKey, index);
        
        if ($(container + ` .${type}`)[index - 1]) {
            $($(container + ` .${type}`)[index - 1]).after(elementHtml);
        } else {
            $(container).append(elementHtml);
        }

        this.bindElementEvents(container, type, pKey, index);
    }

    /**
     * 生成元素HTML
     * @param {string} type - 元素类型
     * @param {Object} data - 元素数据
     * @param {string} pKey - 父键名
     * @param {number} index - 索引
     * @returns {string} HTML字符串
     */
    static generateElementHtml(type, data, pKey, index) {
        const fields = this.generateFieldsHtml(data, pKey);
        const enumValues = this.generateEnumValuesHtml(data, pKey);
        const methods = this.generateMethodsHtml(data, pKey);
        const valueType = this.generateValueTypeHtml(data, pKey);
        const transfers = this.generateTransfersHtml(data, pKey);

        const differHtmlMap = {
            methods, 
            enums: valueType, 
            transfers
        };

        return `
            <div class="${type}">
                <h5 style="display: flex;justify-content: space-between">
                    <span class="title">${this.typeNameMap[type].slice(0, -1) + '-' + (index + 1)}</span>
                    <div>
                        <button type="button" class="btn btn-primary add-${type}-item">+</button>
                        <button type="button" class="btn btn-danger remove-${type}">-</button>
                    </div>
                </h5>
                <div class="form-floating mb-2">
                    <input type="text" name="${pKey}.name" id="${pKey}.name" placeholder="${this.typeNameMap[type]}" required class="form-control" value="${data.name}">
                    <label for="${pKey}.name">${this.typeNameMap[type]}</label>
                </div>
                <div class="form-floating mb-2">
                    <input type="text" name="${pKey}.description" id="${pKey}.description" placeholder="描述" required class="form-control" value="${data.description}">
                    <label for="${pKey}.description">描述</label>
                </div>
                ${differHtmlMap[type] || ''}
                <div class="field-container">
                    ${type === 'enums' ? enumValues : fields}
                </div>
            </div>`;
    }

    /**
     * 生成字段HTML
     * @param {Object} data - 数据对象
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateFieldsHtml(data, pKey) {
        return `<h4>字段</h4>
                <button type="button" id="addField" class="btn btn-primary mb-2">+</button>
                <div class="fields">${data.fields.map((item, i) => this.generateFieldHtml(item, pKey + '.fields[' + i + ']')).join('\n')}</div>`;
    }

    /**
     * 生成枚举值HTML
     * @param {Object} data - 数据对象
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateEnumValuesHtml(data, pKey) {
        const defaultFields = [
            {name: 'code', type: 'String', description: '枚举代码'}, 
            {name: 'description', type: 'String', description: '枚举描述'}
        ];
        
        if (!data.fields.length) data.fields = defaultFields;
        
        return `
            <h4>字段</h4>
            <div class="fields">${data.fields.map((item, i) => this.generateFieldHtml(item, pKey + '.fields[' + i + ']')).join('\n')}</div>
            <h4>枚举值</h4>
            <button type="button" id="addEnumValue" class="btn btn-primary mb-2">+</button>
            <div class="enumValues">${data.enumValues.map((item, i) => this.generateEnumValueHtml(item, pKey + '.enumValues[' + i + ']')).join('\n')}</div>`;
    }

    /**
     * 生成方法HTML
     * @param {Object} data - 数据对象
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateMethodsHtml(data, pKey) {
        return `<div class="form-floating mb-2">
                    <input type="text" name="${pKey}.returnType" id="${pKey}.returnType" placeholder="返回值" required class="form-control" value="${data.returnType}">
                    <label for="${pKey}.returnType">返回值</label>
                </div>`;
    }

    /**
     * 生成值类型HTML
     * @param {Object} data - 数据对象
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateValueTypeHtml(data, pKey) {
        return `<div class="form-floating mb-2">
                    <input type="text" name="${pKey}.valueType" id="${pKey}.valueType" placeholder="返回值" required class="form-control" value="${data.valueType}">
                    <label for="${pKey}.valueType">值类型</label>
                </div>`;
    }

    /**
     * 生成传输对象HTML
     * @param {Object} data - 数据对象
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateTransfersHtml(data, pKey) {
        return `<div class="grid-3">
                    <select class="form-control mb-2" name="${pKey}.adapterType" required>
                        <option value="DRIVING" ${data.adapterType === 'DRIVING' ? 'selected' : ''}>主动适配器</option>
                        <option value="DRIVEN" ${data.adapterType === 'DRIVEN' ? 'selected' : ''}>被动适配器</option>
                    </select>
                    <select class="form-control mb-2" name="${pKey}.cqrsType" required>
                        <option value="COMMAND" ${data.cqrsType === 'COMMAND' ? 'selected' : ''}>命令模式</option>
                        <option value="QUERY" ${data.cqrsType === 'QUERY' ? 'selected' : ''}>查询模式</option>
                    </select>
                    <select class="form-control mb-2" name="${pKey}.messageType" required>
                        <option value="REQUEST" ${data.messageType === 'REQUEST' ? 'selected' : ''}>请求消息</option>
                        <option value="RESPONSE" ${data.messageType === 'RESPONSE' ? 'selected' : ''}>响应消息</option>
                    </select>
                </div>`;
    }

    /**
     * 生成字段HTML
     * @param {Object} item - 字段数据
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateFieldHtml(item = {}, pKey) {
        return `<div class="field">
                    <input type="text" name="${pKey}.name" placeholder="字段名" value="${item.name || ''}" required>
                    <input type="text" name="${pKey}.type" placeholder="字段类型" value="${item.type || ''}" required>
                    <input type="text" name="${pKey}.description" placeholder="字段描述" value="${item.description || ''}" required>
                    <select name="${pKey}.nullable" required>
                        <option value="true" ${item.nullable === 'true' ? 'selected' : ''}>可空</option>
                        <option value="false" ${item.nullable === 'false' ? 'selected' : ''}>非空</option>
                    </select>
                    <button type="button" class="btn btn-danger remove-field">-</button>
                </div>`;
    }

    /**
     * 生成枚举值HTML
     * @param {Object} item - 枚举值数据
     * @param {string} pKey - 父键名
     * @returns {string} HTML字符串
     */
    static generateEnumValueHtml(item = {}, pKey) {
        return `<div class="enumValue">
                    <input type="text" name="${pKey}.code" placeholder="枚举代码" value="${item.code || ''}" required>
                    <input type="text" name="${pKey}.description" placeholder="枚举描述" value="${item.description || ''}" required>
                    <button type="button" class="btn btn-danger remove-enum-value">-</button>
                </div>`;
    }

    /**
     * 绑定元素事件
     * @param {string} container - 容器选择器
     * @param {string} type - 元素类型
     * @param {string} pKey - 父键名
     * @param {number} index - 索引
     */
    static bindElementEvents(container, type, pKey, index) {
        // 添加字段按钮事件
        $(container + ' #addField')[index]?.addEventListener('click', function () {
            const selector = this.parentNode.querySelector('.fields');
            const i = selector.children.length;
            DOMCore.addField(selector, pKey + '.fields[' + i + ']');
        });

        // 添加枚举值按钮事件
        $(container + ' #addEnumValue')[index]?.addEventListener('click', function () {
            const selector = this.parentNode.querySelector('.enumValues');
            const i = selector.children.length;
            DOMCore.addEnumValue(selector, {}, pKey + '.enumValues[' + i + ']');
        });
    }

    /**
     * 添加字段
     * @param {Element} selector - 选择器元素
     * @param {string} key - 键名
     */
    static addField(selector, key) {
        const fieldHtml = this.generateFieldHtml({}, key);
        $(selector).append(fieldHtml);
        this.updateIndex(selector, 'fields');
    }

    /**
     * 添加枚举值
     * @param {Element} selector - 选择器元素
     * @param {Object} obj - 对象数据
     * @param {string} pKey - 父键名
     */
    static addEnumValue(selector, obj, pKey) {
        const enumValueHtml = this.generateEnumValueHtml(obj, pKey);
        $(selector).append(enumValueHtml);
        this.updateIndex(selector, 'enumValues');
    }

    /**
     * 更新字段或枚举值索引
     * @param {Element} selectors - 选择器元素
     * @param {string} type - 类型
     */
    static updateFieldOrEnumValue(selectors, type = 'fields') {
        selectors.forEach((selector, index) => {
            const elements = selector.querySelectorAll(`.${type === 'fields' ? 'field' : 'enumValue'}`);
            elements.forEach((element, elementIndex) => {
                const inputs = element.querySelectorAll('input, select');
                inputs.forEach(input => {
                    const name = input.getAttribute('name');
                    if (name) {
                        const newName = name.replace(/\[\d+\]/, `[${elementIndex}]`);
                        input.setAttribute('name', newName);
                    }
                });
            });
        });
    }

    /**
     * 更新索引
     * @param {Element} selectors - 选择器元素
     * @param {string} type - 类型
     */
    static updateIndex(selectors, type) {
        if (Array.isArray(selectors)) {
            this.updateFieldOrEnumValue(selectors, type);
        } else {
            this.updateFieldOrEnumValue([selectors], type);
        }
    }
} 