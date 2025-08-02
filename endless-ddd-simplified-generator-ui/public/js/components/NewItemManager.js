/**
 * 新建项目管理器类
 * 负责处理新建项目、服务、上下文、聚合的功能
 */
class NewItemManager {
    /**
     * 初始化新建项目管理器
     */
    static initialize() {
        this.createModals();
    }

    /**
     * 创建模态框
     */
    static createModals() {
        // 创建模态框容器
        if (!document.getElementById('new-item-modals')) {
            const modalContainer = document.createElement('div');
            modalContainer.id = 'new-item-modals';
            document.body.appendChild(modalContainer);
        }
    }

    /**
     * 显示新建项目模态框
     */
    static showNewProjectModal() {
        const modalHtml = `
            <div class="modal fade" id="newProjectModal" tabindex="-1" aria-labelledby="newProjectModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="newProjectModalLabel">
                                <i class="bi bi-folder-plus"></i> 新建项目
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="newProjectForm">
                                <div class="mb-3">
                                    <label for="newProjectName" class="form-label">项目名称</label>
                                    <input type="text" class="form-control" id="newProjectName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="newProjectDescription" class="form-label">项目描述</label>
                                    <textarea class="form-control" id="newProjectDescription" rows="3"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="newProjectBasePackage" class="form-label">基础包名</label>
                                    <input type="text" class="form-control" id="newProjectBasePackage" required>
                                </div>
                                <div class="mb-3">
                                    <label for="newProjectOutputPath" class="form-label">输出路径</label>
                                    <input type="text" class="form-control" id="newProjectOutputPath" required>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-success" onclick="NewItemManager.createProject()">
                                <i class="bi bi-plus-circle"></i> 创建项目
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        this.showModal(modalHtml, 'newProjectModal');
    }

    /**
     * 显示新建服务模态框
     */
    static showNewServiceModal() {
        const modalHtml = `
            <div class="modal fade" id="newServiceModal" tabindex="-1" aria-labelledby="newServiceModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="newServiceModalLabel">
                                <i class="bi bi-gear-plus"></i> 新建服务
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="newServiceForm">
                                <div class="mb-3">
                                    <label for="newServiceName" class="form-label">服务名称</label>
                                    <input type="text" class="form-control" id="newServiceName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="newServiceDescription" class="form-label">服务描述</label>
                                    <textarea class="form-control" id="newServiceDescription" rows="3"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="newServicePort" class="form-label">服务端口</label>
                                    <input type="number" class="form-control" id="newServicePort" value="8080" required>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-info" onclick="NewItemManager.createService()">
                                <i class="bi bi-plus-circle"></i> 创建服务
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        this.showModal(modalHtml, 'newServiceModal');
    }

    /**
     * 显示新建上下文模态框
     */
    static showNewContextModal() {
        const modalHtml = `
            <div class="modal fade" id="newContextModal" tabindex="-1" aria-labelledby="newContextModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="newContextModalLabel">
                                <i class="bi bi-diagram-3"></i> 新建限界上下文
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="newContextForm">
                                <div class="mb-3">
                                    <label for="newContextService" class="form-label">所属服务</label>
                                    <select class="form-select" id="newContextService" required>
                                        <option value="">选择服务</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="newContextName" class="form-label">上下文名称</label>
                                    <input type="text" class="form-control" id="newContextName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="newContextDescription" class="form-label">上下文描述</label>
                                    <textarea class="form-control" id="newContextDescription" rows="3"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-warning" onclick="NewItemManager.createContext()">
                                <i class="bi bi-plus-circle"></i> 创建上下文
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        this.showModal(modalHtml, 'newContextModal');
        this.loadServicesForContext();
    }

    /**
     * 显示新建聚合模态框
     */
    static showNewAggregateModal() {
        const modalHtml = `
            <div class="modal fade" id="newAggregateModal" tabindex="-1" aria-labelledby="newAggregateModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="newAggregateModalLabel">
                                <i class="bi bi-box"></i> 新建聚合
                            </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="newAggregateForm">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="newAggregateService" class="form-label">所属服务</label>
                                            <select class="form-select" id="newAggregateService" required>
                                                <option value="">选择服务</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="newAggregateContext" class="form-label">所属上下文</label>
                                            <select class="form-select" id="newAggregateContext" required>
                                                <option value="">选择上下文</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="newAggregateDomain" class="form-label">所属领域</label>
                                            <select class="form-select" id="newAggregateDomain" required>
                                                <option value="">选择领域</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="newAggregateName" class="form-label">聚合名称</label>
                                            <input type="text" class="form-control" id="newAggregateName" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="newAggregateDescription" class="form-label">聚合描述</label>
                                    <textarea class="form-control" id="newAggregateDescription" rows="3"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" onclick="NewItemManager.createAggregate()">
                                <i class="bi bi-plus-circle"></i> 创建聚合
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        this.showModal(modalHtml, 'newAggregateModal');
        this.loadServicesForAggregate();
    }

