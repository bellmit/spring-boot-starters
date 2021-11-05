package org.shield.fadada.sdk.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import org.shield.fadada.sdk.client.HasSha1Md5ParamSigner;
import org.shield.fadada.sdk.processor.AffixProcessor;
import org.shield.fadada.sdk.processor.HasAffixParamProcessor;
import org.shield.fadada.sdk.processor.HasSha1ParamProcessor;
import org.shield.fadada.sdk.processor.Md5Processor;
import org.shield.fadada.sdk.processor.Sha1Processor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Contract;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Configuration
@EnableConfigurationProperties(FadadaProperties.class)
@EnableFeignClients(basePackages = "org.shield.fadada.sdk.client")
public class FadadaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Contract springMvcContract() {
        List<AnnotatedParameterProcessor> processors = new ArrayList<>();
        processors.add(new Sha1Processor());
        processors.add(new Md5Processor());
        processors.add(new AffixProcessor());
        processors.add(new HasSha1ParamProcessor());
        processors.add(new HasAffixParamProcessor());
        return new SpringMvcContract(processors);
    }

    @Bean
    public HasSha1Md5ParamSigner hasSha1Md5ParamSigner(FadadaProperties fadadaProperties) {
        return new HasSha1Md5ParamSigner(fadadaProperties);
    }
}
