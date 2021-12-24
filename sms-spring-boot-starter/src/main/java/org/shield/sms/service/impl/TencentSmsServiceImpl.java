package org.shield.sms.service.impl;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.shield.sms.autoconfigure.TencentSmsProperties;
import org.shield.sms.service.SmsService;
import com.tencentcloudapi.sms.v20210111.SmsClient;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class TencentSmsServiceImpl implements SmsService {

    private SmsClient client;

    private TencentSmsProperties props;

    private static final String OK = "OK";
    private static final String PLUS = "+";

    public TencentSmsServiceImpl(SmsClient client, TencentSmsProperties props) {
        this.client = client;
        this.props = props;
    }

    @Override
    public boolean sendSmsCode(String phone, String code) {
        sendSmsCode(phone, code, props.getDefaultTemplateCode());
        return false;
    }

    @Override
    public boolean sendSmsCode(String phone, String code, String templateId) {

        try {

            SendSmsRequest req = new SendSmsRequest();

            /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666 */
            String sdkAppId = props.getSdkAppId();
            req.setSmsSdkAppId(sdkAppId);

            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
            req.setSignName(props.getSignName());

            req.setTemplateId(templateId);

            /*
             * 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号] 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号
             */
            if (!phone.startsWith(PLUS)) {
                phone = "+86" + phone;
            }
            req.setPhoneNumberSet(new String[] {phone});

            /* 模板参数: 若无模板参数，则设置为空 */
            String[] templateParamSet = {code};
            req.setTemplateParamSet(templateParamSet);

            /*
             * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应
             */
            SendSmsResponse res = client.SendSms(req);

            if (OK.equals(res.getSendStatusSet()[0].getCode())) {
                return true;
            }

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return false;
    }
}
