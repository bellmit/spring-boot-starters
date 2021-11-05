package org.shield.fadada.sdk.client.core.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.Sha1Param;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class AccountRegisterRequest {

    /**
     * 用户在接入方的唯一标识
     */
    @Sha1Param("open_id")
    @NotBlank
    private String openId;

    /**
     * 1:个人 2:企业
     */
    @Sha1Param("account_type")
    @NotBlank
    private Integer accountType;
}