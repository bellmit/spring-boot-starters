package org.shield.gateway.filter.factory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.shield.gateway.config.Config;
import org.shield.gateway.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 管理员用户权限验证
 *
 * 如果当前用户为匿名，则跳过认证
 *
 * @author zacksleo@gmail.com
 */
@Component
public class AdminAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    private static final String VALID_CATALOG = "AdminUser";

    private static final String AUTH_USER_ID = "auth-userId";

    private static final String ANY_METHOD = "ANY";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${admin.hostname:admin}")
    private String adminHost;

    public AdminAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            // 如果在 AccessTokenFilter 中跳过了验证，则这里也跳过
            if (!exchange.getRequest().getHeaders().containsKey(AUTH_USER_ID)) {
                return chain.filter(exchange);
            }
            String url = exchange.getRequest().getURI().getPath();

            if (config.getExcludes().stream().anyMatch(e -> url.matches(e))) {
                return chain.filter(exchange);
            }

            boolean validUser = VALID_CATALOG.equals(exchange.getRequest().getHeaders().getFirst("auth-catalog"));

            if (!validUser) {
                return unauthroizedException(exchange);
            }
            String adminId = exchange.getRequest().getHeaders().getFirst(AUTH_USER_ID);
            String method = exchange.getRequest().getMethodValue();

            return getRoutes(adminId).flatMap(s -> {
                List<String> routes = new ArrayList<>(Arrays.asList(s));
                if (routes.stream().anyMatch(e -> {
                    String[] paths = e.split("\\s");
                    if (!ANY_METHOD.equals(paths[0]) && !method.equals(paths[0])) {
                        return false;
                    }
                    return url.matches(paths[1]);
                })) {
                    return chain.filter(exchange);
                }
                return unauthroizedException(exchange);
            });

        }, -800);
    }

    public Mono<String[]> getRoutes(String adminId) {
        String url = String.format("http://%s/admins/%s/routes", adminHost, adminId);
        return webClientBuilder.build().get().uri(url).retrieve().bodyToMono(String[].class)
                .timeout(Duration.ofSeconds(3)).onErrorResume(throwable -> Mono.just(new String[0]));
    }

    private Mono<Void> unauthroizedException(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.FORBIDDEN);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = originalResponse.bufferFactory().wrap(new ForbiddenException("没有权限进行该操作").toBytes());
        return originalResponse.writeWith(Flux.just(buffer));
    }
}
