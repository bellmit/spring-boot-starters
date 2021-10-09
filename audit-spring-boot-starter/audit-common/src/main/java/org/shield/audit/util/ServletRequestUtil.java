package org.shield.audit.util;

import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class ServletRequestUtil {

    private static final String START_WITH = "%";

    private ServletRequestUtil() {
    }

    public static String getHeader(String key) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            if (!(request instanceof HttpServletRequest)) {
                return null;
            }
            String val = request.getHeader(key);
            if (val == null || val.isEmpty()) {
                return null;
            }
            if (shouldDecode(val)) {
                return convert(val);
            }
            return val;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHeader(String key, String defaultValue) {
        String value = getHeader(key);
        return value == null ? defaultValue : value;
    }

    /**
     * 是否需要解码
     *
     * @param value
     * @return
     */
    private static boolean shouldDecode(String value) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        if (value.indexOf(START_WITH) < 0) {
            return false;
        }
        return true;
    }

    /**
     * 解码
     *
     * @param source
     * @return
     */
    private static String convert(final String source) {
        try {
            return URLDecoder.decode(source, Charset.forName("UTF-8").name());
        } catch (Exception e) {
            return source;
        }
    }
}
