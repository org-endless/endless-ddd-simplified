# Endless DDD Simplified Generator

一个基于Spring Boot和Tauri的DDD项目生成器，支持Web界面和桌面应用。

## 项目结构

```
endless-ddd-simplified-generator/
├── build/                    # 构建脚本和输出
│   ├── bin/                  # 构建脚本
│   │   ├── build.bat        # Windows构建脚本
│   │   ├── build.sh         # Linux/macOS构建脚本
│   │   ├── dev.bat          # Windows开发脚本
│   │   └── dev.sh           # Linux/macOS开发脚本
│   └── output/              # 构建输出目录
├── endless-ddd-simplified-generator-core/    # Spring Boot核心模块
├── endless-ddd-simplified-generator-components/  # 组件模块
├── endless-ddd-simplified-generator-common/      # 公共模块
├── tauri/                   # Tauri桌面应用
├── src/                     # Rust构建工具源码
├── Cargo.toml              # Rust项目配置
└── README.md               # 项目说明
```

## 环境要求

### 必需环境
- **Rust**: 1.70+ (https://rustup.rs/)
- **Java**: 11+ (https://adoptium.net/)
- **Maven**: 3.6+ (https://maven.apache.org/)

### 可选环境
- **Node.js**: 16+ (用于Tauri开发)
- **Git**: 版本控制

## 快速开始

### 1. 克隆项目
```bash
git clone <repository-url>
cd endless-ddd-simplified-generator
```

### 2. 构建完整应用

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

### 3. 开发模式

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

## 构建输出

构建完成后，输出目录结构如下：

```
build/output/
├── endless-ddd-generator-YYYYMMDD-HHMMSS/    # 完整分发包
│   ├── spring-boot/                          # Spring Boot应用
│   │   ├── endless-ddd-simplified-generator-core-*.jar
│   │   ├── start.bat                         # Windows启动脚本
│   │   └── start.sh                          # Linux/macOS启动脚本
│   ├── tauri/                                # Tauri桌面应用
│   │   ├── windows/                          # Windows可执行文件
│   │   ├── macos/                            # macOS可执行文件
│   │   └── linux/                            # Linux可执行文件
│   └── README.md                             # 使用说明
├── tauri/                                    # Tauri构建结果
└── *.jar                                     # Spring Boot JAR文件
```

## 功能特性

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
- 📋 环境检查

## 开发指南

### 项目结构说明

#### Spring Boot模块
- `endless-ddd-simplified-generator-core`: 核心Web应用
- `endless-ddd-simplified-generator-components`: 业务组件
- `endless-ddd-simplified-generator-common`: 公共工具

#### Tauri模块
- `tauri/`: 桌面应用配置
- `tauri/src-tauri/`: Rust后端代码
- `tauri/public/`: 前端静态资源

#### 构建工具
- `src/main.rs`: Rust构建工具主程序
- `build/bin/`: 构建脚本
- `build/output/`: 构建输出

### 自定义构建

#### 仅构建Spring Boot
```bash
cargo run -- build-spring-boot --output build/output
```

#### 仅构建Tauri
```bash
cargo run -- build-tauri --output build/output
```

#### 自定义端口开发
```bash
cargo run -- dev --port 9090
```

## 故障排除

### 常见问题

#### 1. Rust环境问题
```bash
# 检查Rust版本
rustc --version

# 更新Rust
rustup update
```

#### 2. Java环境问题
```bash
# 检查Java版本
java -version

# 设置JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-11

# 设置JAVA_HOME (Linux/macOS)
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
```

#### 3. Maven环境问题
```bash
# 检查Maven版本
mvn -version

# 清理Maven缓存
mvn clean
```

#### 4. Tauri构建问题
```bash
# 安装Tauri CLI
cargo install tauri-cli

# 清理Tauri缓存
cargo clean
```

### 平台特定问题

#### Windows
- 确保安装了Visual Studio Build Tools
- 安装WebView2运行时

#### macOS
- 安装Xcode命令行工具
- 确保有开发者证书

#### Linux
- 安装必要的系统依赖
- 配置显示环境

## 贡献指南

1. Fork项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建Pull Request

## 许可证

MIT License

## 联系方式

- 项目主页: [GitHub Repository]
- 问题反馈: [GitHub Issues]
- 讨论区: [GitHub Discussions] 