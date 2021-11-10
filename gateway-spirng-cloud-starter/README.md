# 微服务网关

## 全局过滤器

### WipeOutAuthEader

抹除以 auth-\*开头的 header, 防止伪造

默认启用，使用如下方式临时禁用

```yaml
# 抹除 auth-的 header
wipe-out-auth-header:
  enable: false
```

## 局部过滤器

### AccessTokenFilter

全局进行 Token 认证，Token 可以通过 Header 中的 Authorization 传递，也可以通过 url 中的?accessToken=传递

如果需要排除相关的 url，使用 args.excludes 配置数组, 如果在全局设置了 access-token.excludes，优先匹配并跳过

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: iot-admin
          uri: lb://iot-admin
          predicates:
            - Path=/iot/admin/**
          filters:
            - StripPrefix=2
            # 验证 AccessToken
            - name: AccessToken
              args:
                excludes:
                  - /iot/admin/tokens
filters:
  # 全局 AccessToken 跳过验证设置
  access-token:
    # 跳过 Token 验证的路由, 使用 String.matches 中的表达式
    excludes:
      - /.*/swagger-resources.*
      - /.*/v3/api-docs
```

### AdminAuth

管理员权限验证, 使用方式如下，如果需要排除相关的 url，使用 args.excludes 配置数组, 否则 args 可以省略, 此时使用 access-token.excludes

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: iot-admin
          uri: lb://iot-admin
          predicates:
            - Path=/iot/admin/**
          filters:
            - StripPrefix=2
            # AccessToken 验证
            - name: AccessToken
              args:
                excludes:
                  - /iot/admin/captchas
                  - /iot/admin/tokens
            # 验证管理员权限
            - name: AdminAuth
```