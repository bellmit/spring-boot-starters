package org.shield.audit.autoconfigure;

import org.shield.audit.annotation.EnableAuditLog;
import org.shield.audit.mapper.AuditLogMapper;
import org.shield.audit.repository.AuditLogRepository;
import org.shield.audit.service.AuditLogService;
import org.shield.audit.service.impl.MongoAuditLogServiceImpl;
import org.shield.audit.service.impl.MysqlAuditLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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
