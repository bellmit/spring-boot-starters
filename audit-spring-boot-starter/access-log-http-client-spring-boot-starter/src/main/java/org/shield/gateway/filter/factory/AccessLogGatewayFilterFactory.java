package org.shield.gateway.filter.factory;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.JsonNode;

import org.shield.gateway.feign.AccessLogHttpClient;
import org.shield.gateway.model.AccessLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 记录用户访问日志
 *
 * @author zacksleo@gmail.com
 */
@Component
@Slf4j
public class AccessLogGatewayFilterFactory extends AbstractGatewayFilterFactory<AccessLogGatewayFilterFactory.Config> {

    @Autowired
    private ApplicationContext context;

    private static final String AUTH_USER_ID = "auth-userId";

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public AccessLogGatewayFilterFactory() {
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

            String userId = exchange.getRequest().getHeaders().getFirst("auth-userId");
            String userName = exchange.getRequest().getHeaders().getFirst("auth-userName");
            String tenant = exchange.getRequest().getHeaders().getFirst("auth-userName");
            String method = exchange.getRequest().getMethodValue();

            UserAgent ua = UserAgentUtil.parse(exchange.getRequest().getHeaders().getFirst("User-Agent"));

            AccessLogForm form = new AccessLogForm();
            form.setTenant(tenant == null ? "" : tenant);
            form.setIp(getClientIpByHeader(exchange.getRequest()));
            form.setBrowser(ua.getBrowser().toString() + " " + ua.getVersion());
            form.setOs(ua.getOs().toString());
            form.setRequestMethod(method);
            form.setRequestParams(getRequestBody(exchange.getRequest()));
            form.setOperatorId(userId);
            form.setOperatorName(userName);
            form.setRemark(exchange.getRequest().getQueryParams().toString());
            form.setUrl(exchange.getRequest().getURI().toString());

            AccessLogHttpClient accessLogHttpClient = context.getBean(AccessLogHttpClient.class);
            log.info("记录日志...");
            executorService.submit((Callable<AccessLogForm>) () -> accessLogHttpClient.createAccesLogs(form));
            // future.get();

            return chain.filter(exchange);

        }, 100);
    }

    public static String getClientIpByHeader(ServerHttpRequest request) {
        String[] headers = { "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR" };
        String ip;
        for (String header : headers) {
            ip = request.getHeaders().getFirst(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddress().getAddress().getHostAddress();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }

    private String getRequestBody(ServerHttpRequest request) {

        Flux<DataBuffer> body = request.getBody();
        StringBuilder sb = new StringBuilder();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            // DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });

        return sb.toString();
    }

    public static class Config {

        private List<String> excludes = new ArrayList<>();

        public Config() {
        };

        public Config(String excludes) {
            super();
            this.excludes = Arrays.asList(excludes.split(","));
        }

        public Config(List<String> excludes) {
            super();
            this.excludes = excludes;
        }

        public List<String> getExcludes() {
            return excludes;
        }

        public void setExcludes(List<String> excludes) {
            this.excludes = excludes;
        }
    }
}
