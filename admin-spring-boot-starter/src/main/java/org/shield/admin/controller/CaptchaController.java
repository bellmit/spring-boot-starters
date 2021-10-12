package org.shield.admin.controller;

import org.shield.admin.vo.CaptchaVo;
import org.shield.captcha.service.CaptchaService;
import org.shield.captcha.model.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zacksleo@gmail.com
 */
@Api(tags = "系统账号: 验证码")
@RequestMapping("captchas")
@RestController("AdminConsoleCaptchaController")
public class CaptchaController {

    @Autowired
    CaptchaService service;

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiOperation("获取验证码")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CaptchaVo create() {
        Captcha captcha = service.create();
        CaptchaVo vo = new CaptchaVo();
        vo.setBase64(captcha.getCaptcha().getImageBase64Data());
        vo.setKey(captcha.getKey());
        return vo;
    }
}
