package org.shield.fadada.sdk.client;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResult {
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultCode {
        private ResultCode(){}

        public static final List<Integer> SUCCESS_CODES = Arrays.asList(1, 1000);
    }
}
