package org.shield.fadada.sdk.client.core.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.RequestUrl;
import org.shield.fadada.sdk.annatation.Sha1Param;
import lombok.Data;

/**
 * 页面接口，返回签署页面，根据浏览器UA信息返回pc或H5页面.
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@RequestUrl("viewContract.api")
public class ViewContractRequest {

    /**
     * 合同编号
     */
    @Sha1Param("contract_id")
    @NotBlank(message = "合同编号不能为空")
    private String contractId;
}
