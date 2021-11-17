package org.shield.sms.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.shield.sms.service.SmsService;
import org.shield.sms.service.impl.AliyunSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    @Autowired
    private SmsProperties smsProperties;

    @ConditionalOnProperty(name = "sms.driver", havingValue = "aliyun", matchIfMissing = true)
    @Bean
    public SmsService defaultSmsService() throws Exception {
        AliyunSmsProperties props = smsProperties.getAliyun();
        Config config = new Config()
        .setAccessKeyId(props.getAccessKey())
        .setAccessKeySecret(props.getSecretKey())
        .setEndpoint(props.getEndpoint());
        return new AliyunSmsServiceImpl(new Client(config), props);
    }
}