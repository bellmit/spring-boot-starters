package org.shield.fadada.sdk.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result<T> extends BaseResult {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
