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
}
