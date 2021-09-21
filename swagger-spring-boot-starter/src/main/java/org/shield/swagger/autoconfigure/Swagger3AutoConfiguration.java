package org.shield.swagger.autoconfigure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.service.Server;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zacksleo@gmail.com
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class Swagger3AutoConfiguration {

    @Autowired
    private SwaggerProperties props;

    @Bean
    public Docket createRestApi() {
        List<Server> servers = servers();
        Server first = servers.isEmpty() ? null : servers.get(0);
        Server[] remaining = servers.isEmpty() ? new Server[0]
                : servers.subList(1, servers.size()).toArray(new Server[0]);
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).servers(first, remaining)
                .directModelSubstitute(Byte.class, Integer.class).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
                .build().globalRequestParameters(globalRequestParameters());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(props.title).description(props.description).version(props.version).build();
    }

    private List<RequestParameter> globalRequestParameters() {
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder().in(ParameterType.HEADER)
                .name("Authorization").required(false)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));
        return Collections.singletonList(parameterBuilder.build());
    }

    private List<Server> servers() {
        return props.getServers().stream().map(e -> new Server(e.getName(), e.getUrl(), e.getDescription(),
                Collections.emptyList(), new ArrayList<>())).collect(Collectors.toList());
    }
}
