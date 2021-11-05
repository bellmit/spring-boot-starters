package org.shield.fadada.sdk.decoder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shield.fadada.sdk.client.BaseResult;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Slf4j
public class ResultDecoder implements Decoder {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String DATA = "data";

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {

        /// ============默认super实现===========

        // 404, 204 返回空
        if (response.status() == HttpStatus.NOT_FOUND.value() || response.status() == HttpStatus.NO_CONTENT.value()) {
            return Util.emptyValueOf(type);
        }
        // body 为空时返回 null
        if (response.body() == null) {
            return null;
        }
        // 返回类型为 byte[] 时，直接返回
        if (byte[].class.equals(type)) {
            return Util.toByteArray(response.body().asInputStream());
        }

        /// ==============自定义实现==============

        // 返回类型为 ResponseEntity 时，使用 ResponseEntityDecoder 解析
        if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(ResponseEntity.class)) {
            return new ResponseEntityDecoder(new Default()).decode(response, type);
        }

        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        log.info("fadada response body: {}", bodyStr);

        // 获取状态码
        BaseResult result = json2obj(bodyStr, BaseResult.class);
        boolean success = BaseResult.ResultCode.SUCCESS_CODES.contains(result.getCode());

        // 如果是布尔类型，则根据 code 解析
        if (Boolean.class.equals(type) || boolean.class.equals(type)) {
            return success;
        }

        // 返回的不是布尔类型，如果操作失败，抛出异常
        if (!success) {
            throw new DecodeException(response.status(), "接口返回错误：" + result.getMsg(), response.request());
        }

        JsonNode jsonNode = mapper.readTree(bodyStr);

        // 如果是基本类型（字符串），则直接返回 data 部分
        if (type.equals(String.class)) {
            return !jsonNode.has(DATA) ? bodyStr : jsonNode.get(DATA).toString();
        }

        // 如果不包含 data 字段，则直接解析当前层级中的字段
        if (!jsonNode.has(DATA)) {
            return json2obj(bodyStr, type);
        }

        if (jsonNode.get(DATA).isTextual()) {
            return json2obj(jsonNode.get(DATA).asText(), type);
        }

        return json2obj(jsonNode.get(DATA).toString(), type);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param <T>
     * @param jsonStr
     * @param targetType
     * @return
     */
    public static <T> T json2obj(String jsonStr, Type targetType) {
        try {
            return mapper.readValue(jsonStr, mapper.constructType(targetType));
        } catch (IOException e) {
            throw new IllegalArgumentException("decode failed:" + jsonStr, e);
        }
    }
}
