package org.shield.admin.form;

import javax.validation.constraints.NotBlank;
import org.shield.captcha.validator.annatation.ValidCaptcha;
import org.shield.validation.validator.annotation.Phone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@ApiModel("短信登录")
@Data
public class SmsLoginForm {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18888888888", required = true)
    @NotBlank(message = "请输入手机号")
    @Phone
    private String phone;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "短信验证码", example = "345876")
    @NotBlank(message = "请输入验证码")
    @ValidCaptcha
    private String verifyCode;
}
