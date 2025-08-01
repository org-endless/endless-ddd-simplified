# 文件浏览器功能说明

## 概述

文件浏览器功能允许用户选择本地文件夹，浏览项目结构，并将项目信息保存到浏览器存储中。这类似于IDE的文件浏览器功能。

## 主要功能

### 1. 文件夹选择
- 支持现代浏览器的 File System Access API
- 降级支持传统文件输入方式
- 自动检测浏览器兼容性

### 2. 文件树显示
- 显示完整的文件目录结构
- 区分文件和文件夹
- 显示文件大小
- 支持层级缩进

### 3. 浏览器存储管理
- 统一管理项目信息
- 保存最近项目列表
- 用户偏好设置
- 会话数据管理

### 4. 数据持久化
- 项目信息自动保存
- 最近项目列表维护
- 强制刷新时清理数据
- 其他时候保持元数据

## 技术实现

### StorageManager.js
统一管理浏览器存储的核心类：

```javascript
// 存储键定义
STORAGE_KEYS = {
    PROJECT_INFO: 'endless_ddd_project_info',
    FILE_PATHS: 'endless_ddd_file_paths',
    RECENT_PROJECTS: 'endless_ddd_recent_projects',
    USER_PREFERENCES: 'endless_ddd_user_preferences',
    SESSION_DATA: 'endless_ddd_session_data'
}
```

### FileExplorer.js
文件浏览器组件：

```javascript
class FileExplorer {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.storageManager = new StorageManager();
        this.fileTree = [];
        this.currentPath = '';
    }
}
```

## 使用方法

### 1. 访问文件浏览器
- 从主页点击"文件浏览器"按钮
- 或直接访问 `components/FileExplorer.html`

### 2. 选择文件夹
- 点击"选择文件夹"按钮
- 在文件对话框中选择要浏览的文件夹
- 系统会自动读取文件夹结构

### 3. 浏览文件
- 文件树会显示所有文件和文件夹
- 点击文件夹可以展开/折叠
- 文件大小会自动显示

### 4. 管理项目
- 最近项目会自动保存
- 可以快速重新打开项目
- 项目信息会持久化保存

## 存储策略

### 数据分类
1. **项目信息** - 当前打开的项目详情
2. **文件路径** - 项目的文件结构
3. **最近项目** - 最近打开的项目列表
4. **用户偏好** - 主题、语言等设置
5. **会话数据** - 临时会话信息

### 清理策略
- **强制刷新**：清理所有数据
- **正常操作**：保持元数据，更新项目信息
- **手动清理**：通过设置界面清理

## 浏览器兼容性

### 现代浏览器
- Chrome 86+
- Edge 86+
- Firefox 85+

支持 File System Access API，可以直接选择文件夹。

### 传统浏览器
- 使用 `<input type="file" webkitdirectory>` 降级方案
- 支持文件夹选择但功能有限

## 文件结构

```
public/
├── js/
│   ├── core/
│   │   └── StorageManager.js      # 存储管理器
│   └── components/
│       └── FileExplorer.js        # 文件浏览器组件
├── components/
│   └── FileExplorer.html          # 文件浏览器页面
└── test-file-explorer.html        # 测试页面
```

## 测试

访问 `test-file-explorer.html` 可以测试：
- 存储管理器功能
- 文件浏览器组件
- 数据持久化
- 清理功能

## 注意事项

1. **安全限制**：由于浏览器安全策略，无法直接获取完整文件路径
2. **权限要求**：需要用户明确授权才能访问文件夹
3. **数据限制**：浏览器存储有容量限制，大项目可能无法完全保存
4. **兼容性**：不同浏览器的文件API支持程度不同

## 未来改进

1. **文件内容预览**：支持查看文件内容
2. **搜索功能**：在文件树中搜索文件
3. **拖拽支持**：支持拖拽文件到界面
4. **多项目管理**：同时管理多个项目
5. **云端同步**：支持将项目信息同步到云端 