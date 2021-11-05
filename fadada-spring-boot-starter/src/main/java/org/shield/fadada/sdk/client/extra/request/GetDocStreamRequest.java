package org.shield.fadada.sdk.client.extra.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.RequestUrl;
import org.shield.fadada.sdk.annatation.Sha1Param;
import feign.form.FormProperty;
import lombok.Data;

/**
 * 根据模板id跳转编辑页面
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@RequestUrl("get_doc_stream.api")
public class GetDocStreamRequest {

    /**
     * 合同模板ID
     */
    @Sha1Param("contract_template_id")
    @NotBlank
    private String contractTemplateId;

    /**
     * 填充完回调地址
     */
    @FormProperty("return_url")
    private String returnUrl;
}
