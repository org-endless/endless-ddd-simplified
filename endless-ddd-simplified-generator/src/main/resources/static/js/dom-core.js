const typeNameMap = {
    entities: '实体名', enums: '枚举名', values: '值对象名', methods: '方法名', transfers: '传输对象名',
}

// 动态添加实体、枚举和值对象
function addDynamicElement(container, type, data = {}, index) {
    data = {
        name: data.name || '',
        description: data.description || '',
        fields: data.fields || [],
        enumValues: data.enumValues || [],
        returnType: data.returnType || '',
        valueType: data.valueType || '',
        adapterType: data.adapterType || '',
        cqrsType: data.cqrsType || '',
        messageType: data.messageType || '',
    }
    const pKey = `${type}[${index}]`
    const fields = `<h4>字段</h4>
                    <button type="button" id="addField" class="btn btn-primary mb-2">+</button>
                    <div class="fields">${data.fields.map((item, i) => genFieldHtml(item, pKey + '.fields[' + i + ']')).join('\n')}</div>`
    const enumValues = function () {
        const t = [{name: 'code', type: 'String', description: '枚举代码'}, {
            name: 'description', type: 'String', description: '枚举描述'
        }]
        if (!data.fields.length) data.fields = t
        return `
            <h4>字段</h4>
            <div class="fields">${data.fields.map((item, i) => genFieldHtml(item, pKey + '.fields[' + i + ']')).join('\n')}</div>
            <h4>枚举值</h4>
            <button type="button" id="addEnumValue" class="btn btn-primary mb-2">+</button>
            <div class="enumValues">${data.enumValues.map((item, i) => genEnumValueHtml(item, pKey + '.enumValues[' + i + ']')).join('\n')}</div>`
    }
    const methods = `
                    <div class="form-floating mb-2">
                        <input type="text" name="${pKey}.returnType" id="${pKey}.returnType" placeholder="返回值" required class="form-control" value="${data.returnType}">
                        <label for="${pKey}.returnType">返回值</label>
                    </div>`
    const valueType = `
                    <div class="form-floating mb-2">
                        <input type="text" name="${pKey}.valueType" id="${pKey}.valueType" placeholder="返回值" required class="form-control" value="${data.valueType}">
                        <label for="${pKey}.valueType">值类型</label>
                    </div>`
    const transfers = `
                    <div class="grid-3">
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
                    </div>`

    const differHtmlMap = {
        methods, enums: valueType, transfers
    }
    const elementHtml = `
            <div class="${type}">
                <h5 style="display: flex;justify-content: space-between">
                    <span class="title">${typeNameMap[type].slice(0, -1) + '-' + (index + 1)}</span>
                    <div>
                        <button type="button" class="btn btn-primary add-${type}-item">+</button>
                        <button type="button" class="btn btn-danger remove-${type}">-</button>
                    </div>
                </h5>
                <div class="form-floating mb-2">
                    <input type="text" name="${pKey}.name" id="${pKey}.name" placeholder="${typeNameMap[type]}" required class="form-control" value="${data.name}">
                    <label for="${pKey}.name">${typeNameMap[type]}</label>
                </div>
                <div class="form-floating mb-2">
                    <input type="text" name="${pKey}.description" id="${pKey}.description" placeholder="描述" required class="form-control" value="${data.description}">
                    <label for="${pKey}.description">描述</label>
                </div>
                ${differHtmlMap[type] || ''}
                <div class="field-container">
                    ${type === 'enums' ? enumValues() : fields}
                </div>
                
            </div>`;
    if ($(container + ` .${type}`)[index - 1]) {
        $($(container + ` .${type}`)[index - 1]).after(elementHtml);
    } else {
        $(container).append(elementHtml)
    }

    $(container + ' #addField')[index]?.addEventListener('click', function () {
        const selector = this.parentNode.querySelector('.fields')
        const i = selector.children.length
        addField(selector, pKey + '.fields[' + i + ']')
    })
    $(container + ' #addEnumValue')[index]?.addEventListener('click', function () {
        const selector = this.parentNode.querySelector('.enumValues')
        const i = selector.children.length
        // let obj = {...data.enumValues[0]}
        // Object.keys(obj).forEach(key => {
        //     obj[key] = ''
        // })
        const obj = {
            code: '', description: ''
        }
        addEnumValue(selector, obj, pKey + '.enumValues[' + i + ']')
    })
    $(`.add-${type}-item`)[index].addEventListener('click', function () {
        addDynamicElement(`.${type}-container`, type, {}, index + 1);
        const parent = $(this).closest(`.${type}-container`)
        updateIndex([...parent.children()], type)
    })
}

