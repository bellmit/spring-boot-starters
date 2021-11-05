package org.shield.fadada.sdk.client.core.request;

import org.shield.fadada.sdk.annatation.Sha1Param;
import lombok.Data;

/**
 * 获取个人实名认证地址
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class PersonVerifyUrlRequest {

    @Sha1Param("customer_id")
    private String customerId;

    @Sha1Param("verified_way")
    private String verifiedWay;

    @Sha1Param("page_modify")
    private String pageModify;

    @Sha1Param("notify_url")
    private String notifyUrl;

    @Sha1Param("return_url")
    private String returnUrl;

    @Sha1Param("customer_name")
    private String customerName;

    @Sha1Param("customer_identity_type")
    private String customerIdentType;

    @Sha1Param("customer_identity_no")
    private String customerIdentNo;

    @Sha1Param("mobile")
    private String mobile;

    @Sha1Param("ident_front_path")
    private String identFrontPath;

    @Sha1Param("ident_front_img")
    private String identFrontImg;

    @Sha1Param("ident_back_path")
    private String identBackPath;

    @Sha1Param("ident_back_img")
    private String identBackImg;

    @Sha1Param("id_photo_optional")
    private String idPhotoOptional;

    @Sha1Param("result_type")
    private String resultType;

    @Sha1Param("cert_flag")
    private String certFlag;

    @Sha1Param("option")
    private String option;

    @Sha1Param("cert_type")
    private String certType;

    @Sha1Param("bank_card_no")
    private String bankCardNo;

    @Sha1Param("is_mini_program")
    private String isMiniProgram;

    @Sha1Param("lang")
    private String lang;

    @Sha1Param("is_allow_overseas_bank_card_auth")
    private String isAllowOverseasBankCardAuth;
}
