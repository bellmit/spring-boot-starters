package org.shield.gateway.filter.factory;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shield.gateway.config.Config;
import org.shield.gateway.config.SwaggerDocsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * 列出所有可用的服务
 *
 * @author zacksleo@gmail.com
 */
@Component
public class ListServiceGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    @Autowired
    private SwaggerDocsProperties props;

    @Autowired
    private ObjectMapper objectMapper;

    public ListServiceGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {

            List<SwaggerDocsProperties.Service> services = props.getServices().stream()
                    .filter(e -> config.getExcludes().stream().noneMatch(f -> e.getUrl().matches(f)))
                    .collect(Collectors.toList());

            ServerHttpResponse originalResponse = exchange.getResponse();
            originalResponse.setStatusCode(HttpStatus.OK);
            originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            DataBuffer buffer = originalResponse.bufferFactory().wrap(object2string(services).getBytes());
            return originalResponse.writeWith(Flux.just(buffer));

        }, -800);
    }

    private String object2string(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }
}
