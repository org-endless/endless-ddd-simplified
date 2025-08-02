# EndlessDDD - 构建脚本说明

本目录包含EndlessDDD项目的所有构建和开发脚本。

## 脚本概览

### 环境检查脚本

- `check-env.bat` / `check-env.sh` - 检查并自动安装开发环境依赖
- `install-check.bat` / `install-check.sh` - 安装包运行时的环境检查

### 构建脚本

- `build-jar.bat` / `build-jar.sh` - 构建Spring Boot JAR文件
- `build-installer.bat` / `build-installer.sh` - 构建生产安装包

### 开发脚本

- `dev.bat` / `dev.sh` - 开发模式，编译并启动应用进行调试

## 使用方法

### 1. 首次设置（环境检查）

```bash
# Windows
bin\check-env.bat

# Linux/macOS
bash bin/check-env.sh
```

这些脚本会检查并自动安装：

- Java 21 或更高版本
- Maven 3.6+ (使用项目根路径的mvnw)
- Rust 1.70+
- Tauri CLI

### 2. 开发模式

```bash
# Windows
bin\dev.bat

# Linux/macOS
bash bin/dev.sh
```

开发模式会：

1. 检查环境依赖
2. 构建Spring Boot JAR文件
3. 清理Rust缓存
4. 编译Tauri应用
5. 启动应用进行调试

### 3. 构建JAR文件

```bash
# Windows
bin\build-jar.bat

# Linux/macOS
bash bin/build-jar.sh
```

仅构建Spring Boot JAR文件，用于：

- 更新后端逻辑
- 测试Spring Boot功能
- 准备生产构建

### 4. 构建生产安装包

```bash
# Windows
bin\build-installer.bat

# Linux/macOS
bash bin/build-installer.sh
```

生产构建会：

1. 检查环境依赖
2. 清理所有缓存
3. 构建Spring Boot JAR文件
4. 编译Tauri应用
5. 生成安装包（MSI/DEB/DMG）

## 安装包特性

生成的安装包包含以下特性：

### 自动环境检查

- 检查Java 21+环境
- 自动下载并安装OpenJDK 21
- 配置环境变量

### 进程管理

- 启动时自动启动Spring Boot服务
- 关闭应用时自动清理Spring Boot进程
- 防止端口冲突

### 桌面应用体验

- 原生桌面应用界面
- 响应式布局，适配窗口大小
- 无滚动条，全屏体验

## 文件结构

```TEXT
bin/
├── check-env.bat          # Windows环境检查
├── check-env.sh           # Linux/macOS环境检查
├── build-jar.bat          # Windows JAR构建
├── build-jar.sh           # Linux/macOS JAR构建
├── dev.bat                # Windows开发模式
├── dev.sh                 # Linux/macOS开发模式
├── build-installer.bat    # Windows生产构建
├── build-installer.sh     # Linux/macOS生产构建
├── install-check.bat      # Windows安装环境检查
├── install-check.sh       # Linux/macOS安装环境检查
└── README.md              # 本文档
```

## Maven构建说明

项目使用Maven Wrapper (mvnw)进行构建，确保版本一致性：

- **Windows**: `mvnw.cmd clean package`
- **Linux/macOS**: `./mvnw clean package`

所有脚本都会自动使用项目根路径的mvnw，无需单独安装Maven。

## 故障排除

### 常见问题

1. **Java版本过低**
   - 运行环境检查脚本自动安装Java 21
   - 或手动安装OpenJDK 21

2. **Maven构建失败**
   - 检查网络连接
   - 清理Maven缓存：`./mvnw clean`
   - 检查settings.xml配置

3. **Rust编译失败**
   - 更新Rust：`rustup update`
   - 清理缓存：`cargo clean`
   - 检查依赖版本

4. **端口60001被占用**
   - 脚本会自动检测并清理占用进程
   - 或手动终止：`taskkill /F /IM java.exe`

### 调试技巧

1. **查看详细日志**

   ```bash
   # 启用详细Maven输出
   ./mvnw clean package -X

   # 启用详细Cargo输出
   cargo build -vv
   ```

2. **检查环境变量**

   ```bash
   # Windows
   echo %JAVA_HOME%
   echo %PATH%

   # Linux/macOS
   echo $JAVA_HOME
   echo $PATH
   ```

3. **验证安装**

   ```bash
   java -version
   ./mvnw -version
   rustc --version
   cargo tauri --version
   ```

## 开发流程

1. **日常开发**

   ```bash
   bin/dev.bat  # 或 bin/dev.sh
   ```

2. **更新后端逻辑**

   ```bash
   bin/build-jar.bat  # 或 bin/build-jar.sh
   bin/dev.bat        # 重新启动应用
   ```

3. **发布新版本**

   ```bash
   bin/build-installer.bat  # 或 bin/build-installer.sh
   ```

## 注意事项

- 所有脚本都会自动检查环境依赖
- 生产构建会清理所有缓存确保干净构建
- 开发模式会保留缓存以提高构建速度
- 安装包会自动处理Java环境依赖
- 应用关闭时会自动清理Spring Boot进程
- 使用Maven Wrapper确保构建环境一致性
