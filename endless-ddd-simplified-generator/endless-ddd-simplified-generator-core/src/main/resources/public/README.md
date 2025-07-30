# DDD代码生成器前端

## 项目创建流程

### 步骤1：项目基本信息
- **项目构件ID**: 项目的唯一标识符
- **组织ID**: Maven Group ID
- **项目名称**: 项目的显示名称
- **项目版本**: 项目版本号（默认1.0.0）
- **项目作者**: 项目作者信息
- **项目根路径**: 项目的根目录路径
- **项目基础包名**: Java包的基础路径
- **项目描述**: 项目的详细描述

### 步骤2：技术栈配置
- **Java版本**: 选择Java 8、11、17或21
- **日志框架**: 选择Logback、Log4j2或Log4j
- **持久化框架**: 选择MyBatis Plus、MyBatis或JPA
- **Spring Doc**: 是否启用Spring Doc文档

### 步骤3：服务配置
- **服务构件ID列表**: 添加一个或多个服务构件ID
  - 点击"添加服务构件ID"按钮可以添加更多服务
  - 点击删除按钮可以移除不需要的服务

### 步骤4：确认配置
- 显示所有配置的摘要信息
- 确认无误后点击"完成配置"按钮

## 功能特性

### ✅ 表单验证
- 每个步骤都有相应的验证规则
- 必填字段检查
- 数据格式验证

### ✅ 数据持久化
- 配置数据自动保存到本地存储
- 页面刷新后自动恢复配置

### ✅ 后端集成
- 配置完成后自动发送到后端API
- 支持项目创建和代码生成

### ✅ 响应式设计
- 支持桌面和移动设备
- 自适应布局

## 技术栈

- **HTML5**: 语义化标记
- **CSS3**: 响应式样式
- **JavaScript ES6+**: 现代JavaScript特性
- **Bootstrap 5**: UI框架
- **Bootstrap Icons**: 图标库

## 文件结构

```
static/
├── components/
│   ├── ProjectConfigWizard.html    # 项目配置向导
│   ├── NavigationBar.html          # 导航栏
│   ├── SidebarPanel.html           # 侧边栏
│   ├── MainContentArea.html        # 主内容区域
│   └── AlertModal.html             # 警告模态框
├── css/
│   ├── base.css                    # 基础样式
│   ├── components.css              # 组件样式
│   └── bootstrap.min.css           # Bootstrap样式
├── js/
│   ├── app.js                      # 主应用
│   ├── ProjectConfigWizard.js      # 项目配置向导
│   ├── AlertManager.js             # 警告管理器
│   ├── core/
│   │   ├── DataManager.js          # 数据管理器
│   │   ├── DataTransform.js        # 数据转换
│   │   └── DOMCore.js              # DOM核心
│   └── ui/
│       └── TabManager.js           # 标签页管理器
└── README.md                       # 说明文档
```

## 使用方法

1. 打开应用首页
2. 点击"新建项目"按钮
3. 按照向导步骤填写项目信息
4. 在最后一步确认配置
5. 点击"完成配置"创建项目

## API接口

### 项目创建接口
- **URL**: `/api/generator/project`
- **方法**: `POST`
- **Content-Type**: `application/json`

### 请求参数
```json
{
  "projectArtifactId": "项目构件ID",
  "groupId": "组织ID",
  "name": "项目名称",
  "description": "项目描述",
  "version": "项目版本",
  "author": "项目作者",
  "rootPath": "项目根路径",
  "basePackage": "项目基础包名",
  "enableSpringDoc": "true",
  "javaVersion": "JAVA_11",
  "loggingFramework": "LOGBACK",
  "persistenceFramework": "MYBATIS_PLUS",
  "serviceArtifactIds": ["服务1", "服务2"]
}
```

## 开发说明

### 添加新字段
1. 在`ProjectConfigWizard.html`中添加表单字段
2. 在`ProjectConfigWizard.js`中添加验证逻辑
3. 更新配置保存和加载方法
4. 更新配置摘要生成方法

### 修改验证规则
1. 在`ProjectConfigWizard.js`的`validateStep*`方法中修改验证逻辑
2. 确保验证错误信息清晰明确

### 自定义样式
1. 在`components.css`中添加自定义样式
2. 使用CSS变量支持主题切换
3. 确保响应式设计

## 注意事项

- 所有表单字段都有相应的验证规则
- 配置数据会自动保存到浏览器本地存储
- 支持深色和浅色主题切换
- 移动设备友好的响应式设计 