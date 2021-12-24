package org.shield.sms.autoconfigure;

/**
 * 腾讯云短信配置
 *
 * @author zacksleo@gmail.com
 */
public class TencentSmsProperties {

    /**
     * accessKey/secretId
     */
    private String accessKey;

    /**
     * SecretKey
     */
    private String secretKey;

    /**
     * 短信服务的服务接入地址
     */
    private String endpoint = "sms.tencentcloudapi.com";

    /**
     * appId
     */
    private String sdkAppId;

    /**
     * regionId
     */
    private String regionId = "ap-guangzhou";

    /**
     * defaultTemplateCode
     */
    private String defaultTemplateCode;

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

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
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

    public void setDefaultTemplateCode(String defaultTemplateCode) {
        this.defaultTemplateCode = defaultTemplateCode;
    }
}
