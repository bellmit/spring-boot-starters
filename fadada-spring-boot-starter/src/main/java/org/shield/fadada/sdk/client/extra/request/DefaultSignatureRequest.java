package org.shield.fadada.sdk.client.extra.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.Sha1Param;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设置默认章接口
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@ApiModel
@Data
public class DefaultSignatureRequest {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号", example = "BAD1AB602E619CFF4822A20188B7FE6C", required = true)
    @NotBlank(message = "客户编号不能为空")
    @Sha1Param("customer_id")
    private String customerId;

    @ApiModelProperty(value = "签章编号", example = "SIGN0001")
    @Sha1Param("signature_id")
    private String signatureId;
}

