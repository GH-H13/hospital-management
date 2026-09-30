# 医院住院管理系统

基于 **Servlet + JSP + MySQL** 的 JavaWeb 课程设计项目，采用 **MVC 架构**实现，包含管理员与医生两种角色，覆盖住院管理的核心业务流程。

## 功能模块

| 模块      | 说明                 | 可用角色   |
| ------- | ------------------ | ------ |
| 登录 / 退出 | 管理员、医生分角色登录，会话过滤保护 | 管理员、医生 |
| 病人管理    | 入院登记、病人信息维护、在院病人查询 | 管理员、医生 |
| 床位管理    | 床位信息维护、分配与使用状态跟踪   | 管理员    |
| 医生管理    | 医生信息维护、个人资料与密码修改   | 管理员、医生 |
| 收费项目管理  | 标准化收费项目的增删改查       | 管理员    |
| 收费记录管理  | 医生从预设收费项目中选择开具收费记录 | 医生     |
| 统计报表    | 床位利用率、收费情况等统计      | 管理员    |

## 技术栈

- **后端**：Servlet 4.0、JSP、JSTL 1.2.1、Filter（登录拦截）
- **数据库**：MySQL 8.0，JDBC + HikariCP 连接池
- **前端**：JSP 页面 + 公共样式 `style.css`
- **构建**：Maven（WAR 包），Java 8
- **运行环境**：Tomcat 9

## 项目结构

```
hospital/
├── src/main/java/com/hospital/
│   ├── entity/    # 实体类（Admin、Doctor、Patient、Bed、Charge、ChargeItem 等）
│   ├── dao/       # 数据访问层（接口 + impl 实现）
│   ├── service/   # 业务逻辑层
│   ├── web/       # Servlet 控制层（登录、病人、床位、医生、收费、统计等）
│   ├── filter/    # SessionFilter 登录拦截
│   └── util/      # DBUtil 数据库工具
├── src/main/resources/
│   └── db.properties   # 数据库连接配置
├── web/
│   ├── index.jsp
│   ├── css/style.css
│   └── WEB-INF/
│       ├── web.xml
│       └── jsp/        # 各模块 JSP 页面（admin、bed、charge、doctor、patient 等）
├── lib/                # MySQL 驱动（本地依赖）
├── hospital_db.sql     # 数据库初始化脚本
└── pom.xml
```

## 快速开始

### 1. 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8.0
- Tomcat 9

### 2. 初始化数据库

在 MySQL 中执行根目录下的 `hospital_db.sql` 脚本，创建数据库及 6 张数据表（`tb_admin`、`tb_doctor`、`tb_patient`、`tb_bed`、`tb_charge_item`、`tb_charge`）。

### 3. 修改数据库配置

编辑 `src/main/resources/db.properties`，填入本机的 MySQL 地址、用户名和密码。

### 4. 构建与部署

```bash
mvn clean package
```

将生成的 `target/hospital-management-1.0.0.war` 部署到 Tomcat 9（复制到 `webapps/` 目录），启动后访问：

```
http://localhost:8080/hospital-management/
```

## 数据库表

| 表名               | 说明             |
| ---------------- | -------------- |
| `tb_admin`       | 管理员账户          |
| `tb_doctor`      | 医生信息           |
| `tb_patient`     | 病人（住院）信息       |
| `tb_bed`         | 床位信息           |
| `tb_charge_item` | 标准收费项目（由管理员维护） |
| `tb_charge`      | 收费记录（医生按项目开具）  |

## 设计说明

- **MVC 分层**：JSP 负责视图，Servlet 负责控制转发，Service/DAO 负责业务与数据访问，层间职责清晰。
- **标准化收费机制**：收费项目由管理员统一维护，医生只能从预设项目中选择使用，保证收费数据规范一致。
- **会话安全**：通过 `SessionFilter` 对未登录请求统一拦截，防止越权访问。
