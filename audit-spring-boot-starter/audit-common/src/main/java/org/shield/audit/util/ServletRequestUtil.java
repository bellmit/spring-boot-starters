package org.shield.audit.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class ServletRequestUtil {

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
            return val;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHeader(String key, String defaultValue) {
        String value = getHeader(key);
        return value == null ? defaultValue : value;
    }
}
