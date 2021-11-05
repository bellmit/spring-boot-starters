package org.shield.fadada.sdk.client.extra.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.Sha1Param;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 替换签章
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@ApiModel
public class ReplaceSignatureRequest {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号", example = "BAD1AB602E619CFF4822A20188B7FE6C", required = true)
    @NotBlank(message = "客户编号不能为空")
    @Sha1Param("customer_id")
    private String customerId;

    @ApiModelProperty(value = "签章编号", example = "SIGN0001", required = true)
    @NotBlank(message = "签章编号不能为空")
    @Sha1Param("signature_id")
    private String signatureId;

    @ApiModelProperty(value = "签章图片", notes = "需去掉data:image/jpg;base64前缀", example = "R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==", required = true)
    @NotBlank(message = "签章图片不能为空")
    @Sha1Param("signature_img_base64")
    private String signatureImgBase64;
}
