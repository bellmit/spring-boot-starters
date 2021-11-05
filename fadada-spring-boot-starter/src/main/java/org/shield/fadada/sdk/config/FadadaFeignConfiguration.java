package org.shield.fadada.sdk.config;

import org.shield.fadada.sdk.autoconfigure.FadadaProperties;
import org.shield.fadada.sdk.decoder.ResultDecoder;
import org.shield.fadada.sdk.interceptor.SignInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Configuration
public class FadadaFeignConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SignInterceptor signInterceptor(FadadaProperties fadadaProperties) {
        return new SignInterceptor(fadadaProperties);
    }

    @Bean
    public Decoder feignDecoder() {
        return new ResultDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return new HttpMessageConverters(new RestTemplate().getMessageConverters());
            }
        }));
    }
}
