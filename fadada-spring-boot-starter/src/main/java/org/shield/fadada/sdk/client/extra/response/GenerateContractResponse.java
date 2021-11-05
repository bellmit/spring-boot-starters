package org.shield.fadada.sdk.client.extra.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class GenerateContractResponse {
    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址",
            example = "https://testapi.fadada.com:8443/api/downLoadContract.action?app_id=405629&v=2.0&timestamp=20211103140432&contract_id=1635911976&msg_digest=NTU3QjlGMjYxRDE3MDU4OEE2RUE1NDBBQThFNzZBOTNBREQ2NEJCQQ==")
    private String downloadUrl;
    /**
     * 查看地址
     */
    @ApiModelProperty(value = "查看地址",
            example = "https://testapi.fadada.com:8443/api/viewContract.action?app_id=405629&v=2.0&timestamp=20211103140432&contract_id=1635911976&msg_digest=NTU3QjlGMjYxRDE3MDU4OEE2RUE1NDBBQThFNzZBOTNBREQ2NEJCQQ==")
    private String viewPdfUrl;
}
