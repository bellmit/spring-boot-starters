package org.shield.audit.client.autoconfigure;

import com.mzt.logapi.starter.annotation.EnableLogRecord;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@ComponentScan({ "org.shield.audit.client" })
@ConditionalOnBean(annotation = EnableLogRecord.class)
@EnableFeignClients("org.shield.audit.client.feign")
public class AutoConfiguration {
}
