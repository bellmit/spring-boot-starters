package org.shield.fadada.sdk.client.extra.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.Sha1Param;
import feign.form.FormProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class FillPageRequest {

    /**
     * 合同模板ID
     */
    @Sha1Param("contract_template_id")
    @NotBlank
    private String contractTemplateId;

    /**
     * 合同ID
     */
    @Sha1Param("contract_id")
    @NotBlank
    private String contractId;

    /**
     * 合同名称抬头
     */
    @FormProperty("doc_title")
    @NotBlank
    private String docTitle;

    /**
     * 填充完回调地址
     */
    @FormProperty("return_url")
    @NotBlank
    private String returnUrl;
}