    /**
     * 显示模态框
     * @param {string} modalHtml - 模态框HTML
     * @param {string} modalId - 模态框ID
     */
    static showModal(modalHtml, modalId) {
        // 移除已存在的模态框
        $(`#${modalId}`).remove();
        
        // 添加新模态框
        $('#new-item-modals').append(modalHtml);
        
        // 显示模态框
        const modal = new bootstrap.Modal(document.getElementById(modalId));
        modal.show();
    }

    /**
     * 为上下文模态框加载服务列表
     */
    static loadServicesForContext() {
        $.get('/services', function(data) {
            const select = $('#newContextService');
            select.empty().append('<option value="">选择服务</option>');
            data.forEach(function(service) {
                select.append(`<option value="${service}">${service}</option>`);
            });
        });
    }

    /**
     * 为聚合模态框加载服务列表
     */
    static loadServicesForAggregate() {
        $.get('/services', function(data) {
            const select = $('#newAggregateService');
            select.empty().append('<option value="">选择服务</option>');
            data.forEach(function(service) {
                select.append(`<option value="${service}">${service}</option>`);
            });
        });
    }

    /**
     * 创建项目
     */
    static createProject() {
        const projectData = {
            name: $('#newProjectName').val(),
            description: $('#newProjectDescription').val(),
            basePackage: $('#newProjectBasePackage').val(),
            outputPath: $('#newProjectOutputPath').val()
        };

        if (!projectData.name || !projectData.basePackage || !projectData.outputPath) {
            AlertManager.error('请填写所有必填字段');
            return;
        }

        // 这里应该调用后端API创建项目
        console.log('创建项目:', projectData);
        AlertManager.success('项目创建成功！');
        
        // 关闭模态框
        bootstrap.Modal.getInstance(document.getElementById('newProjectModal')).hide();
    }

    /**
     * 创建服务
     */
    static createService() {
        const serviceData = {
            name: $('#newServiceName').val(),
            description: $('#newServiceDescription').val(),
            port: $('#newServicePort').val()
        };

        if (!serviceData.name) {
            AlertManager.error('请填写服务名称');
            return;
        }

        // 这里应该调用后端API创建服务
        console.log('创建服务:', serviceData);
        AlertManager.success('服务创建成功！');
        
        // 刷新服务列表
        DataManager.loadServices();
        
        // 关闭模态框
        bootstrap.Modal.getInstance(document.getElementById('newServiceModal')).hide();
    }

    /**
     * 创建上下文
     */
    static createContext() {
        const contextData = {
            serviceName: $('#newContextService').val(),
            name: $('#newContextName').val(),
            description: $('#newContextDescription').val()
        };

        if (!contextData.serviceName || !contextData.name) {
            AlertManager.error('请填写所有必填字段');
            return;
        }

        // 这里应该调用后端API创建上下文
        console.log('创建上下文:', contextData);
        AlertManager.success('上下文创建成功！');
        
        // 关闭模态框
        bootstrap.Modal.getInstance(document.getElementById('newContextModal')).hide();
    }

    /**
     * 创建聚合
     */
    static createAggregate() {
        const aggregateData = {
            serviceName: $('#newAggregateService').val(),
            contextName: $('#newAggregateContext').val(),
            domainName: $('#newAggregateDomain').val(),
            name: $('#newAggregateName').val(),
            description: $('#newAggregateDescription').val()
        };

        if (!aggregateData.serviceName || !aggregateData.contextName || 
            !aggregateData.domainName || !aggregateData.name) {
            AlertManager.error('请填写所有必填字段');
            return;
        }

        // 这里应该调用后端API创建聚合
        console.log('创建聚合:', aggregateData);
        AlertManager.success('聚合创建成功！');
        
        // 关闭模态框
        bootstrap.Modal.getInstance(document.getElementById('newAggregateModal')).hide();
    }
} 