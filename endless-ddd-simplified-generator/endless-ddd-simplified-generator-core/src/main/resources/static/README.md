# 静态资源模块化结构说明

## 目录结构

```
static/
├── css/                          # 样式文件目录
│   ├── bootstrap.min.css        # Bootstrap框架样式 (第三方)
│   ├── base.css                 # 基础样式 (自定义)
│   └── components.css           # 组件样式 (自定义)
├── js/                          # JavaScript文件目录
│   ├── core/                    # 核心模块目录
│   │   ├── DataManager.js       # 数据管理器
│   │   ├── DOMCore.js           # DOM核心工具
│   │   └── DataTransform.js     # 数据转换工具
│   ├── ui/                      # UI模块目录
│   │   └── TabManager.js        # 标签页管理器
│   ├── jquery.min.js            # jQuery库 (第三方)
│   ├── popper.min.js            # Popper.js库 (第三方)
│   ├── bootstrap.bundle.min.js  # Bootstrap JS库 (第三方)
│   ├── theme.toggle.js          # 主题切换 (第三方)
│   ├── AlertManager.js          # 提示管理器 (自定义)
│   ├── ComponentLoader.js       # 组件加载器 (自定义)
│   ├── ProjectConfigWizard.js   # 项目配置向导 (自定义)
│   └── app.js                   # 主应用脚本 (自定义)
└── components/                   # HTML组件目录
    ├── NavigationBar.html       # 导航栏组件
    ├── ProjectConfigWizard.html # 项目配置向导组件
    ├── SidebarPanel.html        # 左侧边栏组件
    ├── MainContentArea.html     # 主内容区域组件
    └── AlertModal.html          # 模态框组件
```

## JavaScript模块化架构

### 1. 第三方库 (必需)
- **jquery.min.js** - DOM操作和AJAX请求
- **bootstrap.bundle.min.js** - UI组件和交互
- **popper.min.js** - Bootstrap依赖库
- **theme.toggle.js** - Bootstrap主题切换

### 2. 核心模块 (core/)
- **DataManager.js** - 数据管理器
  - 服务/上下文/领域/聚合级联选择
  - 表单数据获取和提交
  - 聚合信息回显
- **DOMCore.js** - DOM核心工具
  - 动态元素生成
  - 字段和枚举值处理
  - HTML模板生成
- **DataTransform.js** - 数据转换工具
  - 表单数据转换
  - 数据验证和清理
  - 数组处理

### 3. UI模块 (ui/)
- **TabManager.js** - 标签页管理器
  - 标签页切换逻辑
  - 状态管理
  - 动态标签页操作

### 4. 工具模块
- **AlertManager.js** - 提示管理器
  - 成功/错误/警告/信息提示
  - 确认对话框
  - 加载提示
- **ComponentLoader.js** - 组件加载器
  - HTML组件异步加载
  - 错误处理和重试
  - 组件状态监控
- **ProjectConfigWizard.js** - 项目配置向导
  - 4步引导流程
  - 配置数据保存/加载
  - 表单状态管理

### 5. 主应用 (app.js)
- **App类** - 主应用类
  - 模块初始化和协调
  - 全局事件绑定
  - 应用状态管理

## CSS模块化架构

### 1. 第三方样式
- **bootstrap.min.css** - Bootstrap框架样式

### 2. 自定义样式
- **base.css** - 基础样式
  - 标签页样式
  - 按钮样式
  - 表单样式
  - 网格布局
  - 响应式设计
- **components.css** - 组件样式
  - 向导样式
  - 模块卡片样式
  - 配置区域样式
  - 主题适配

## 模块依赖关系

```
app.js
├── DataManager.js
│   ├── DataTransform.js
│   └── DOMCore.js
├── TabManager.js
├── AlertManager.js
├── ComponentLoader.js
└── ProjectConfigWizard.js
```

## 加载顺序

### JavaScript加载顺序
1. **第三方库** (jQuery, Bootstrap, Popper)
2. **核心模块** (DataTransform, DOMCore, DataManager)
3. **UI模块** (TabManager)
4. **工具模块** (AlertManager, ComponentLoader, ProjectConfigWizard)
5. **主题模块** (theme.toggle)
6. **主应用** (app.js)

### CSS加载顺序
1. **第三方样式** (Bootstrap)
2. **基础样式** (base.css)
3. **组件样式** (components.css)

## 模块职责分工

### 数据层
- **DataManager** - 数据获取、提交、回显
- **DataTransform** - 数据转换、验证、清理

### 视图层
- **DOMCore** - DOM操作、模板生成
- **TabManager** - 标签页管理
- **ComponentLoader** - 组件加载

### 交互层
- **AlertManager** - 用户提示
- **ProjectConfigWizard** - 配置向导

### 应用层
- **App** - 模块协调、事件管理

## 扩展指南

### 添加新模块
1. 在相应目录下创建模块文件
2. 使用ES6类语法和静态方法
3. 在app.js中初始化模块
4. 在index.html中引入脚本

### 添加新样式
1. 在css目录下创建样式文件
2. 按功能分类组织样式
3. 在index.html中引入样式文件

### 添加新组件
1. 在components目录下创建HTML文件
2. 在ComponentLoader.js中添加加载逻辑
3. 使用PascalCase命名规范

## 性能优化

### 文件压缩
- 生产环境应使用压缩版本的第三方库
- 自定义JS和CSS文件应进行压缩

### 缓存策略
- 第三方库使用CDN和长期缓存
- 自定义文件使用版本号控制缓存

### 按需加载
- 非核心功能可考虑按需加载
- 大型组件可考虑懒加载

## 维护指南

### 代码规范
- 使用ES6+语法
- 遵循JSDoc注释规范
- 使用PascalCase命名类
- 使用camelCase命名方法

### 错误处理
- 所有异步操作都应包含错误处理
- 使用AlertManager统一提示错误
- 记录详细错误日志

### 测试建议
- 单元测试各个模块
- 集成测试模块间交互
- 端到端测试用户流程 