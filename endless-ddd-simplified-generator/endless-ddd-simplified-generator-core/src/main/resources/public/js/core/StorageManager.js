/**
 * 浏览器存储管理器
 * 统一管理浏览器存储，包括项目信息、文件路径等元数据
 */
class StorageManager {
    constructor() {
        this.STORAGE_KEYS = {
            PROJECT_INFO: 'endless_ddd_project_info',
            FILE_PATHS: 'endless_ddd_file_paths',
            RECENT_PROJECTS: 'endless_ddd_recent_projects',
            USER_PREFERENCES: 'endless_ddd_user_preferences',
            SESSION_DATA: 'endless_ddd_session_data'
        };
        
        this.init();
    }

    /**
     * 初始化存储管理器
     */
    init() {
        // 检查是否需要清理存储（强制刷新时）
        this.checkForForceRefresh();
        
        // 初始化默认值
        this.initializeDefaults();
    }

    /**
     * 检查强制刷新
     */
    checkForForceRefresh() {
        const forceRefresh = sessionStorage.getItem('force_refresh');
        if (forceRefresh === 'true') {
            this.clearAllData();
            sessionStorage.removeItem('force_refresh');
        }
    }

    /**
     * 初始化默认值
     */
    initializeDefaults() {
        if (!this.getProjectInfo()) {
            this.setProjectInfo({
                name: '',
                path: '',
                lastOpened: null,
                isOpen: false
            });
        }

        if (!this.getRecentProjects()) {
            this.setRecentProjects([]);
        }

        if (!this.getUserPreferences()) {
            this.setUserPreferences({
                theme: 'light',
                language: 'zh-CN',
                autoSave: true,
                showLineNumbers: true
            });
        }
    }

    /**
     * 保存项目信息
     * @param {Object} projectInfo 项目信息
     */
    setProjectInfo(projectInfo) {
        try {
            localStorage.setItem(this.STORAGE_KEYS.PROJECT_INFO, JSON.stringify(projectInfo));
            this.updateRecentProjects(projectInfo);
        } catch (error) {
            console.error('保存项目信息失败:', error);
        }
    }

    /**
     * 获取项目信息
     * @returns {Object|null} 项目信息
     */
    getProjectInfo() {
        try {
            const data = localStorage.getItem(this.STORAGE_KEYS.PROJECT_INFO);
            return data ? JSON.parse(data) : null;
        } catch (error) {
            console.error('获取项目信息失败:', error);
            return null;
        }
    }

    /**
     * 保存文件路径列表
     * @param {Array} filePaths 文件路径列表
     */
    setFilePaths(filePaths) {
        try {
            localStorage.setItem(this.STORAGE_KEYS.FILE_PATHS, JSON.stringify(filePaths));
        } catch (error) {
            console.error('保存文件路径失败:', error);
        }
    }

    /**
     * 获取文件路径列表
     * @returns {Array} 文件路径列表
     */
    getFilePaths() {
        try {
            const data = localStorage.getItem(this.STORAGE_KEYS.FILE_PATHS);
            return data ? JSON.parse(data) : [];
        } catch (error) {
            console.error('获取文件路径失败:', error);
            return [];
        }
    }

    /**
     * 更新最近项目列表
     * @param {Object} projectInfo 项目信息
     */
    updateRecentProjects(projectInfo) {
        try {
            let recentProjects = this.getRecentProjects();
            
            // 移除已存在的相同路径项目
            recentProjects = recentProjects.filter(p => p.path !== projectInfo.path);
            
            // 添加到开头
            recentProjects.unshift({
                name: projectInfo.name,
                path: projectInfo.path,
                lastOpened: new Date().toISOString()
            });
            
            // 限制最近项目数量为10个
            recentProjects = recentProjects.slice(0, 10);
            
            this.setRecentProjects(recentProjects);
        } catch (error) {
            console.error('更新最近项目失败:', error);
        }
    }

    /**
     * 保存最近项目列表
     * @param {Array} recentProjects 最近项目列表
     */
    setRecentProjects(recentProjects) {
        try {
            localStorage.setItem(this.STORAGE_KEYS.RECENT_PROJECTS, JSON.stringify(recentProjects));
        } catch (error) {
            console.error('保存最近项目失败:', error);
        }
    }

    /**
     * 获取最近项目列表
     * @returns {Array} 最近项目列表
     */
    getRecentProjects() {
        try {
            const data = localStorage.getItem(this.STORAGE_KEYS.RECENT_PROJECTS);
            return data ? JSON.parse(data) : [];
        } catch (error) {
            console.error('获取最近项目失败:', error);
            return [];
        }
    }

