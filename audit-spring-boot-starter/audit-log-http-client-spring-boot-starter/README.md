
# audit-log-http-client

## 使用方法

### 开启日志

```java
@EnableLogRecord(tenant = "org.shield.module")
```

### 配置 YAML

```yaml
# 操作日志
audit:
  log:
    hostname: iot-admin
```

### 注解

```java
@LogRecordAnnotation(bizNo = "{{#orderId}}", category = "合同", detail = "{{#_ret}}", success = "创建", prefix = "")
```

注解详细使用方法见 [通用操作日志组件](https://github.com/mouzt/mzt-biz-log/blob/master/readme.md#%E6%97%A5%E5%BF%97%E5%9F%8B%E7%82%B9)