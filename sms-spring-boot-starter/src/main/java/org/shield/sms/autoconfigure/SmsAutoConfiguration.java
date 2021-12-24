package org.shield.sms.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.shield.sms.service.SmsService;
import org.shield.sms.service.impl.AliyunSmsServiceImpl;
import org.shield.sms.service.impl.TencentSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
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
        Config config = new Config().setAccessKeyId(props.getAccessKey()).setAccessKeySecret(props.getSecretKey())
                .setEndpoint(props.getEndpoint());
        return new AliyunSmsServiceImpl(new Client(config), props);
    }

    @ConditionalOnProperty(name = "sms.driver", havingValue = "tencent")
    @Bean
    public SmsService tencentSmsService() {
        TencentSmsProperties props = smsProperties.getTencent();
        Credential cred = new Credential(props.getAccessKey(), props.getSecretKey());
        // 实例化一个http选项，可选，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(30);
        httpProfile.setEndpoint(props.getEndpoint());

        /*
         * 非必要步骤: 实例化一个客户端配置对象，可以指定超时时间等配置
         */
        ClientProfile clientProfile = new ClientProfile();
        /*
         * SDK默认用TC3-HMAC-SHA256进行签名 非必要请不要修改这个字段
         */
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        /*
         * 实例化要请求产品(以sms为例)的client对象 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量
         */
        return new TencentSmsServiceImpl(new SmsClient(cred, props.getRegionId(), clientProfile), props);
    }
}
