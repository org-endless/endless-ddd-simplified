/**
 * AppBar组件
 * 统一的顶部导航栏
 */
class AppBar {
    constructor() {
        this.isDarkMode = this.getStoredTheme() === 'dark';
        this.init();
    }

    /**
     * 初始化AppBar
     */
    init() {
        this.createAppBar();
        this.setupEventListeners();
        this.applyTheme();
        this.updateThemeIcon();
    }

    /**
     * 创建AppBar HTML结构
     */
    createAppBar() {
        const appBar = document.createElement('div');
        appBar.id = 'appBar';
        appBar.className = 'app-bar';

        // 根据当前页面路径确定图片路径
        const imagePath = this.getImagePath();

        appBar.innerHTML = `
            <div class="app-bar-container">
                <div class="app-bar-left">
                    <a href="/endless-ddd-simplified-generator-ui/public" class="app-bar-brand">
                        <img src="${imagePath}" alt="Endless DDD" class="app-bar-logo">
                        <span class="app-bar-title">Endless DDD</span>
                        <span class="app-bar-subtitle">Simplified Generator</span>
                    </a>
                </div>
                
                <div class="app-bar-center">
                    <!-- 中间区域预留，后续添加功能按钮 -->
                </div>
                
                <div class="app-bar-right">
                    <div class="app-bar-actions">
                        <button class="btn btn-outline-secondary btn-sm theme-toggle" id="themeToggle" title="切换主题">
                            <i class="bi bi-sun-fill light-icon"></i>
                            <i class="bi bi-moon-fill dark-icon"></i>
                        </button>
                    </div>
                </div>
            </div>
        `;

        // 插入到页面顶部
        document.body.insertBefore(appBar, document.body.firstChild);
    }

    /**
     * 根据当前页面路径获取图片路径
     * @returns {string} 图片路径
     */
    getImagePath() {
        const currentPath = window.location.pathname;

        // 简单的路径检测
        if (currentPath.includes('/components/')) {
            return '../image/endless-favicon-256.svg';
        }

        // 默认返回根目录路径
        return 'image/endless-favicon-256.svg';
    }

    /**
     * 设置事件监听器
     */
    setupEventListeners() {
        const themeToggle = document.getElementById('themeToggle');
        if (themeToggle) {
            // 移除可能存在的旧事件监听器
            themeToggle.removeEventListener('click', this.toggleTheme.bind(this));
            // 添加新的事件监听器
            themeToggle.addEventListener('click', () => {
                this.toggleTheme();
            });
        }
    }

    /**
     * 切换主题
     */
    toggleTheme() {
        this.isDarkMode = !this.isDarkMode;
        this.applyTheme();
        this.saveTheme();
        this.updateThemeIcon();
    }

    /**
     * 应用主题
     */
    applyTheme() {
        const html = document.documentElement;
        if (this.isDarkMode) {
            html.setAttribute('data-bs-theme', 'dark');
            html.classList.add('dark-mode');
            // 设置暗黑模式背景
            document.body.style.backgroundColor = 'var(--dark-bg-primary)';
            document.body.style.color = 'var(--dark-text-primary)';
        } else {
            html.setAttribute('data-bs-theme', 'light');
            html.classList.remove('dark-mode');
            // 恢复明亮模式背景
            document.body.style.backgroundColor = '';
            document.body.style.color = '';
        }
    }

    /**
     * 更新主题图标
     */
    updateThemeIcon() {
        const themeToggle = document.getElementById('themeToggle');
        if (themeToggle) {
            const lightIcon = themeToggle.querySelector('.light-icon');
            const darkIcon = themeToggle.querySelector('.dark-icon');

            if (this.isDarkMode) {
                lightIcon.style.display = 'none';
                darkIcon.style.display = 'inline-block';
            } else {
                lightIcon.style.display = 'inline-block';
                darkIcon.style.display = 'none';
            }
        }
    }

    /**
     * 保存主题设置
     */
    saveTheme() {
        localStorage.setItem('endless-ddd-theme', this.isDarkMode ? 'dark' : 'light');
    }

    /**
     * 获取存储的主题设置
     */
    getStoredTheme() {
        return localStorage.getItem('endless-ddd-theme') || 'light';
    }

    /**
     * 添加中间功能按钮
     * @param {string} id - 按钮ID
     * @param {string} text - 按钮文本
     * @param {string} icon - 图标类名
     * @param {Function} onClick - 点击回调
     */
    addCenterButton(id, text, icon, onClick) {
        const center = document.querySelector('.app-bar-center');
        if (center) {
            const button = document.createElement('button');
            button.id = id;
            button.className = 'btn btn-outline-primary btn-sm app-bar-btn';
            button.innerHTML = `<i class="bi ${icon}"></i> ${text}`;
            button.addEventListener('click', onClick);
            center.appendChild(button);
        }
    }

    /**
     * 添加右侧功能按钮
     * @param {string} id - 按钮ID
     * @param {string} text - 按钮文本
     * @param {string} icon - 图标类名
     * @param {Function} onClick - 点击回调
     */
    addRightButton(id, text, icon, onClick) {
        const actions = document.querySelector('.app-bar-actions');
        if (actions) {
            const button = document.createElement('button');
            button.id = id;
            button.className = 'btn btn-outline-secondary btn-sm app-bar-btn';
            button.innerHTML = `<i class="bi ${icon}"></i> ${text}`;
            button.addEventListener('click', onClick);
            actions.appendChild(button);
        }
    }
}

// 在浏览器环境中暴露到全局
if (typeof window !== 'undefined') {
    window.AppBar = AppBar;
}
