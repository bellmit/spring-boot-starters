package org.shield.fadada.sdk.client.core.request;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.shield.fadada.sdk.annatation.AffixParam;
import org.shield.fadada.sdk.annatation.Sha1Param;
import feign.form.FormProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 *
 * 模板填充
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GenerateContractRequest {

    @Sha1Param(value = "template_id", order = "1")
    @NotBlank
    private String templateId;

    @Sha1Param(value = "contract_id", order = "2")
    @NotBlank
    private String contractId;

    @FormProperty("doc_title")
    private String docTitle;

    @FormProperty("font_size")
    private Float fontSize;

    @FormProperty("font_type")
    private Integer fontType;

    @FormProperty("dynamic_tables")
    private String dynamicTables;

    @FormProperty("fill_type")
    private Integer fillType = 0;

    @AffixParam("parameter_map")
    @NotBlank
    private String parameterMap;
}
