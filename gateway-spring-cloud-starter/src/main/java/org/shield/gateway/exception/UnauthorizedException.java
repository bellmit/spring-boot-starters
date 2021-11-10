package org.shield.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class UnauthorizedException extends Exception {

    public UnauthorizedException(String message) {
        super(message);
    }

    public byte[] toBytes() {
        Map<String, Object> data = new HashMap<>(5);
        data.put("timestamp", new Date());
        data.put("status", 401);
        data.put("error", "Unauthorized");
        data.put("message", getMessage());
        String result;
        try {
            result = new ObjectMapper().writeValueAsString(data);
        } catch (Exception e) {
            result = "{\"status\": \"401\",\"error\": \"Unauthorized\",\"message\": \"invalid token\"}";
        }
        return result.getBytes(StandardCharsets.UTF_8);
    }
}
