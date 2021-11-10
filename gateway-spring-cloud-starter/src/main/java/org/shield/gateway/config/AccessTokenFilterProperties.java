package org.shield.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Component
@ConfigurationProperties(prefix = "filters.access-token")
public class AccessTokenFilterProperties {

    List<String> excludes = new ArrayList<>();

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }
}