// 添加枚举字段
function genEnumValueHtml(item = {}, pKey) {
    const keys = Object.keys(item)
    return `
        <div class="field mb-2">
            ${keys.map(key => {
        return `<input type="text" name="${pKey}.${key}" placeholder="${key}" required class="form-control" value="${item[key]}">`
    }).join('\n')}
            <div>
                <button type="button" class="btn btn-primary add-field">+</button>&nbsp;&nbsp;
                <button type="button" class="btn btn-danger remove-field">-</button>
            </div>
        </div>`
}

function addEnumValue(selector, obj, pKey) {
    const enumValueHtml = genEnumValueHtml(obj, pKey);
    $(selector).append(enumValueHtml);
}

// 添加实体和值对象字段
function genFieldHtml(data = {}, pKey) {
    data = {
        name: data.name || '',
        type: data.type || '',
        description: data.description || '',
        nullable: data.nullable || false
    }
    return `
        <div class="field mb-2">
            <input type="text" name="${pKey}.name" placeholder="字段名" required class="form-control" value="${data.name}">
            <input type="text" name="${pKey}.type" placeholder="字段类型" required class="form-control" value="${data.type}">
            <input type="text" name="${pKey}.description" placeholder="描述" required class="form-control" value="${data.description}">
            <div class="form-check">
                <input type="checkbox" name="${pKey}.nullable" class="form-check-input" id="${pKey}.nullable" ${data.nullable ? 'checked' : ''}>
                <label class="form-check-label" for="${pKey}.nullable">可空</label>
            </div>
            ${pKey.startsWith('enums') ? '' : '<div>' + '<button type="button" class="btn btn-primary add-field">+</button>&nbsp;&nbsp;' + '<button type="button" class="btn btn-danger remove-field">-</button>' + '</div>'}
        </div>`
}

function addField(selector, key) {
    const fieldHtml = genFieldHtml({}, key);
    $(selector).append(fieldHtml);
}

// 更新字段的index
function updateFieldOrEnumValue(selectors, type = 'fields') {
    selectors.map((item, index) => {
        item.querySelectorAll('input').forEach(input => {
            const attr = input.getAttribute('name')
            const name = type === 'fields' ? attr.replace(/fields\[\d+]/, `fields[${index}]`) : attr.replace(/enumValues\[\d+]/, `enumValues[${index}]`)
            input.setAttribute('name', name)
            input.setAttribute('id', name)
        })
        item.querySelectorAll('label').forEach(label => {
            const attr = label.getAttribute('for')
            const name = type === 'fields' ? attr.replace(/fields\[\d+]/, `fields[${index}]`) : attr.replace(/enumValues\[\d+]/, `enumValues[${index}]`)
            label.setAttribute('for', name)
        })
    })
}

function updateIndex(selectors, type) {
    const typeMap = {
        entities: /entities\[\d+]/,
        enums: /enums\[\d+]/,
        values: /values\[\d+]/,
        methods: /methods\[\d+]/,
        transfers: /transfers\[\d+]/
    }
    selectors.forEach((item, index) => {
        const title = item.querySelector('.title')
        const text = title.textContent
        title.textContent = text.replace(/\d+/, index + 1)
        item.querySelectorAll('input').forEach(input => {
            const attr = input.getAttribute('name')
            const name = attr.replace(typeMap[type], `${type}[${index}]`)
            input.setAttribute('name', name)
            input.setAttribute('id', name)
        })
        item.querySelectorAll('select').forEach(select => {
            const attr = select.getAttribute('name')
            const name = attr.replace(typeMap[type], `${type}[${index}]`)
            select.setAttribute('name', name)
        })
        item.querySelectorAll('label').forEach(label => {
            const attr = label.getAttribute('for')
            const name = attr.replace(typeMap[type], `${type}[${index}]`)
            label.setAttribute('for', name)
        })
    })
}
