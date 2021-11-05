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

配置登录

首先生成密钥

rand 至少为32 位

```bash
openssl rand 256 | base64
```

```yaml
jwt:
  # This token must be encoded using Base64 with mininum 88 Bits (you can type `openssl rand 256 | base64` on your command line)
  base64-secret: {密钥}
  # token is valid 24 hours
  token-validity-in-seconds: 86400

```
