package org.shield.sms.autoconfigure;

/**
 * 阿里云短信配置
 *
 * @author zacksleo@gmail.com
 */
public class AliyunSmsProperties {

    /**
     * AccessKey
     */
    private String accessKey;

    /**
     * SecretKey
     */
    private String secretKey;

    /**
     * 短信服务的服务接入地址
     */
    private String endpoint = "dysmsapi.aliyuncs.com";

    /**
     * version
     */
    private String version = "2017-05-25";

    /**
     * regionId
     */
    private String regionId = "cn-hangzhou";

    /**
     * defaultTemplateCode
     */
    private String defaultTemplateCode = "SMS_170315212";

    /**
     * 短信签名
     */
    private String signName;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getDefaultTemplateCode() {
        return defaultTemplateCode;
    }
}
