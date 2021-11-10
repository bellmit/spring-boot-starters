package org.shield.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Component
@ConfigurationProperties(prefix = "swagger.docs")
public class SwaggerDocsProperties {

    private List<Service> services;

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public static class Service {
        /**
         * 服务名称
         */

        private String name;

        /**
         * API 地址
         */
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