    /**
     * 保存用户偏好设置
     * @param {Object} preferences 用户偏好设置
     */
    setUserPreferences(preferences) {
        try {
            localStorage.setItem(this.STORAGE_KEYS.USER_PREFERENCES, JSON.stringify(preferences));
        } catch (error) {
            console.error('保存用户偏好设置失败:', error);
        }
    }

    /**
     * 获取用户偏好设置
     * @returns {Object} 用户偏好设置
     */
    getUserPreferences() {
        try {
            const data = localStorage.getItem(this.STORAGE_KEYS.USER_PREFERENCES);
            return data ? JSON.parse(data) : {};
        } catch (error) {
            console.error('获取用户偏好设置失败:', error);
            return {};
        }
    }

    /**
     * 保存会话数据
     * @param {Object} sessionData 会话数据
     */
    setSessionData(sessionData) {
        try {
            sessionStorage.setItem(this.STORAGE_KEYS.SESSION_DATA, JSON.stringify(sessionData));
        } catch (error) {
            console.error('保存会话数据失败:', error);
        }
    }

    /**
     * 获取会话数据
     * @returns {Object} 会话数据
     */
    getSessionData() {
        try {
            const data = sessionStorage.getItem(this.STORAGE_KEYS.SESSION_DATA);
            return data ? JSON.parse(data) : {};
        } catch (error) {
            console.error('获取会话数据失败:', error);
            return {};
        }
    }

    /**
     * 打开项目
     * @param {string} projectPath 项目路径
     * @param {string} projectName 项目名称
     */
    openProject(projectPath, projectName = '') {
        const projectInfo = {
            name: projectName || this.extractProjectName(projectPath),
            path: projectPath,
            lastOpened: new Date().toISOString(),
            isOpen: true
        };
        
        this.setProjectInfo(projectInfo);
        return projectInfo;
    }

    /**
     * 关闭项目
     */
    closeProject() {
        const projectInfo = this.getProjectInfo();
        if (projectInfo) {
            projectInfo.isOpen = false;
            this.setProjectInfo(projectInfo);
        }
    }

    /**
     * 从路径中提取项目名称
     * @param {string} path 项目路径
     * @returns {string} 项目名称
     */
    extractProjectName(path) {
        if (!path) return '';
        
        // 移除末尾的斜杠
        const cleanPath = path.replace(/[\/\\]$/, '');
        
        // 获取最后一个路径段作为项目名称
        const pathParts = cleanPath.split(/[\/\\]/);
        return pathParts[pathParts.length - 1] || '未命名项目';
    }

    /**
     * 检查项目是否已打开
     * @returns {boolean} 是否已打开
     */
    isProjectOpen() {
        const projectInfo = this.getProjectInfo();
        return projectInfo && projectInfo.isOpen;
    }

    /**
     * 获取当前项目路径
     * @returns {string} 项目路径
     */
    getCurrentProjectPath() {
        const projectInfo = this.getProjectInfo();
        return projectInfo ? projectInfo.path : '';
    }

    /**
     * 清理所有数据
     */
    clearAllData() {
        try {
            Object.values(this.STORAGE_KEYS).forEach(key => {
                localStorage.removeItem(key);
                sessionStorage.removeItem(key);
            });
        } catch (error) {
            console.error('清理存储数据失败:', error);
        }
    }

    /**
     * 强制刷新时清理数据
     */
    forceRefresh() {
        sessionStorage.setItem('force_refresh', 'true');
        window.location.reload();
    }

    /**
     * 导出存储数据
     * @returns {Object} 存储数据
     */
    exportData() {
        return {
            projectInfo: this.getProjectInfo(),
            filePaths: this.getFilePaths(),
            recentProjects: this.getRecentProjects(),
            userPreferences: this.getUserPreferences(),
            sessionData: this.getSessionData()
        };
    }

    /**
     * 导入存储数据
     * @param {Object} data 存储数据
     */
    importData(data) {
        try {
            if (data.projectInfo) this.setProjectInfo(data.projectInfo);
            if (data.filePaths) this.setFilePaths(data.filePaths);
            if (data.recentProjects) this.setRecentProjects(data.recentProjects);
            if (data.userPreferences) this.setUserPreferences(data.userPreferences);
            if (data.sessionData) this.setSessionData(data.sessionData);
        } catch (error) {
            console.error('导入存储数据失败:', error);
        }
    }
}

// 导出到全局
window.StorageManager = StorageManager; 