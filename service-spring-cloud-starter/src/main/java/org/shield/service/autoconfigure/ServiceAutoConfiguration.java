package org.shield.service.autoconfigure;

import org.shield.service.converter.HeaderDecoderConverter;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@ControllerAdvice
public class ServiceAutoConfiguration {

    @Bean
    public HeaderDecoderConverter headerDecoderConverter() {
        return new HeaderDecoderConverter();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
