package org.shield.gateway.filter.factory;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.shield.gateway.config.AccessTokenFilterProperties;
import org.shield.gateway.config.Config;
import org.shield.gateway.exception.UnauthorizedException;
import org.shield.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URLEncoder;
import java.util.function.Consumer;
import org.springframework.http.HttpCookie;

/**
 * AccessToken 认证
 *
 * <p>
 * 全局进行 Token 认证，Token 可以通过 Header 中的 Authorization 传递，也可以通过 url 中的?accessToken=传递
 * </p>
 * <p>
 * 如果需要排除相关的 url，使用 args.excludes 配置数组, 如果在全局设置了 access-token.excludes，优先匹配并跳过
 * </p>
 *
 * <pre>
 * - name: AccessToken
 *   args:
 *      excludes:
 *        - /iot/admin/tokens
 * </pre>
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Component
public class AccessTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AccessTokenFilterProperties tokenFilterProperties;

    public AccessTokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();
            if (tokenFilterProperties.getExcludes().stream().anyMatch(e -> url.matches(e))) {
                return chain.filter(exchange);
            }

            if (config.getExcludes().stream().anyMatch(e -> url.matches(e))) {
                return chain.filter(exchange);
            }

            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (!StringUtils.hasLength(token)) {
                token = exchange.getRequest().getQueryParams().getFirst("accessToken");
            }
            if (!StringUtils.hasLength(token)) {
                HttpCookie cookie = exchange.getRequest().getCookies().getFirst("Authorization");
                if (cookie != null) {
                    token = cookie.getValue();
                }
            }
            if (!StringUtils.hasLength(token)) {
                return tokenMissingResponse(exchange);
            }

            try {
                Claims tokenInfo = jwtTokenUtils.getTokenBody(token);
                // 相关授权的用户信息写入 header 中
                Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                    Object name = tokenInfo.get("name");
                    Object username = tokenInfo.get("username");
                    httpHeader.set("auth-catalog", StrUtil.isEmpty(tokenInfo.getIssuer()) ? "" : tokenInfo.getIssuer());
                    httpHeader.set("auth-name", name != null ? encode(name.toString()) : "");
                    httpHeader.set("auth-userId", tokenInfo.getSubject());
                    httpHeader.set("auth-uid", tokenInfo.get("uid") == null ? "0" : tokenInfo.get("uid").toString());
                    httpHeader.set("auth-username", username != null ? username.toString() : "");
                    httpHeader.set("expiration", DateUtil.format(tokenInfo.getExpiration(), "yyyy-MM-dd HH:mm:ss"));
                };
                // 将现在的request，添加当前身份
                ServerHttpRequest mutableReq = exchange.getRequest().mutate().headers(httpHeaders).build();
                ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                return chain.filter(mutableExchange);

            } catch (Exception e) {
                System.out.printf("token 认证失败: %s", e.getMessage());
                return tokenMissingResponse(exchange);
            }

        }, -800);
    }

    private Mono<Void> tokenMissingResponse(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = originalResponse.bufferFactory().wrap(new UnauthorizedException("token missing").toBytes());
        return originalResponse.writeWith(Flux.just(buffer));
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }
}
