package org.shield.fadada.sdk.client.core.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.shield.fadada.sdk.annatation.Sha1Param;
import feign.form.FormProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddSignatureRequest {

    /**
     * 客户编号，注册账号时返回
     */
    @Sha1Param("customer_id")
    private String customerId;

    /**
     * 签章图片base64, 上传的base64码需要去除前缀data:image/jpg;base64
     */
    @FormProperty("signature_img_base64")
    private String signatureImgBase64;
}
