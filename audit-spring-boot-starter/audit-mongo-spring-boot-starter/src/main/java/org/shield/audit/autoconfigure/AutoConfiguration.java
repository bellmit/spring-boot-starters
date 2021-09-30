package org.shield.audit.autoconfigure;

import org.shield.audit.annotation.EnableAuditLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@ConditionalOnBean(annotation = EnableAuditLog.class)
@ComponentScan({ "org.shield.audit" })
@EnableMongoRepositories(basePackages = { "org.shield.audit.repository" })
public class AutoConfiguration {
}
