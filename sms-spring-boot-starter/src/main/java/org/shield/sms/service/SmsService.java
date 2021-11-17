package org.shield.sms.service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public boolean sendSmsCode(String phone, String code);

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param code
     * @param templateId
     * @return
     */
    public boolean sendSmsCode(String phone, String code, String templateId);
}
