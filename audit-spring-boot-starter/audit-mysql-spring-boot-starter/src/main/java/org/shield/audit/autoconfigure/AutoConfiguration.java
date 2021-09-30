package org.shield.audit.autoconfigure;

import org.shield.audit.annotation.EnableAuditLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@ConditionalOnBean(annotation = EnableAuditLog.class)
@ComponentScan({ "org.shield.audit" })
@MapperScan({ "org.shield.audit.mapper" })
public class AutoConfiguration {
}
