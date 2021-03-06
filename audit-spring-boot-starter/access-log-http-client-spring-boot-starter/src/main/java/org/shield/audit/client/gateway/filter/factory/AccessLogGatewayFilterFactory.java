package org.shield.audit.client.gateway.filter.factory;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.shield.audit.client.gateway.feign.AccessLogHttpClient;
import org.shield.audit.client.gateway.model.AccessLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 记录用户访问日志
 *
 * @author zacksleo@gmail.com
 */
@Component
public class AccessLogGatewayFilterFactory extends AbstractGatewayFilterFactory<AccessLogGatewayFilterFactory.Config> {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    private static final String AUTH_USER_ID = "auth-userId";

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

            if (config.getExcludes().stream().anyMatch(url::matches)) {
                return chain.filter(exchange);
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> submitAccessLog(exchange)));
        }, 100);
    }

    @Async
    private void submitAccessLog(ServerWebExchange exchange) {
        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
        ServerHttpRequest request = exchange.getRequest();
        String uri = (uris.isEmpty()) ? null : uris.iterator().next().getPath();
        String userId = request.getHeaders().getFirst(AUTH_USER_ID);
        String userName = request.getHeaders().getFirst("auth-userName");
        String tenant = request.getHeaders().getFirst("auth-userName");
        String method = request.getMethodValue();

        UserAgent ua = UserAgentUtil.parse(request.getHeaders().getFirst("User-Agent"));

        AccessLogForm form = new AccessLogForm();
        form.setTenant(tenant == null ? "" : tenant);
        form.setIp(getClientIpByHeader(request));
        form.setBrowser(ua.getBrowser().toString() + " " + ua.getVersion());
        form.setOs(ua.getOs().toString());
        form.setRequestMethod(method);
        if (HttpMethod.GET.equals(request.getMethod())) {
            form.setRequestParams(request.getQueryParams().toString());
        } else {
            form.setRequestParams(getRequestBody(request));
        }
        form.setOperatorId(userId);
        form.setOperatorName(userName);
        form.setRecordTime(new Date());
        form.setUrl(uri != null ? uri : request.getURI().getPath());

        AccessLogHttpClient accessLogHttpClient = context.getBean(AccessLogHttpClient.class);

        asyncTaskExecutor.execute(() -> {
            try {
                accessLogHttpClient.createAccesLogs(form);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String getClientIpByHeader(ServerHttpRequest request) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"};
        String ip;
        for (String header : headers) {
            ip = request.getHeaders().getFirst(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        if (request.getRemoteAddress() == null) {
            return "";
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
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });

        return sb.toString();
    }

    public static class Config {

        private List<String> excludes = new ArrayList<>();

        public Config() {}

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
