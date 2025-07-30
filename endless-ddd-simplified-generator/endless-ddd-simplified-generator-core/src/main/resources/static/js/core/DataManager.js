/**
 * 数据管理器类
 * 负责处理服务、上下文、领域、聚合的级联选择和数据回显
 */
class DataManager {
    /**
     * 初始化数据管理器
     */
    static initialize() {
        // 等待组件加载完成后再初始化
        this.waitForComponents().then(() => {
        this.loadServices();
        this.bindEvents();
        });
    }

    /**
     * 等待组件加载完成
     */
    static async waitForComponents() {
        return new Promise((resolve) => {
            const checkComponents = () => {
                const serviceSelect = document.getElementById('serviceNameSelect');
                if (serviceSelect) {
                    resolve();
                } else {
                    setTimeout(checkComponents, 100);
                }
            };
            checkComponents();
        });
    }

    /**
     * 加载服务列表
     */
    static loadServices() {
        $.get('/services', function (data) {
            data.forEach(function (service) {
                $('#serviceNameSelect').append(`<option value="${service}">${service}</option>`);
            });
        });
    }

    /**
     * 绑定事件
     */
    static bindEvents() {
        // 服务选择事件
        $('#serviceNameSelect').change(function () {
            DataManager.onServiceChange($(this).val());
        });

        // 上下文选择事件
        $('#contextNameSelect').change(function () {
            DataManager.onContextChange($('#serviceNameSelect').val(), $(this).val());
        });

        // 领域选择事件
        $('#domainNameSelect').change(function () {
            DataManager.onDomainChange($('#serviceNameSelect').val(), $('#contextNameSelect').val(), $(this).val());
        });

        // 聚合选择事件
        $('#aggregateNameSelect').change(function () {
            DataManager.onAggregateChange($('#serviceNameSelect').val(), $('#contextNameSelect').val(), $('#domainNameSelect').val(), $(this).val());
        });
    }

    /**
     * 服务选择变化处理
     * @param {string} serviceName - 服务名称
     */
    static onServiceChange(serviceName) {
        $('#contextNameSelect').empty().append('<option value="">限界上下文</option>');
        $('#domainNameSelect').empty().append('<option value="">领域</option>');
        $('#aggregateNameSelect').empty().append('<option value="">聚合</option>');
        
        if (serviceName) {
            $.get('/contexts', {serviceName: serviceName}, function (data) {
                data.forEach(function (context) {
                    $('#contextNameSelect').append(`<option value="${context}">${context}</option>`);
                });
            });
        }
    }

    /**
     * 上下文选择变化处理
     * @param {string} serviceName - 服务名称
     * @param {string} contextName - 上下文名称
     */
    static onContextChange(serviceName, contextName) {
        $('#domainNameSelect').empty().append('<option value="">领域</option>');
        $('#aggregateNameSelect').empty().append('<option value="">聚合</option>');
        
        if (contextName) {
            $.get('/domains', {serviceName: serviceName, contextName: contextName}, function (data) {
                data.forEach(function (domain) {
                    $('#domainNameSelect').append(`<option value="${domain}">${domain}</option>`);
                });
            });
        }
    }

    /**
     * 领域选择变化处理
     * @param {string} serviceName - 服务名称
     * @param {string} contextName - 上下文名称
     * @param {string} domainName - 领域名称
     */
    static onDomainChange(serviceName, contextName, domainName) {
        $('#aggregateNameSelect').empty().append('<option value="">聚合</option>');
        
        if (serviceName && domainName) {
            $.get('/aggregates', {
                serviceName: serviceName,
                contextName: contextName,
                domainName: domainName
            }, function (data) {
                data.forEach(function (aggregate) {
                    $('#aggregateNameSelect').append(`<option value="${aggregate}">${aggregate}</option>`);
                });
            });
        }
    }

    /**
     * 聚合选择变化处理
     * @param {string} serviceName - 服务名称
     * @param {string} contextName - 上下文名称
     * @param {string} domainName - 领域名称
     * @param {string} aggregateName - 聚合名称
     */
    static onAggregateChange(serviceName, contextName, domainName, aggregateName) {
        // 更新顶部路径显示
        DataManager.updatePathDisplay(serviceName, contextName, domainName, aggregateName);
        
        if (aggregateName) {
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
                    DataManager.fillAggregateData(data);
                },
                error: function (xhr, status, error) {
                    AlertManager.error('加载聚合信息失败：' + error);
                }
            });
        }
    }

    /**
     * 更新顶部路径显示
     * @param {string} serviceName - 服务名称
     * @param {string} contextName - 上下文名称
     * @param {string} domainName - 领域名称
     * @param {string} aggregateName - 聚合名称
     */
    static updatePathDisplay(serviceName, contextName, domainName, aggregateName) {
        $('#currentProject').text('DDD项目');
        $('#currentService').text(serviceName || '服务');
        $('#currentContext').text(contextName || '上下文');
        $('#currentDomain').text(domainName || '领域');
        $('#currentAggregate').text(aggregateName || '聚合');
    }

    /**
     * 填充聚合数据
     * @param {Object} data - 聚合数据
     */
    static fillAggregateData(data) {
        // 基本信息回显
        $('#authorInput').val(data.author);
        $('#versionInput').val(data.version);
        $('#serviceNameInput').val(data.serviceName);
        $('#rootPathInput').val(data.rootPath);
        $('#serviceSubPackageInput').val(data.serviceSubPackage);
        $('#contextNameInput').val(data.contextName);
        $('#domainNameInput').val(data.domainName);
        $('#aggregateNameInput').val(data.aggregateName);
        $('#descriptionInput').val(data.description);

        // 字段信息回显
        $('.field-container .fields').empty();
        data.fields?.forEach((item, index) => {
            const fieldHtml = DOMCore.generateFieldHtml(item, `fields[${index}]`);
            $('.field-container .fields').append(fieldHtml);
        });
    }

    /**
     * 获取表单数据
     * @returns {Object} 表单数据对象
     */
    static getFormData() {
        const formData = {};
        const form = $('#yamlForm')[0];
        const formElements = form.elements;

        for (let i = 0; i < formElements.length; i++) {
            const element = formElements[i];
            if (element.name && element.value) {
                DataTransform.setValue(formData, element.name.split('.'), element.value);
            }
        }

        return formData;
    }

    /**
     * 提交表单数据
     * @param {Function} onSuccess - 成功回调
     * @param {Function} onError - 错误回调
     */
    static submitFormData(onSuccess, onError) {
        const formData = this.getFormData();
        
        $.ajax({
            type: 'POST',
            url: '/generate',
            data: JSON.stringify(formData),
            contentType: 'application/json;type=utf-8',
            success: function (response) {
                if (onSuccess) {
                    onSuccess(response);
                }
            },
            error: function (xhr, status, error) {
                if (onError) {
                    onError(error);
                } else {
                    AlertManager.error('提交失败：' + error);
                }
            }
        });
    }
} 