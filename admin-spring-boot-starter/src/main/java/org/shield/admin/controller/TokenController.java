package org.shield.admin.controller;

import org.shield.admin.service.TokenService;
import org.shield.admin.vo.TokenVo;
import org.shield.admin.form.PasswordLoginForm;
import org.shield.admin.form.SmsLoginForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

/**
 * 令牌
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "系统账号: 登录")
@RestController("AdminConsoleTokenController")
@RequestMapping("tokens")
public class TokenController {

    /**
     * 登录验证码缓存添加前缀，避免冲突
     */
    public static final String LOGIN_SMS_CODE_PREFIX = "ADMIN_LOGIN_CODE_";

    @Autowired
    private TokenService service;

    /**
     * 账号密码登录
     *
     * @param form
     * @return
     */
    @ApiOperation("通过验证码获取令牌")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "{{#form.username}}", category = "LOGIN", detail = "{{#_ret}}",
            fail = "登录失败: {{#_errorMsg}}", success = "登录成功: {{#form.username}}", prefix = "")
    public TokenVo create(@Valid @RequestBody PasswordLoginForm form) throws Exception {
        return service.create(form);
    }

    /**
     * 短信验证码登录
     *
     * @param form
     * @return
     */
    @ApiOperation("通过短信验证码获取令牌")
    @PostMapping("/sms")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "{{#form.phone}}", category = "LOGIN", detail = "{{#_ret}}",
            fail = "登录失败: {{#_errorMsg}}", success = "登录成功: {{#form.phone}}", prefix = "")
    public TokenVo create(@RequestBody SmsLoginForm form) throws Exception {
        form.setVerifyCode(LOGIN_SMS_CODE_PREFIX + form.getPhone() + ":" + form.getVerifyCode());
        return service.create(form);
    }
}
