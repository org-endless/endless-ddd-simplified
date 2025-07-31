# Endless DDD Simplified

> 🌱 一个简洁清晰的领域驱动设计（DDD）构建框架，致力于打造高可维护、高内聚、低耦合的业务系统。

---

## 🔧 特性 Features

- ✅ **基于 DDD（领域驱动设计）原则** 构建项目骨架，关注业务模型本身
- ✅ **CQRS 架构**：命令 / 查询职责分离，提高系统的扩展性和读写性能
- ✅ **去事件化设计**：不依赖事件驱动模型，控制复杂度，适用于大部分业务系统
- ✅ **Spring Boot 3.3.5**：基于最新版本构建，生态完备
- ✅ **Java 21**：使用最新 LTS 版本，拥抱现代语法与性能提升

---

## 💡 设计理念

本项目旨在提供一种**轻量但严谨的 DDD 实践方式**，帮助团队在保持业务模型清晰性的前提下，加快开发效率并确保系统的可演进性。

通过合理分层和职责划分，避免过度工程，同时为未来的演化预留空间。

---

## 🚀 技术栈

| 技术          | 版本                |
|-------------|-------------------|
| Java        | 21                |
| Spring Boot | 3.3.5             |
| 架构模式        | CQRS（命令查询职责分离）    |
| 模型设计        | DDD 分层架构（不使用事件驱动） |

---

## ☕ Buy Me a Cup of Coffee

如果你觉得本项目对你有帮助，请我喝一杯咖啡 ☕ 😊

<img src="![b21d529b9bd43b6eb6641776489d0e91](https://github.com/user-attachments/assets/01daca5d-ed7c-4dcc-9fe9-9c2fcec8e18b)" width="256" alt="Buy Me a Coffee QR" />


---

## 📁 项目结构（示例）

```
endless-ddd-simplified
├── application     // 应用服务层（协调命令/查询，封装用例逻辑）
├── domain          // 领域层（聚合根、实体、值对象、仓储接口等）
├── infrastructure  // 基础设施层（数据库实现、消息、外部系统适配器、被动适配器）
├── facade          // 界面层（主动适配器）
└── sidecar         // 服务网格边车层（Controller, 服务网格接入）
```

每一层职责清晰，便于协作开发、维护和模块重用。

---

## 🚀 快速开始（Quick Start）

以下是本地开发环境下的典型启动步骤：

### 1. 克隆项目

```bash
git clone https://github.com/org-endless/endless-ddd-simplified.git
cd endless-ddd-simplified
```

### 2. 构建项目

使用自带的 Maven Wrapper（无需预装 Maven）：

```bash
./mvnw clean install
```

或使用本地 Maven：

```bash
mvn clean install
```

### 3. 运行项目

```bash
./mvnw spring-boot:run
```

默认启动端口为 `60001`，访问地址：

```
http://localhost:60001/
```

---

## 📄 License

本项目基于 **Apache License 2.0** 许可协议发布。

- 📄 查看完整协议内容请访问：[https://www.apache.org/licenses/LICENSE-2.0](https://www.apache.org/licenses/LICENSE-2.0)
- 📁 或查阅仓库中的 [LICENSE](./LICENSE) 文件。

---

## 🙏 致谢

本项目参考或集成了以下优秀开源组件：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MapStruct](https://mapstruct.org/) - Java Bean 映射器
- [Lombok](https://projectlombok.org/) - 简化 Java 代码
- [Hibernate Validator](https://hibernate.org/validator/) - 校验框架
- 领域驱动设计相关思想与资料来自 Eric Evans 和 DDD 社区
