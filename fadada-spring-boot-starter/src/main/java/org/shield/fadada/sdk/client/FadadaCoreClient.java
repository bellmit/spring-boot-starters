package org.shield.fadada.sdk.client;

import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.shield.fadada.sdk.annatation.HasAffixParam;
import org.shield.fadada.sdk.annatation.HasMd5Param;
import org.shield.fadada.sdk.annatation.HasSha1Param;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.shield.fadada.sdk.client.core.request.AddSignatureRequest;
import org.shield.fadada.sdk.client.core.request.ExtSignAutoRequest;
import org.shield.fadada.sdk.client.core.request.GenerateContractRequest;
import org.shield.fadada.sdk.client.core.request.PersonVerifyUrlRequest;
import org.shield.fadada.sdk.client.core.response.AddSignatureResponse;
import org.shield.fadada.sdk.config.FadadaFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@FeignClient(name = "fadada-core-client", url = "${fadada.sdk.url:https://api.fadada.com:8443/api/ }",
        configuration = FadadaFeignConfiguration.class)
public interface FadadaCoreClient {

    /**
     * get Object Mapper
     *
     * @return
     */
    static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    /**
     * 注册账号, 使用默认的签名方式
     *
     * 需要参数 sha1 签名的参数，使用 @Sha1Param 注解标识 <br/>
     * 需要参数 md5 签名的参数，使用 @HasMd5Param 注解标识
     *
     * @param openid 用户在接入方的唯一标识 字符（len<=64）不支持以下特殊字符：'!<>^\
     * @param accountType 1:个人 2:企业
     * @return
     */
    @PostMapping("account_register.api")
    String registerAccount(@Sha1Param("open_id") String openid, @Sha1Param("account_type") String accountType);

    /**
     * 获取个人实名认证地址
     *
     * @param request
     * @return
     */
    @PostMapping(value = "get_person_verify_url.api")
    String getPersonVerifyUrl(@HasMd5Param @HasSha1Param PersonVerifyUrlRequest request);

    /**
     * 实名证书申请
     *
     * 调用此接口可以给相关账号颁发证书
     *
     * @param customerId
     * @param verifiedSerialno
     * @return
     */
    @PostMapping(value = "apply_cert.api")
    boolean applyCert(@Sha1Param("customer_id") String customerId,
            @Sha1Param("verified_serialno") String verifiedSerialno);

    /**
     * 印章上传
     *
     * 上传自制的图片，新增用户签章。（上传成功后自动设置为默认章，可后台配置不自动设置为默认章）
     *
     * 每个主体只需新增一次，无需多次重复新增
     *
     * @see <a href="https://open.fadada.com/#/portal/documentCenter/ZLZRHIWBZK/FRNXNT42GGE1LPSP">印章上传</a>
     *
     * @param request
     * @return
     */
    @PostMapping("add_signature.api")
    public AddSignatureResponse addSignature(@HasSha1Param AddSignatureRequest request);

    /**
     * 自定义印章
     *
     * 新增用户签章，可自定义印章展示的内容（新增成功后自动设置为默认章，可后台配置不自动设置为默认章），自定义印章个数建议不超过50个
     *
     * 每一个印章样式，只需上传一次，无须多次重复上传
     *
     * 返回反转义后的base64，需要增加前缀data:image/jpg;base64
     *
     * @see <a href="https://open.fadada.com/#/portal/documentCenter/EYB5TTVK5K/BOOF3JAJWXQYYHWE">自定义印章</a>
     *
     * @param customerId 客户编号，注册账号时返回
     * @param content 印章展示的内容
     * @return
     */
    default String customSignature(@Sha1Param("customer_id") String customerId, @Sha1Param("content") String content) {
        return customSignatureRaw(customerId, content).replace("\\/", "/");
    }

    /**
     * 自定义印章
     *
     * 新增用户签章，可自定义印章展示的内容（新增成功后自动设置为默认章，可后台配置不自动设置为默认章），自定义印章个数建议不超过50个
     *
     * 每一个印章样式，只需上传一次，无须多次重复上传
     *
     * 返回的base64，需要将\/反转义成/，同时增加前缀data:image/jpg;base64
     *
     * @see <a href="https://open.fadada.com/#/portal/documentCenter/EYB5TTVK5K/BOOF3JAJWXQYYHWE">自定义印章</a>
     *
     * @param customerId 客户编号，注册账号时返回
     * @param content 印章展示的内容
     * @return
     */
    @PostMapping("custom_signature.api")
    public String customSignatureRaw(@Sha1Param("customer_id") String customerId, @Sha1Param("content") String content);

