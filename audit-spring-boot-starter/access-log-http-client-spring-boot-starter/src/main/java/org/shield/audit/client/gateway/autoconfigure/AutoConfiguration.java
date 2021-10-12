package org.shield.audit.client.gateway.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@ComponentScan({ "org.shield.audit.client.gateway" })
@EnableFeignClients({ "org.shield.audit.client.gateway.feign" })
@EnableAsync
public class AutoConfiguration {

    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;

    @ConditionalOnMissingBean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setThreadNamePrefix("async-task-thread-pool-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
