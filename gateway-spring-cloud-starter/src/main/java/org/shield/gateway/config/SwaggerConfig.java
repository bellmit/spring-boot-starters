package org.shield.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Primary
@Configuration
public class SwaggerConfig implements SwaggerResourcesProvider {
    @Resource
    private SwaggerDocsProperties props;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        for (SwaggerDocsProperties.Service service : props.getServices()) {
            resources.add(swaggerResource(service.getName(), service.getUrl() + "/v3/api-docs", "1.0"));
        }
        return resources;
    }

    private SwaggerResource swaggerResource(final String name, final String location, final String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
