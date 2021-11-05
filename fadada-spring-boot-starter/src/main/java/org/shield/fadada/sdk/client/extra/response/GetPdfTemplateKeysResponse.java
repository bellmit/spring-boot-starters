package org.shield.fadada.sdk.client.extra.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 *
 * 获取 pdf 模版表单域 key 值
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPdfTemplateKeysResponse {

    /**
     * 表单域 key 值
     */
    @ApiModelProperty(name = "form_keys", example = "fieldName1")
    private String key;

    /**
     * 表单域名称
     */
    @ApiModelProperty(name = "name", example = "contractId")
    private String name;

    /**
     * 开始位置
     */
    @ApiModelProperty(name = "pageBegin", example = "1")
    private Integer pageBegin;

    /**
     * 水平位移
     */
    @ApiModelProperty(name = "xs", example = "601.5")
    private Float xs;

    /**
     * 垂直位移
     */
    @ApiModelProperty(name = "ys", example = "190")
    private Float ys;
}
