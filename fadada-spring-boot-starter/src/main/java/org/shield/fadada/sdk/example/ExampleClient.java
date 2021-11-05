package org.shield.fadada.sdk.example;

import org.shield.fadada.sdk.annatation.HasMd5Param;
import org.shield.fadada.sdk.annatation.HasSha1Param;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.shield.fadada.sdk.client.core.request.AccountRegisterRequest;
import org.shield.fadada.sdk.client.core.request.PersonVerifyUrlRequest;
import org.shield.fadada.sdk.config.FadadaFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 调用示例
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@FeignClient(name = "fadada-example", url = "${fadada.sdk.url:https://api.fadada.com:8443/api/ }",
        configuration = FadadaFeignConfiguration.class)
public interface ExampleClient {

    /**
     * 注册账号, 使用默认的签名方式
     *
     * 需要参数 sha1 签名的参数，使用 @Sha1Param 注解标识
     * 需要参数 md5 签名的参数，使用 @HasMd5Param 注解标识
     *
     * @param openid
     * @param accountType
     * @return
     */
    @PostMapping("account_register.api")
    String register(@Sha1Param("open_id") String openid, @Sha1Param("account_type") String accountType);

    /**
     * 注册账号，使用实体类的签名方式
     *
     * 如果实体类中存在需要签名的参数，使用 @HasSha1Param / @HasMd5Param 注解标识, 实体中需要签名的字段使用 @Sha1Param / @Md5Param 注解标识
     *
     * @param request
     * @return
     */
    @PostMapping(value = "account_register.api")
    String register(@HasSha1Param AccountRegisterRequest request);

    /**
     * 获取个人实名认证地址
     *
     * @param request
     * @return
     */
    @PostMapping(value = "get_person_verify_url.api")
    String getPersonVerifyUrl(@HasMd5Param @HasSha1Param PersonVerifyUrlRequest request);

    /**
     * 上传合同模版，文件上传
     *
     * @param contractTemplateId
     * @param templateName
     * @param file
     * @return
     */
    @PostMapping(value = "upload_template_docs.api", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadTemplateDocs(@Sha1Param("contract_template_id") String contractTemplateId,
            @RequestParam("template_name") String templateName, @RequestPart(value = "file") MultipartFile file);
}
