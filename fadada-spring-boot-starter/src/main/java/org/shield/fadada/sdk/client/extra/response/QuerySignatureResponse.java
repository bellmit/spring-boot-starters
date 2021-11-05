package org.shield.fadada.sdk.client.extra.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuerySignatureResponse {

    /**
     * 企业或者个人 1：个人 2：企业
     */
    private Integer signatureType;
    /**
     * 签章图片ID
     */
    private String signatureId;

    /**
     * 签章图片base64
     */
    private String signatureImgBase64;

    /**
     * 签章图片作用范围：0：非默认 1：默认章
     */
    private Integer signatureScope;

    /**
     * 扩展信息，
     */
    private String signatureSubInfo;

    /**
     * 签章来源 0 平台上传 1 法大大生成
     */
    private String signatureSource;
}
