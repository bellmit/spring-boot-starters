package org.shield.fadada.sdk.client;

import java.util.List;
import org.shield.fadada.sdk.annatation.HasSha1Param;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.shield.fadada.sdk.client.extra.request.DefaultSignatureRequest;
import org.shield.fadada.sdk.client.extra.request.QuerySignatureRequest;
import org.shield.fadada.sdk.client.extra.request.RemoveSignatureRequest;
import org.shield.fadada.sdk.client.extra.request.ReplaceSignatureRequest;
import org.shield.fadada.sdk.client.extra.response.GetPdfTemplateKeysResponse;
import org.shield.fadada.sdk.client.extra.response.QuerySignatureResponse;
import org.shield.fadada.sdk.config.FadadaFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 法大大客户端
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@FeignClient(name = "fadada-client", url = "${fadada.sdk.url:https://api.fadada.com:8443/api/ }",
        configuration = FadadaFeignConfiguration.class)
public interface FadadaClient extends FadadaCoreClient {

    /// 模版

    /**
     * 上传合同模板接口
     *
     * 用于跳转编辑页面,参与加密摘要参数（除公共参数外，还需contract_template_id）
     *
     *
     * @see https://open.fadada.com/#/portal/documentCenter/ZAWA2TJJHS/UQO9JG9B5VWHNUOE
     *
     * @param contractTemplateId 合同模板id
     * @param templateName 模版名称
     * @param file 上传合同的文件
     * @return
     */
    @PostMapping(value = "upload_template_docs.api", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    boolean uploadTemplateDocs(@Sha1Param("contract_template_id") String contractTemplateId,
            @RequestParam("template_name") String templateName, @RequestPart(value = "file") MultipartFile file);

    /**
     * 根据模板id跳转编辑页面
     *
     * @see https://open.fadada.com/#/portal/documentCenter/GPOKR4RYUI/CMRFMZQHFTKHUFVX
     *
     * @param contractTemplateId 合同模板id
     * @param returnUrl 保存后的跳转地址
     * @return
     */
    @GetMapping(value = "get_doc_stream.api")
    ResponseEntity<byte[]> getDocStream(@Sha1Param("contract_template_id") String contractTemplateId,
            @RequestParam("return_url") String returnUrl);

    /**
     * 跳转合同填充页面接口
     *
     * 跳转合同填充页面,参与加密摘要参数（除公共参数外，还需contract_id，contract_template_id）
     *
     * @see https://open.fadada.com/#/portal/documentCenter/ZJFP96S4ZM/CBACETUYCJXBPPGW
     *
     * @param contractTemplateId 合同模板ID
     * @param contractId 合同ID
     * @param docTitle 合同名称抬头
     * @param returnUrl 填充完回调地址
     * @return
     */
    @Deprecated
    @GetMapping(value = "fill_page.api")
    ResponseEntity<byte[]> fillPage(@Sha1Param("contract_template_id") String contractTemplateId,
            @Sha1Param("contract_id") String contractId, @RequestParam("doc_title") String docTitle,
            @RequestParam("return_url") String returnUrl);

    /**
     * 获取 pdf 模版表单域 key 值接口
     *
     * 获取到模板中可填充的key-value键值对。
     *
     * @param templateId 模板ID
     * @param fillType 0：pdf模板；1：在线填充模板 不填默认为0
     * @return
     */
    @PostMapping("get_pdftemplate_keys.api")
    public List<GetPdfTemplateKeysResponse> getPdfTemplateKeys(@Sha1Param("template_id") String templateId,
            @RequestParam("fill_type") Integer fillType);

    /**
     * 获取 在线填充PDF模板 表单域 key 值接口
     *
     * 获取到模板中可填充的key-value键值对。
     *
     * @param templateId 模板ID
     * @return
     */
    default List<GetPdfTemplateKeysResponse> getOnlinePdfTemplateKeys(@Sha1Param("template_id") String templateId) {
        return getPdfTemplateKeys(templateId, 0);
    }

    /**
     * 查询签章接口 获取用户签章图片
     *
     * @see https://open.fadada.com/#/portal/documentCenter/0KYAHH3YBI/WU56O467E5V4HVL1
     *
     * @param querySignatureRequest
     * @return
     */
    @PostMapping("query_signature.api")
    public List<QuerySignatureResponse> querySignature(@HasSha1Param QuerySignatureRequest querySignatureRequest);

    /**
     * 替换签章接口 替换用户签章图片
     *
     * @see https://open.fadada.com/#/portal/documentCenter/TB440ESQOB/H5DMRQ6EKEAGSY38
     *
     * @param replaceSignatureRequest
     * @return
     */
    @PostMapping("replace_signature.api")
    public boolean replaceSignature(@HasSha1Param ReplaceSignatureRequest replaceSignatureRequest);

    /**
     * 删除签章接口 删除用户签章图片
     *
     * @see https://open.fadada.com/#/portal/documentCenter/SSDKQ0E1TV/MPDSMLOBZXIRN6EL
     *
     * @param removeSignatureRequest
     * @return
     */
    @PostMapping("remove_signature.api")
    public boolean removeSignature(@HasSha1Param RemoveSignatureRequest removeSignatureRequest);

    /**
     * 设置默认签章接口
     *
     * @see https://open.fadada.com/#/portal/documentCenter/D8LLJTXQXN/ZDPN4F1A7ANJTRRB
     *
     * @param defaultSignatureRequest
     * @return
     */
    @PostMapping("default_signature.api")
    public boolean defaultSignature(@HasSha1Param DefaultSignatureRequest defaultSignatureRequest);
}
