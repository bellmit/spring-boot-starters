package org.shield.sms.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云短信配置
 *
 * @author zacksleo@gmail.com
 */
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    /**
     * driver
     */
    private String driver;

    private AliyunSmsProperties aliyun;

    private TencentSmsProperties tencent;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public AliyunSmsProperties getAliyun() {
        return aliyun;
    }

    public void setAliyun(AliyunSmsProperties aliyun) {
        this.aliyun = aliyun;
    }

    public TencentSmsProperties getTencent() {
        return tencent;
    }

    public void setTencent(TencentSmsProperties tencent) {
        this.tencent = tencent;
    }
}
