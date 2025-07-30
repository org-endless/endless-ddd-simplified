/**
 * 组件加载器类
 * 负责异步加载HTML组件
 */
class ComponentLoader {
    /**
     * 加载单个组件
     * @param {string} containerId - 容器元素ID
     * @param {string} componentPath - 组件文件路径
     * @returns {Promise<void>}
     */
    static async loadComponent(containerId, componentPath) {
        try {
            const response = await fetch(componentPath);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const html = await response.text();
            const container = document.getElementById(containerId);
            if (container) {
                container.innerHTML = html;
                console.log(`组件加载成功: ${componentPath}`);
            } else {
                console.error(`容器元素不存在: ${containerId}`);
            }
        } catch (error) {
            console.error(`组件加载失败 ${componentPath}:`, error);
            this.handleLoadError(containerId, componentPath, error);
        }
    }

    /**
     * 加载所有组件
     * @returns {Promise<void>}
     */
    static async loadAllComponents() {
        const components = [
            { containerId: 'navbar-container', path: '/components/NavigationBar.html' },
            { containerId: 'project-wizard-container', path: '/components/ProjectConfigWizard.html' },
            { containerId: 'sidebar-container', path: '/components/SidebarPanel.html' },
            { containerId: 'main-content-container', path: '/components/MainContentArea.html' },
            { containerId: 'modal-container', path: '/components/AlertModal.html' }
        ];

        try {
            await Promise.all(
                components.map(component => 
                    this.loadComponent(component.containerId, component.path)
                )
            );
            console.log('所有组件加载完成');
        } catch (error) {
            console.error('组件加载过程中发生错误:', error);
        }
    }

    /**
     * 处理加载错误
     * @param {string} containerId - 容器ID
     * @param {string} componentPath - 组件路径
     * @param {Error} error - 错误对象
     */
    static handleLoadError(containerId, componentPath, error) {
        const container = document.getElementById(containerId);
        if (container) {
            container.innerHTML = `
                <div class="alert alert-danger">
                    <h5>组件加载失败</h5>
                    <p>路径: ${componentPath}</p>
                    <p>错误: ${error.message}</p>
                    <button class="btn btn-primary" onclick="ComponentLoader.retryLoad('${containerId}', '${componentPath}')">
                        重试
                    </button>
                </div>
            `;
        }
    }

    /**
     * 重试加载组件
     * @param {string} containerId - 容器ID
     * @param {string} componentPath - 组件路径
     */
    static async retryLoad(containerId, componentPath) {
        console.log(`重试加载组件: ${componentPath}`);
        await this.loadComponent(containerId, componentPath);
    }

    /**
     * 检查组件是否已加载
     * @param {string} containerId - 容器ID
     * @returns {boolean}
     */
    static isComponentLoaded(containerId) {
        const container = document.getElementById(containerId);
        return container && container.children.length > 0;
    }

    /**
     * 获取已加载的组件列表
     * @returns {string[]}
     */
    static getLoadedComponents() {
        const containers = [
            'navbar-container',
            'project-wizard-container', 
            'sidebar-container',
            'main-content-container',
            'modal-container'
        ];
        
        return containers.filter(containerId => this.isComponentLoaded(containerId));
    }
} 