# Endless DDD Simplified Generator

基于Spring Boot和Tauri的DDD项目生成器，支持Web界面和桌面应用。

## 📁 目录结构

```
tauri/
├── build/                    # 构建脚本和输出
│   ├── bin/                  # 构建脚本
│   │   ├── build.bat        # Windows构建脚本
│   │   ├── build.sh         # Linux/macOS构建脚本
│   │   ├── dev.bat          # Windows开发脚本
│   │   └── dev.sh           # Linux/macOS开发脚本
│   └── output/              # 构建输出目录
│       ├── source/           # 可运行的应用包
│       │   ├── lib/          # JAR文件目录
│       │   ├── config/       # 配置文件目录
│       │   ├── public/       # 静态文件目录
│       │   ├── start.bat     # Windows启动脚本
│       │   ├── start.sh      # Linux/macOS启动脚本
│       │   └── *.exe         # 可执行文件
│       └── endless-ddd-generator-YYYYMMDD-HHMMSS/  # 完整安装包
├── public/                   # 静态资源文件
│   ├── css/                 # 样式文件
│   ├── js/                  # JavaScript文件
│   ├── components/          # 组件文件
│   ├── image/               # 图片资源
│   └── index.html           # 主页面
├── src/                     # Rust运行时源码
├── src-tauri/               # Tauri桌面应用源码
├── Cargo.toml              # Rust项目配置
├── tauri.conf.json         # Tauri配置文件
└── README.md               # 项目说明
```

## 🚀 快速开始

### 环境要求

- **Rust**: 1.70+ (https://rustup.rs/)
- **Java**: JDK 21+ (https://adoptium.net/)
- **Maven**: 3.6+ (https://maven.apache.org/)

### 图形界面安装程序

项目现在提供图形界面安装程序，支持中英文界面：

#### Windows 用户
```bash
# 运行图形界面安装程序
install.bat
```

#### Linux/macOS 用户
```bash
# 运行图形界面安装程序
chmod +x install.sh
./install.sh
```

#### 手动启动
如果自动启动失败，可以手动打开 `installer.html` 文件。

**安装程序特性：**
- 🌍 支持中英文双语界面
- 🎨 现代化图形界面设计
- 📋 分步安装向导
- 🔧 自动系统检测
- 📁 自定义安装路径
- ⚡ 实时安装进度显示

### 构建完整应用

#### Windows
```bash
cd build/bin
build.bat
```

#### Linux/macOS
```bash
cd build/bin
chmod +x build.sh
./build.sh
```

### 开发模式

#### Windows
```bash
cd build/bin
dev.bat
```

#### Linux/macOS
```bash
cd build/bin
chmod +x dev.sh
./dev.sh
```

## 🔧 构建流程

### 1. 构建Spring Boot应用
- 进入 `endless-ddd-simplified-generator-core` 目录
- 运行 `mvn clean package -DskipTests`
- 复制生成的JAR文件到 `build/output/source/lib/`

### 2. 复制配置文件
- 复制 `application.properties/yml/yaml` 到 `build/output/source/config/`

### 3. 复制静态文件
- 复制 `public/` 目录下的所有文件到 `build/output/source/public/`

### 4. 构建Tauri应用
- 运行 `cargo tauri build --release`
- 复制生成的可执行文件到 `build/output/source/`

### 5. 创建启动脚本
- 生成 `start.bat` (Windows) 和 `start.sh` (Linux/macOS)
- 脚本会启动Spring Boot和Tauri应用

### 6. 打包安装文件
- 创建带时间戳的安装包目录
- 复制所有文件到安装包
- 生成安装说明文档

## 📦 构建输出

构建完成后，`build/output/` 目录将包含：

```
build/output/
├── source/                                  # 可运行的应用包
│   ├── installer.html                      # 图形界面安装程序
│   ├── install.bat                         # Windows安装启动脚本
│   ├── install.sh                          # Linux/macOS安装启动脚本
│   ├── lib/                                # JAR文件
│   │   └── endless-ddd-simplified-generator-core-*.jar
│   ├── config/                             # 配置文件
│   │   └── application.properties
│   ├── public/                             # 静态文件
│   │   ├── css/
│   │   ├── js/
│   │   └── index.html
│   ├── start.bat                           # Windows启动脚本
│   ├── start.sh                            # Linux/macOS启动脚本
│   └── endless-ddd-simplified-generator.exe # 可执行文件
└── endless-ddd-generator-YYYYMMDD-HHMMSS/  # 完整安装包
    ├── source/                             # 应用文件
    └── README.md                           # 安装说明
```

## 🎯 主要特性

### Spring Boot Web应用
- 🚀 快速项目生成
- 📝 可视化配置界面
- 🔧 自定义模板支持
- 📦 多种项目结构

### Tauri桌面应用
- 🖥️ 原生桌面体验
- 📁 真实文件系统访问
- 🔒 安全权限控制
- ⚡ 高性能渲染

### 构建系统
- 🔨 一键构建
- 📦 跨平台支持
- 🎯 自动化打包
- 📋 环境检查（JDK 21）

## 🔍 环境检查

构建系统会自动检查以下环境：

1. **Rust环境**: 检查rustc和cargo是否可用
2. **Java环境**: 检查java命令和JDK 21版本
3. **Maven环境**: 检查mvn命令是否可用

如果环境不满足要求，会提示安装相应的工具。

## 🛠️ 开发指南

### 修改Spring Boot应用
- 编辑 `../endless-ddd-simplified-generator-core/` 下的Java代码
- 运行 `mvn compile` 编译
- 运行 `mvn spring-boot:run` 启动开发服务器

### 修改Tauri应用
- 编辑 `src-tauri/src/main.rs` 修改Rust代码
- 编辑 `public/` 下的前端代码
- 运行 `cargo tauri dev` 启动开发模式

### 修改运行时逻辑
- 编辑 `src/main.rs` 修改运行时逻辑
- 运行 `cargo build` 重新编译
- 重新构建应用包

## 🐛 故障排除

### 常见问题

#### 1. Java版本问题
```bash
# 检查Java版本
java -version

# 安装JDK 21
# 访问: https://adoptium.net/
```

#### 2. Rust环境问题
```bash
# 检查Rust版本
rustc --version

# 更新Rust
rustup update
```

#### 3. Tauri构建失败
```bash
# 安装Tauri CLI
cargo install tauri-cli

# 清理缓存
cargo clean
```

#### 4. Maven构建失败
```bash
# 清理Maven缓存
mvn clean

# 重新编译
mvn compile
```

### 平台特定问题

#### Windows
- 安装Visual Studio Build Tools
- 安装WebView2运行时
- 确保PowerShell执行策略允许脚本运行

#### macOS
- 安装Xcode命令行工具: `xcode-select --install`
- 确保有开发者证书

#### Linux
```bash
# Ubuntu/Debian依赖
sudo apt update
sudo apt install libwebkit2gtk-4.0-dev \
    build-essential \
    curl \
    wget \
    libssl-dev \
    libgtk-3-dev \
    libayatana-appindicator3-dev \
    librsvg2-dev
```

## 📋 验证构建

### 1. 验证应用包
```bash
cd build/output/source
# Windows
start.bat

# Linux/macOS
chmod +x start.sh
./start.sh
```

### 2. 验证安装包
```bash
cd build/output/endless-ddd-generator-YYYYMMDD-HHMMSS
# 解压并运行
```

## 📄 许可证

MIT License

## 🤝 贡献

欢迎提交Issue和Pull Request！ 