# Spring Boot规范目录结构说明

## 目录结构

```
src/main/resources/
├── templates/                    # 模板目录 (Spring Boot标准)
│   └── index.html              # 主页面模板
├── static/                      # 静态资源目录 (Spring Boot标准)
│   ├── css/                    # 样式文件目录
│   │   ├── bootstrap.min.css   # Bootstrap样式
│   │   ├── styles.css          # 自定义样式
│   │   └── main.css           # 主样式文件
│   ├── js/                     # JavaScript文件目录
│   │   ├── jquery.min.js      # jQuery库
│   │   ├── bootstrap.bundle.min.js # Bootstrap JS
│   │   ├── theme.toggle.js    # 主题切换
│   │   ├── transform.js       # 转换工具
│   │   ├── dom-core.js        # DOM核心工具
│   │   ├── script.js          # 主脚本
│   │   ├── AlertManager.js    # 提示管理器类
│   │   ├── ComponentLoader.js # 组件加载器类
│   │   └── ProjectConfigWizard.js # 项目配置向导类
│   └── components/             # HTML组件目录
│       ├── NavigationBar.html  # 导航栏组件
│       ├── ProjectConfigWizard.html # 项目配置向导组件
│       ├── SidebarPanel.html   # 左侧边栏组件
│       ├── MainContentArea.html # 主内容区域组件
│       └── AlertModal.html     # 模态框组件
└── README.md                   # 说明文档
```

## Spring Boot规范说明

### 1. templates目录
- **用途**：存放Thymeleaf模板文件
- **规范**：Spring Boot标准模板目录
- **特点**：支持模板引擎渲染
- **文件**：`index.html` - 主页面模板

### 2. static目录
- **用途**：存放静态资源文件
- **规范**：Spring Boot标准静态资源目录
- **特点**：直接访问，无需模板引擎处理

#### 2.1 css目录
- **用途**：存放CSS样式文件
- **文件**：
  - `bootstrap.min.css` - Bootstrap框架样式
  - `styles.css` - 自定义样式
  - `main.css` - 主样式文件（向导、模块卡片等）

#### 2.2 js目录
- **用途**：存放JavaScript文件
- **文件**：
  - 第三方库：jQuery、Bootstrap等
  - 工具类：主题切换、转换工具等
  - 自定义类：AlertManager、ComponentLoader、ProjectConfigWizard

#### 2.3 components目录
- **用途**：存放HTML组件文件
- **特点**：通过AJAX异步加载
- **文件**：
  - `NavigationBar.html` - 导航栏组件
  - `ProjectConfigWizard.html` - 项目配置向导组件
  - `SidebarPanel.html` - 左侧边栏组件
  - `MainContentArea.html` - 主内容区域组件
  - `AlertModal.html` - 模态框组件

## 命名规范

### HTML文件命名
- **模板文件**：使用小写字母和连字符
  - `index.html`
- **组件文件**：使用PascalCase
  - `NavigationBar.html`
  - `ProjectConfigWizard.html`

### JavaScript文件命名
- **第三方库**：使用原始文件名
  - `jquery.min.js`
  - `bootstrap.bundle.min.js`
- **自定义类**：使用PascalCase
  - `AlertManager.js`
  - `ComponentLoader.js`
  - `ProjectConfigWizard.js`

### CSS文件命名
- **第三方库**：使用原始文件名
  - `bootstrap.min.css`
- **自定义样式**：使用小写字母和连字符
  - `styles.css`
  - `main.css`

## 访问路径

### 模板访问
```
http://localhost:8080/          # 访问主页面模板
```

### 静态资源访问
```
http://localhost:8080/css/main.css                    # CSS文件
http://localhost:8080/js/AlertManager.js              # JS文件
http://localhost:8080/components/NavigationBar.html   # HTML组件
```

## 技术特点

### 1. Spring Boot规范
- **templates目录**：符合Spring Boot模板引擎规范
- **static目录**：符合Spring Boot静态资源规范
- **自动配置**：Spring Boot自动处理静态资源映射

### 2. 组件化设计
- **HTML组件**：独立的HTML片段
- **异步加载**：通过AJAX动态加载组件
- **模块化**：每个功能独立为组件

### 3. 类定义规范
- **ES6类语法**：使用现代JavaScript类定义
- **静态方法**：功能按职责分组
- **JSDoc注释**：完整的代码文档

### 4. 响应式设计
- **Bootstrap框架**：响应式布局
- **主题切换**：支持明暗主题
- **移动端适配**：适配不同屏幕尺寸

## 使用方法

### 1. 访问应用
```
http://localhost:8080/
```

### 2. 组件加载
- 页面自动加载所有组件
- 支持错误处理和重试机制
- 组件状态实时监控

### 3. 项目配置
- 点击"项目配置"进入向导
- 4步引导式配置流程
- 配置数据自动保存

### 4. 代码生成
- 选择要生成的组件
- 配置聚合信息
- 点击"生成代码"

## 扩展指南

### 添加新组件
1. 在`static/components/`目录下创建HTML文件
2. 使用PascalCase命名规范
3. 在`ComponentLoader.js`中添加加载逻辑

### 添加新类
1. 在`static/js/`目录下创建JavaScript文件
2. 使用ES6类语法
3. 在`index.html`中引入脚本文件

### 添加新样式
1. 在`static/css/`目录下创建CSS文件
2. 在`index.html`中引入样式文件
3. 使用CSS变量支持主题切换

## 注意事项

1. **Spring Boot规范**：严格遵循Spring Boot目录结构规范
2. **命名规范**：HTML组件使用PascalCase，其他文件使用小写字母和连字符
3. **路径规范**：静态资源路径以`/static/`开头
4. **模板规范**：模板文件放在`templates`目录下
5. **组件规范**：HTML组件放在`static/components`目录下
6. **错误处理**：所有异步操作都应包含错误处理 