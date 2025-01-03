$(document).ready(function () {
    // 切换标签
    $('.nav-tabs .nav-link').click(function () {
        window.__hash = this.id.split('-')[0]
        $('.nav-tabs .nav-link').removeClass('active');
        $(this).addClass('active');
        $('.tab-pane').removeClass('show active');
        $('#' + __hash).addClass('show active');
        $('#aggregateTab').focus();
    });
    // 加载服务名称
    $.get('/services', function (data) {
        data.forEach(function (service) {
            $('#serviceName').append(`<option value="${service}">${service}</option>`);
        });
    });

    // 当选择服务后，加载限界上下文
    $('#serviceName').change(function () {
        const serviceName = $(this).val();
        $('#contextName').empty().append('<option value="">限界上下文</option>');
        $('#domainName').empty().append('<option value="">领域</option>');
        $('#aggregateName').empty().append('<option value="">聚合</option>'); // 清空聚合选择
        if (serviceName) {
            $.get('/contexts', {serviceName: serviceName}, function (data) {
                data.forEach(function (context) {
                    $('#contextName').append(`<option value="${context}">${context}</option>`);
                });
            });
        }
    });
    // 当选择服务后，加载领域名称
    $('#contextName').change(function () {
        const serviceName = $('#serviceName').val();
        const contextName = $(this).val();
        $('#domainName').empty().append('<option value="">领域</option>');
        $('#aggregateName').empty().append('<option value="">聚合</option>'); // 清空聚合选择
        if (contextName) {
            $.get('/domains', {serviceName: serviceName, contextName: contextName}, function (data) {
                data.forEach(function (domain) {
                    $('#domainName').append(`<option value="${domain}">${domain}</option>`);
                });
            });
        }
    });

    // 当选择领域后，自动加载聚合文件
    $('#domainName').change(function () {
        const serviceName = $('#serviceName').val();
        const contextName = $('#contextName').val();
        const domainName = $(this).val();
        $('#aggregateName').empty().append('<option value="">聚合</option>');
        if (serviceName && domainName) {
            $.get('/aggregates', {
                serviceName: serviceName,
                contextName: contextName,
                domainName: domainName
            }, function (data) {
                data.forEach(function (aggregate) {
                    $('#aggregateName').append(`<option value="${aggregate}">${aggregate}</option>`);
                });
            });
        }
    });

    // 当选择聚合文件后，自动加载聚合信息
    $('#aggregateName').change(function () {
        const selectedAggregate = $(this).val();
        const serviceName = $('#serviceName').val();
        const contextName = $('#contextName').val();
        const domainName = $('#domainName').val();
        const aggregateName = $('#aggregateName').val();
        if (selectedAggregate) {
            $.ajax({
                type: 'POST',
                url: '/aggregate/get',
                data: JSON.stringify({
                    serviceName: serviceName,
                    contextName: contextName,
                    domainName: domainName,
                    aggregateName: aggregateName
                }),
                contentType: 'application/json;type=utf-8',
                success: function (data) {
                    // 聚合信息回显
                    $('input[name="author"]').val(data.author);
                    $('input[name="version"]').val(data.version);
                    $('input[name="groupId"]').val(data.groupId);
                    $('input[name="serviceName"]').val(data.serviceName);
                    $('input[name="rootPath"]').val(data.rootPath);
                    $('input[name="serviceSubPackage"]').val(data.serviceSubPackage);
                    $('input[name="contextName"]').val(data.contextName);
                    $('input[name="domainName"]').val(data.domainName);
                    $('input[name="aggregateName"]').val(data.aggregateName);
                    $('input[name="description"]').val(data.description);

                    // 聚合信息回显, 清空已有字段
                    $('.field-container .fields').empty();
                    data.fields?.forEach((item, index) => {
                        const fieldHtml = genFieldHtml(item, `fields[${index}]`);
                        $('.field-container .fields').append(fieldHtml);
                    });
                    // 实体信息回显
                    $('.entities-container').empty();
                    data.entities?.forEach((item = {}, index) => {
                        addDynamicElement('.entities-container', 'entities', item, index)
                    })
                    // 枚举信息回显
                    $('.enums-container').empty();
                    data.enums?.forEach((item = {}, index) => {
                        addDynamicElement('.enums-container', 'enums', item, index)
                    })
                    // 值对象信息回显
                    $('.values-container').empty();
                    data.values?.forEach((item = {}, index) => {
                        addDynamicElement('.values-container', 'values', item, index)
                    })
                    // 方法信息回显
                    $('.methods-container').empty();
                    data.methods?.forEach((item = {}, index) => {
                        addDynamicElement('.methods-container', 'methods', item, index)
                    })
                    // 方法信息回显
                    $('.transfers-container').empty();
                    data.transfers?.forEach((item = {}, index) => {
                        addDynamicElement('.transfers-container', 'transfers', item, index)
                    })
                }
            })
        }
    });

    // 生成代码
    $('#generateCode').click(function () {
        // 这里可以添加提交生成代码的逻辑
        const serviceName = $('#serviceName').val();
        const contextName = $('#contextName').val();
        const domainName = $('#domainName').val();
        const aggregateName = $('#aggregateName').val();
        if (!serviceName || !contextName || !domainName || !aggregateName) {
            showMessage('请先选择聚合');
            return;
        }

        // 处理选择的生成选项
        const enableOptions = {
            enableEntity: $('input[name="enableEntity"]').is(':checked'),
            enableRecord: $('input[name="enableRecord"]').is(':checked'),
            enableValue: $('input[name="enableValue"]').is(':checked'),
            enableEnum: $('input[name="enableEnum"]').is(':checked'),
            enableMethod: $('input[name="enableMethod"]').is(':checked'),
            enableRepository: $('input[name="enableRepository"]').is(':checked'),

            enableCommandHandler: $('input[name="enableCommandHandler"]').is(':checked'),
            enableCommandTransfer: $('input[name="enableCommandTransfer"]').is(':checked'),
            enableQueryHandler: $('input[name="enableQueryHandler"]').is(':checked'),
            enableQueryTransfer: $('input[name="enableQueryTransfer"]').is(':checked'),

            enableDataManager: $('input[name="enableDataManager"]').is(':checked'),
            enableMapper: $('input[name="enableMapper"]').is(':checked'),
            enableDrivenTransfer: $('input[name="enableDrivenTransfer"]').is(':checked'),

            enableDrivingAdapter: $('input[name="enableDrivingAdapter"]').is(':checked'),

            enableController: $('input[name="enableController"]').is(':checked'),

            serviceName,
            contextName,
            domainName,
            aggregateName
        };

        $.ajax({
            url: `/generate`, // 假设你有一个控制器处理这个请求
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(enableOptions),
            success: function (response) {
                showMessage(response)
            },
            error: function (response) {
                showMessage(response)
            }
        });
    });
    // 生成yaml
    $('#generate').click(function () {
        document.querySelector('#yamlFormSubmit').click()
    })

    $('#yamlForm').submit(function (e) {
        e.preventDefault()
        window.formdata = new FormData(document.querySelector('#yamlForm'))
        document.querySelectorAll('input[type=checkbox]').forEach(item => {
            if (!item.checked) {
                formdata.append(item.name, false)
            }
        })
        const formObject = {}
        formdata.forEach((value, key) => {
            setValue(formObject, key.split('.'), value);
        });
        const data = JSON.stringify(formObject)
        $.ajax({
            url: `/aggregate/put`,
            method: 'POST',
            data,
            contentType: 'application/json',
            success: function (response) {
                showMessage(response)
            }
        })
    })
    document.querySelector('#yamlForm').addEventListener('invalid', function (event) {
        const invalidElement = event.target
        const tabs = ['entities', 'enums', 'values', 'methods', 'transfers']
        const tabName = invalidElement.name.match(/^\w+/)[0]
        if (tabs.includes(tabName)) {
            $(`.nav-tabs #${tabName}-tab`).click()
        } else {
            $(`.nav-tabs #aggregate-tab`).click()
        }
    }, true)

    $('.add-aggregate-field').click(function () {
        const index = $('#aggregate .fields').children().length;
        addField('#aggregate .fields', `fields[${index}]`)
    })
    $('.add-entities').click(function (e) {
        const index = $('.entities-container').children().length;
        addDynamicElement('.entities-container', 'entities', {}, index);
    });

    $('.add-enum').click(function () {
        const index = $('.enums-container').children().length;
        addDynamicElement('.enums-container', 'enums', {}, index);
    });

    $('.add-value').click(function () {
        const index = $('.values-container').children().length;
        addDynamicElement('.values-container', 'values', {}, index);
    });

    $('.add-method').click(function () {
        const index = $('.methods-container').children().length;
        addDynamicElement('.methods-container', 'methods', {}, index);
    });

    $('.add-transfers').click(function () {
        const index = $('.transfers-container').children().length;
        addDynamicElement('.transfers-container', 'transfers', {}, index);
    });

    // 移除动态元素
    $(document).on('click', '.remove-field', function () {
        const parent = $(this).closest('.fields')
        $(this).closest('.field').remove();
        updateFieldOrEnumValue([...parent.children()])
    });
    // 增加动态元素
    $(document).on('click', '.add-field', function () {
        console.log($(this).closest('.field'))
        const key = $(this).closest('.field')[0].querySelector('input').getAttribute('name')
        let parent = $(this).closest('.fields')
        let element = genFieldHtml({}, key.replace(/\.[^.]+$/, ''))
        let type = 'fields'
        if (key.includes('enumValues')) {
            parent = $(this).closest('.enumValues')
            const obj = {
                code: '',
                description: ''
            }
            element = genEnumValueHtml(obj, key.replace(/\.[^.]+$/, ''))
            type = 'enumValues'
        }
        $(this).closest('.field').after(element)
        updateFieldOrEnumValue([...parent.children()], type)
    });

    $(document).on('click', '.remove-entities', function () {
        const parent = $(this).closest('.entities-container')
        $(this).closest('.entities').remove();
        updateIndex([...parent.children()], 'entities')
    });

    $(document).on('click', '.remove-enums', function () {
        const parent = $(this).closest('.enums-container')
        $(this).closest('.enums').remove();
        updateIndex([...parent.children()], 'enums')
    });

    $(document).on('click', '.remove-values', function () {
        const parent = $(this).closest('.values-container')
        $(this).closest('.values').remove();
        updateIndex([...parent.children()], 'values')
    });

    $(document).on('click', '.remove-methods', function () {
        const parent = $(this).closest('.methods-container')
        $(this).closest('.methods').remove();
        updateIndex([...parent.children()], 'methods')
    });

    $(document).on('click', '.remove-transfers', function () {
        const parent = $(this).closest('.transfers-container')
        $(this).closest('.transfers').remove();
        updateIndex([...parent.children()], 'transfers')
    });

    window.handlerAllCheckedChange = function () {
        document.querySelector('#all-checked-1').checked = [...document.querySelectorAll('#all-1 input[type=checkbox]')].every(item => {
            return item.checked
        })
    }
    window.handlerAllChecked = function () {
        const checked = document.querySelector('#all-checked-1').checked
        document.querySelectorAll('#all-1 input[type=checkbox]').forEach(item => {
            item.checked = checked
        })
    }
    handlerAllCheckedChange()

    function showMessage(msg) {
        // 更新模态框内容
        $('#alertModal .modal-body').text(msg);
        // 显示模态框
        $('#alertModal').modal('show');
    }
});
