package org.shield.fadada.sdk.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.CaseUtils;
import org.shield.fadada.sdk.annatation.AffixParam;
import org.shield.fadada.sdk.annatation.HasSha1Param;
import org.shield.fadada.sdk.annatation.Md5Param;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.shield.fadada.sdk.autoconfigure.FadadaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import feign.Request.HttpMethod;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zacksleo <zacksleo@gmail.com> 获取 header 中的需要签名的字段，并进行签名
 *         <p>
 *         需要使用 SHA1签名的字段，使用 _SHA1_ 前缀
 *         </p>
 *         <p>
 *         需要使用 MD5 签名的字段，使用 _MD5_ 前缀, 如果是多个字段，则使用 _MD5_\d+ 保证先后顺序 sha1 字段的顺序, 按照字段名称的字典顺序排序 md5 的字段顺序，按照参数出现的先后顺序排序
 *         </p>
 */
@Slf4j
public class SignInterceptor implements RequestInterceptor {

    @Autowired
    private FadadaProperties fadadaProperties;

    private Map<String, Object> formParams = new HashMap<>();

    private HttpMethod httpMethod;

    private Collection<String> contentType;

    private static final String CONTENT_TYPE = "Content-Type";

    public SignInterceptor(FadadaProperties fadadaProperties) {
        this.fadadaProperties = fadadaProperties;
    }

    @Override
    public void apply(RequestTemplate template) {

        // 处理 header
        resolveHeaders(template);

        log.debug("headers:{}", template.headers());

        // 处理签名
        resolveSign(template);

        // 处理 body
        resolveBody(template);
    }

    /**
     * 获取请求方式和请求头中的Content-Type
     *
     * @param template
     */
    private void resolveHeaders(RequestTemplate template) {
        this.httpMethod = template.request().httpMethod();
        this.contentType =
                template.request().headers().containsKey(CONTENT_TYPE) ? template.request().headers().get(CONTENT_TYPE)
                        : Collections.emptyList();

        if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            // 如果body中有参数，则解析后放入formParams中
            resolveFormParams(template);
            template.removeHeader(CONTENT_TYPE);
            if (HttpMethod.POST.equals(httpMethod)) {
                template.header(CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
            }
        }
    }

    /**
     * 如果body中有参数，则解析后放入formParams中
     *
     * @param template
     */
    @SuppressWarnings("unchecked")
    private void resolveFormParams(RequestTemplate template) {
        if (template.body() == null) {
            return;
        }
        String json = new String(template.body());
        try {
            formParams = new ObjectMapper().readValue(json, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理签名
     *
     * @param template
     */
    private void resolveSign(RequestTemplate template) {
        TreeMap<String, String> sha1Fields = new TreeMap<>();
        TreeMap<String, String[]> md5Fields = new TreeMap<>();
        TreeMap<String, String> affixFields = new TreeMap<>();

        // 遍历header, 获取需要签名的字段
        Iterator<Map.Entry<String, Collection<String>>> it = template.headers().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Collection<String>> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue().iterator().next();
            // 以__SHA1__开头的字段，放入sha1Fields
            if (key.startsWith(Sha1Param.HEADER_PREFIX)) {
                String name = key.replaceAll(Sha1Param.HEADER_PREFIX + "\\d+_", "");
                sha1Fields.put(key, emptyShouldGetFromBody(template, name, value));
                template.removeHeader(key);
                // 以__MD5__开头的字段，处理后放入md5Fields
            } else if (key.startsWith(Md5Param.HEADER_PREFIX)) {
                String name = key.replaceAll(Md5Param.HEADER_PREFIX + "\\d+_", "");
                md5Fields.put(key, new String[] {name, emptyShouldGetFromBody(template, name, value)});
                template.removeHeader(key);
            } else if (key.startsWith(AffixParam.HEADER_PREFIX)) {
                String name = key.replaceAll(AffixParam.HEADER_PREFIX + "\\d+_", "");
                affixFields.put(key, emptyShouldGetFromBody(template, name, value));
                template.removeHeader(key);
            }
        }
        String sha1Param =
                sha1Fields.entrySet().stream().filter(e->!HasSha1Param.DEFAULT_VALUE.equals(e.getValue())).map(entry -> entry.getValue()).collect(Collectors.joining(""));

        String timestamp = timestamp();
        md5Fields.put(String.format("%s000_%s", Md5Param.HEADER_PREFIX, timestamp),
                new String[] {"timestamp", timestamp});

        String md5Param =
                md5Fields.entrySet().stream().map(entry -> entry.getValue()[1]).collect(Collectors.joining(""));

        String affixParam =
                affixFields.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.joining(""));
        log.debug("sha1Param:{}, md5Param:{}, affixParam:{}", sha1Param, md5Param, affixParam);
        String base64 = base64(sha1(fadadaProperties.getAppId() + md5(md5Param)
                + sha1(fadadaProperties.getAppSecret() + sha1Param) + affixParam));

        // 添加公共参数
        template.query("app_id", fadadaProperties.getAppId());
        template.query("timestamp", timestamp);
        template.query("v", fadadaProperties.getVersion());
        template.query("msg_digest", base64);
    }

    /**
     * 处理body
     *
     * @param template
     */
    private void resolveBody(RequestTemplate template) {
        if (formParams.isEmpty()) {
            return;
        }
        String formStr = formParams.entrySet().stream().filter(e -> e.getValue() != null)
                .map(p -> p.getKey() + "=" + urlEncodeUtf8(p.getValue().toString())).reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
        template.body(formStr);
        log.debug("request body: {}", new String(template.body()));
    }

    private String emptyShouldGetFromBody(RequestTemplate template, String key, String value) {
        String rawKey = key;
        if (!HasSha1Param.DEFAULT_VALUE.equals(value)) {
            return value;
        }
        if (!formParams.containsKey(key)) {
            key = CaseUtils.toCamelCase(key, false, '_');
        }
        if (!formParams.containsKey(key) || formParams.getOrDefault(key, "") == null) {
            return value;
        }
        String newValue = formParams.getOrDefault(key, "").toString();

        // 如果值是从 json 中获取的，则需要把 key 放入 query 参数或者 Form 参数中
        resolveQueryFormParams(template, rawKey, newValue);

        log.debug("get value from formParams: {}=>{}", key, newValue);
        return newValue;
    }

    /**
     * 自动将json中的字段转成Form形式或者Query形式
     *
     * @return
     */
    private void resolveQueryFormParams(RequestTemplate template, String rawKey, String newValue) {
        if (HttpMethod.GET.equals(httpMethod) || contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            log.debug("put query params = {}=>{}", rawKey, newValue);
            template.query(rawKey, newValue);
        } else {
            formParams.put(rawKey, newValue);
            log.debug("put form params = {}=>{}", rawKey, newValue);
        }
    }

    private String timestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }

    private String md5(String str) {
        return DigestUtils.md5Hex(str).toUpperCase();
    }

    private String sha1(String str) {
        return DigestUtils.sha1Hex(str).toUpperCase();
    }

    private String base64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    private String urlEncodeUtf8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
