package org.shield.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class ForbiddenException extends Exception {

    public ForbiddenException(String message) {
        super(message);
    }

    public byte[] toBytes() {
        Map<String, Object> data = new HashMap<>(5);
        data.put("timestamp", new Date());
        data.put("status", 403);
        data.put("error", "Forbidden");
        data.put("message", getMessage());
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(data);
        } catch (Exception e) {
            result = "{\"status\": \"403\",\"error\": \"Forbidden\",\"message\": \"没有权限进行该操作\"}";
        }
        return result.getBytes(StandardCharsets.UTF_8);
    }
}
