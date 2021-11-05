package org.shield.fadada.sdk.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@ConfigurationProperties(prefix = "fadada.sdk")
public class FadadaProperties {

    /**
     * 应用编号
     */
    private String appId;

    /**
     * 应用私钥
     */
    private String appSecret;

    /**
     * 版本号
     */
    private String version = "2.0";

    /**
     * 接入入口地址
     */
    private String url;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
