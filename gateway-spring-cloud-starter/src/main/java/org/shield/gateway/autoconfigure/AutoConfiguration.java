package org.shield.gateway.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@ComponentScan({"org.shield.gateway"})
@EnableAsync
public class AutoConfiguration {
}
