package org.shield.swagger.autoconfigure;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zacksleo@gmail.com
 */
@ConfigurationProperties(prefix = "springfox.documentation")
public class SwaggerProperties {
    /**
     * 标题
     */
    String title = "";
    /**
     * 描述
     */
    String description = "接口文档";
    /**
     * 版本号
     */
    String version = "";

    List<Server> servers;

    public static class Server {
        /**
         * 地址
         */
        String url;
        /**
         * 名称
         */
        String name;

        /**
         * 描述
         */
        String description;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}
