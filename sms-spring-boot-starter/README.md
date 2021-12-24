# 短信发送

```yaml
# 阿里云短信
sms:
  driver: aliyun
  aliyun:
    access-key: xxx
    secret-key: xxx
    sign-name: 品牌名称
```

```yaml
# 腾讯云短信
sms:
  driver: tencent
  tencent:
    access-key: xxx
    secret-key: xxx
    sdk-app-id: xxx
    default-template-code: 模版ID
    sign-name: 品牌名称
```
