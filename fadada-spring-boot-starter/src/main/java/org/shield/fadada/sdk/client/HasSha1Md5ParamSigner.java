package org.shield.fadada.sdk.client;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.codec.digest.DigestUtils;
import org.shield.fadada.sdk.annatation.Md5Param;
import org.shield.fadada.sdk.annatation.RequestUrl;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.shield.fadada.sdk.autoconfigure.FadadaProperties;
import org.springframework.validation.annotation.Validated;
import feign.form.FormProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * 根据实体中的注解信息，生成签名后的参数
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Validated
@Slf4j
public class HasSha1Md5ParamSigner {

    TreeMap<String, String[]> sha1Fields = new TreeMap<>();
    TreeMap<String, String[]> md5Fields = new TreeMap<>();
    Map<String, String> params = new HashMap<>(10);

    private FadadaProperties fadadaProperties;

    public HasSha1Md5ParamSigner(FadadaProperties fadadaProperties) {
        this.fadadaProperties = fadadaProperties;

    }

    private void resolveSign(Object object) {
        resolveSha1Params(object);
        resolveMd5Params(object);

        String sha1Param =
                sha1Fields.entrySet().stream().map(entry -> entry.getValue()[1]).collect(Collectors.joining(""));

        String timestamp = timestamp();
        md5Fields.put(String.format("000_%s", "timestamp"), new String[] {"timestamp", timestamp});

        String md5Param =
                md5Fields.entrySet().stream().map(entry -> entry.getValue()[1]).collect(Collectors.joining(""));

        log.debug("sha1Param:{}, md5Param:{}, affixParam:{}", sha1Param, md5Param);
        String base64 = base64(
                sha1(fadadaProperties.getAppId() + md5(md5Param) + sha1(fadadaProperties.getAppSecret() + sha1Param)));

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Sha1Param.class) || field.isAnnotationPresent(Md5Param.class)) {
                continue;
            }
            try {
                FormProperty anno = field.getAnnotation(FormProperty.class);
                String fieldName = anno == null || anno.value().isEmpty() ? field.getName() : anno.value();
                if (field.get(object) != null) {
                    params.put(fieldName, field.get(object).toString());
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 添加公共参数
        params.put("app_id", fadadaProperties.getAppId());
        params.put("timestamp", timestamp);
        params.put("v", fadadaProperties.getVersion());
        params.put("msg_digest", base64);
    }

    public String getSignedUrl(@Valid Object object) {
        resolveSign(object);
        String prefix = fadadaProperties.getUrl();
        if (object.getClass().isAnnotationPresent(RequestUrl.class)) {
            RequestUrl requestUrl = object.getClass().getAnnotation(RequestUrl.class);
            prefix += requestUrl.value();
        }
        return prefix + "?" + params.entrySet().stream().map(p -> p.getKey() + "=" + urlEncodeUtf8(p.getValue()))
                .reduce((p1, p2) -> p1 + "&" + p2).orElse("");
    }

    public Map<String, String> getSignedParams(@Valid Object object) {
        resolveSign(object);
        return params;
    }

    private void resolveSha1Params(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Sha1Param.class)) {
                continue;
            }
            try {
                Sha1Param anno = field.getAnnotation(Sha1Param.class);
                String fieldName = anno.value().isEmpty() ? field.getName() : anno.value();
                Integer order = anno.order().isEmpty() ? i : Integer.valueOf(anno.order());
                sha1Fields.put(String.format("%03d_%s", order, fieldName),
                        new String[] {fieldName, field.get(object).toString()});
                params.put(fieldName, field.get(object).toString());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void resolveMd5Params(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Md5Param.class)) {
                continue;
            }
            try {
                Md5Param anno = field.getAnnotation(Md5Param.class);
                Integer order = anno.order().isEmpty() ? i : Integer.valueOf(anno.order());
                String fieldName = anno.value().isEmpty() ? field.getName() : anno.value();
                md5Fields.put(String.format("%03d_%s", order, fieldName),
                        new String[] {fieldName, field.get(object).toString()});
                params.put(fieldName, field.get(object).toString());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String urlEncodeUtf8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
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
}
