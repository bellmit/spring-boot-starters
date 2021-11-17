package org.shield.sms.service.impl;

import org.shield.sms.autoconfigure.AliyunSmsProperties;
import org.shield.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class AliyunSmsServiceImpl implements SmsService {

    private final Logger logger = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);

    private Client client;

    private AliyunSmsProperties props;

    private static final String OK = "OK";

    public AliyunSmsServiceImpl(Client client, AliyunSmsProperties props) {
        this.client = client;
        this.props = props;
    }

    @Override
    public boolean sendSmsCode(String phone, String code) {
        return sendSmsCode(phone, code, props.getDefaultTemplateCode());
    }

    @Override
    public boolean sendSmsCode(String phone, String code, String templateCode) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phone);
        sendSmsRequest.setSignName(props.getSignName());
        sendSmsRequest.setTemplateCode(templateCode);
        sendSmsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse response;
        try {
            response = client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response == null || response.getBody() == null) {
            return false;
        }
        if (!OK.equals(response.getBody().code)) {
            logger.warn("send sms code failed, phone: {}, code: {}, response: {}", phone, code, response.getBody());
            return false;
        }
        return true;
    }
}