package org.shield.audit.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Component("mongoExpireConfig")
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Data
public class MongoExpireConfig {
    /**
     * 数据过期时间
     */
    private String expireAfter = "";
}
