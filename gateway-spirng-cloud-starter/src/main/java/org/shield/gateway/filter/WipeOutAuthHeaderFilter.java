package org.shield.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * 抹除以 auth-* 开头的 header, 防止伪造
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Component
public class WipeOutAuthHeaderFilter implements GlobalFilter, Ordered {

    @Value("${filters.wipe-out-auth-header.enable:true}")
    private boolean enable;

    private static final String AUTH_REGEX = "auth-.*";

    @Override
    public int getOrder() {
        return -1000;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!enable) {
            return chain.filter(exchange);
        }
        Consumer<HttpHeaders> httpHeaders = header -> {
            header.keySet().stream().forEach(key -> {
                if (key.matches(AUTH_REGEX)) {
                    header.remove(key);
                }
            });
        };
        ServerHttpRequest mutableReq = exchange.getRequest().mutate().headers(httpHeaders).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return chain.filter(mutableExchange);
    }
}
