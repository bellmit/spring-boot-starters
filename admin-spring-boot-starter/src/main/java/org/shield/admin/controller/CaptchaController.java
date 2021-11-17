package org.shield.admin.controller;

import org.shield.admin.vo.CaptchaVo;
import org.shield.captcha.service.CaptchaService;
import org.shield.redis.service.CacheService;
import org.shield.rest.exception.BadRequestException;
import org.shield.sms.service.SmsService;
import org.shield.validation.validator.annotation.Phone;
import org.shield.captcha.model.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.util.RandomUtil;
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
    private CaptchaService captchaService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private CacheService cacheService;

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiOperation("获取验证码")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CaptchaVo create() {
        Captcha captcha = captchaService.create();
        CaptchaVo vo = new CaptchaVo();
        vo.setBase64(captcha.getCaptcha().getImageBase64Data());
        vo.setKey(captcha.getKey());
        return vo;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiOperation("获取短信验证码")
    @PostMapping("/sms-codes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@RequestParam("phone") @Phone String phone) {
        String code = RandomUtil.randomNumbers(6);
        if (!smsService.sendSmsCode(phone, code)) {
            throw new BadRequestException("短信验证码发送失败");
        }
        cacheService.set(TokenController.LOGIN_SMS_CODE_PREFIX + phone, code, 600);
    }
}
