package org.shield.fadada.sdk.client.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddSignatureResponse {

    /**
     * 签章图片ID
     */
    private String signatureId;

    /**
     * 扩展信息，目前为空
     */
    private String signatureSubInfo;
}