    /**
     * 合同上传
     *
     * 接入平台将待签署的pdf文档通过此接口将文档传输到法大大，供签署时使用，请参考“2.3.2.3 合同签署（引用模板）”流程
     *
     * @param contractId
     * @param docTitle
     * @param docUrl
     * @param docType
     * @return
     */
    @PostMapping("uploaddocs.api")
    public String uploadDocs(@Sha1Param("contract_id") String contractId, @RequestParam("doc_title") String docTitle,
            @RequestParam("doc_url") String docUrl, @RequestParam("doc_type") String docType);

    /**
     * 合同下载
     *
     * 通过合同编号下载文档（PDF格式）
     *
     * @param contractId
     *
     * @return
     */
    @GetMapping("downLoadContract.api")
    public ResponseEntity<byte[]> downLoadContract(@Sha1Param("contract_id") String contractId);

    /**
     * 合同下载
     *
     * 通过合同编号下载文档（PDF格式），可通过Content-Length取文件大小。
     *
     * <p>
     * 下载示例：
     * <p>
     *
     * <pre>
     * return ResponseEntity.ok().contentLength(resource.length).contentType(MediaType.APPLICATION_PDF)
     *         .header("Content-Disposition", "attachment;filename=contractId.pdf").body(resource);
     * </pre>
     *
     * @param contractId
     *
     * @return
     */
    @GetMapping("downLoadContract.api")
    public byte[] downLoadContractRaw(@Sha1Param("contract_id") String contractId);

    /**
     * 模板上传
     *
     * 此接口与模板填充 配合使用，接入方预先将制作好的PDF模板通过此接口上传到法大大，后续如需要签署合同时只需要将需填充的内容通过模板填充 传入，即可生成合同供签署操作，请参考“合同签署（引用模板）”流程。
     *
     * @see https://open.fadada.com/#/portal/documentCenter/DQRH9ONMCP/RH8PNGV5RJTTXBJH
     *
     * @param templateId
     * @param docUrl
     *
     * @return
     */
    @PostMapping("uploadtemplate.api")
    public boolean uploadTemplate(@Sha1Param("template_id") String templateId, @RequestParam("doc_url") String docUrl);

    /**
     * 模板填充
     *
     * 将需填充的内容通过模板填充接口传入，即可生成合同供签署操作，请参考“合同签署（引用模板）”流程。
     *
     * @see https://open.fadada.com/#/portal/documentCenter/KDIEKLMPSM/KBQEC9DVMEDQS5PD
     *
     * @param generateContractRequest
     * @param parameterMap
     * @return
     */
    @PostMapping("generate_contract.api")
    public String generateContract(@HasSha1Param @HasAffixParam GenerateContractRequest generateContractRequest);

    /**
     * 模板填充
     *
     * 将需填充的内容通过模板填充接口传入，即可生成合同供签署操作，请参考“合同签署（引用模板）”流程。
     *
     * @see https://open.fadada.com/#/portal/documentCenter/KDIEKLMPSM/KBQEC9DVMEDQS5PD
     *
     * @param generateContractRequest
     * @param parameterMap
     * @return
     * @throws JsonProcessingException
     */
    default String generateContract(GenerateContractRequest generateContractRequest, Map<String, String> parameterMap)
            throws JsonProcessingException {
        generateContractRequest.setParameterMap(getObjectMapper().writeValueAsString(parameterMap));
        return generateContract(generateContractRequest);
    }

    /**
     * 自动签署
     *
     * 自动签接口不需要用户亲自操作，当接入平台调用此接口时，就会在指定的电子合同上签上指定用户的电子章，省略了用户交互的步骤。
     *
     * @see https://open.fadada.com/#/portal/documentCenter/MTKGDVFFTS/YYGDYLFKTTDVHTAZ
     *
     * @param extSignAutoRequest
     * @return
     */
    @PostMapping("extsign_auto.api")
    public boolean extSignAuto(@HasSha1Param @HasAffixParam ExtSignAutoRequest extSignAutoRequest);

    /**
     * 合同归档
     *
     * 接入平台更新合同签署状态为-签署完成，法大大将把合同所有相关操作记录进行归档存证。归档后将不能再对文档再进行签署操作。
     *
     * @see https://open.fadada.com/#/portal/documentCenter/O3XZYZSR9C/XVUNBPZQWX37OLEA
     *
     * @param contractId
     * @return
     */
    @PostMapping("contractFiling.api")
    public boolean contractFiling(@Sha1Param("contract_id") String contractId);
}
