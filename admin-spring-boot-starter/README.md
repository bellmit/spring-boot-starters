# 系统管理通用模块

## 功能

1. 系统账号管理
2. 基于RBAC的权限角色管理
3. App发布管理
4. 广告管理
5. 配置管理
6. 字典管理

## 使用

```java
@SpringBootApplication
@EnableAdminConsole
public class Application {

```

配置 mybatis

```yaml
mybatis:
  mapper-locations:
    - classpath:mapper/*Mapper.xml
    - classpath*:/mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true

```
