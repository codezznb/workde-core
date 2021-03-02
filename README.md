`workde-core` 是一个基于`SpringBoot`、`Ebean`等技术构建的Java服务端运行环境，其目标是让Java服务端开发「开箱即用」。

## 1. 概览
- 一个基于`SpringBoot`、`Ebean`、`dubbo`等技术构建的Java模块化运行环境
- 基于Jdk8+，用较现代的方式书写Java
- 基于SpringBoot，最流行的技术
- 选用Ebean作为持久化层
- 使用ServiceLoader支持模块化加载运行
- 每一个插件为一个jar包
    - 每个插件均提供rest服务
    - 每个插件有自己的数据库，多个插件可以共用一个数据库连接
    - 同时管理自己的数据库迁移脚本
- 项目启动时，加载classpath下的所有plugin，执行数据库迁移脚本，启动SpringBoot
- 部署时，通过classpath下plugin的多少控制服务的大小
## 2. 目标
- 不同的业务(插件)，可以跑在同一个容器(container)中
- 通用的插件，可以很方便的重用，而不需要做进平台
- 在规模较小，业务量不大的情况下，所有业务只用一个应用即可
- 当业务量大了，想使用微服务部署，只需要修改打包部署方式 
- 提供标准的运行环境，从0开始搭一套环境变的更简单
- 几乎不需要手工处理线上数据库升级

## 3. 主要功能

- [x] 插件加载运行平台
- [x] 支持插件生命周期管理
- [x] 根据配置加载插件
- [x] 支持插件生命周期管理
- [x] 根据配置加载插件
- [x] 多datasource管理
- [x] ebean启动时动态增强
- [x] 启动时ebean的`model`动态增强
- [ ] 生成DDL模型和SQL
- [x] 启动时数据库自动迁移
- [x] 提供`entity`基类，定义常用的操作
- [ ] 集成dubbo
- [x] 集成swagger-ui(knife4j)
- [x] undertow替代tomcat

## 4. 第一个插件
### 4.1 创建插件项目

创建一个`maven`项目，并添加依赖：

```xml
<artifactId>grape1</artifactId>
<dependencies>
    <dependency>
        <groupId>org.grape</groupId>
        <artifactId>grape-container</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```