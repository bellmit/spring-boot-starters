package org.shield.fadada.sdk.client.core.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.Md5Param;
import org.shield.fadada.sdk.annatation.Sha1Param;
import feign.form.FormProperty;
import lombok.Data;

/**
 * 自动签署
 *
 * 自动签接口不需要用户亲自操作，当接入平台调用此接口时，就会在指定的电子合同上签上指定用户的电子章，省略了用户交互的步骤。 该签署接口对于用户是无感知的，会存在一定的法律风险，默认只允许接入平台一家企业调用；
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class ExtSignAutoRequest {

    /**
     * 交易号
     *
     * 每次请求视为一个交易。 只允许长度<=32的英文或数字字符。 交易号为接入平台生成，必须保证唯一并自行记录。
     */
    @NotBlank(message = "交易号不能为空")
    @Md5Param(value = "transaction_id", order = "-100")
    private String transactionId;

    /**
     * 合同编号
     *
     * 根据合同编号指定在哪份文档上进行签署。 合同编号在文档传输或合同生成时设定。
     */
    @NotBlank(message = "合同编号不能为空")
    @FormProperty("contract_id")
    private String contractId;

    /**
     * 客户编号
     *
     * 注册账号时返回。
     */
    @NotBlank(message = "客户编号不能为空")
    @Sha1Param("customer_id")
    private String customerId;

    /**
     * 客户角色
     *
     * 1-接入平台；
     */
    @FormProperty("client_role")
    private Integer clientRole;

    /**
     * 文档标题
     *
     * 如“xx投资合同” 。需URL encoding处理，编码utf-8
     */
    @NotBlank(message = "文档标题不能为空")
    @FormProperty("doc_title")
    private String docTitle;

    /**
     * 定位类型
     *
     * 0-关键字 1-坐标
     */
    @FormProperty("position_type")
    private Integer positionType = 0;

    /**
     * 定位关键字
     *
     * 当position_type为0时，此参数必填 关键字为文档中的文字内容（要能使用ctrl+f搜索到）。
     * 法大大按此关键字进行签章位置的定位，将电子章盖在这个关键字上面。凡出现关键字的地方均会盖上指定用户的电子章，建议关键字在合同中保持唯一。
     */
    @FormProperty("sign_keyword")
    private String signKeyword;

    /**
     * 关键字签章策略
     *
     * 0：所有关键字签章 （默认）；1：第一个关键字签章 ；2：最后一个关键字签章；
     */
    @FormProperty("keyword_strategy")
    private Integer keywordStrategy = 0;

    /**
     * 定位坐标
     *
     * 示例：[{"pagenum":0,"x":350,"y":350},{"pagenum":1,"x":350.225,"y":750}] 当position_type为1时，此参数必填
     */
    @FormProperty("signature_positions")
    private String signaturePositions;

    /**
     * 印章编号
     *
     * 调新增签章接口返回
     */
    @FormProperty("signature_id")
    private String signatureId;

    /**
     * 签署结果异步通知URL
     *
     * 如果指定，当签章完成后，法大大将向此URL发送签署结果。 参见[签署结果异步通知规范（notify_url）](#_5.21签署结果异步通知规范（notify_url）) notify_url最大长度为500
     */
    @FormProperty("notify_url")
    private String notifyUrl;

    /**
     * 时间戳显示方式
     *
     * 不传默认为1 1：以后台配置为准，如果存在部分签署显示、部分签署不显示的场景，需要配置显示时间戳，以及时间戳显示样式 2：不显示时间戳
     */
    @FormProperty("signature_show_time")
    private String signatureShowTime;

    /**
     * 关键字偏移量，偏移x位置
     *
     * [-595,595]之间的数字
     */
    private Integer keyx;

    /**
     * 关键字偏移量，偏移y位置
     *
     * [-842,842]之间的数字
     */
    private Integer keyy;
}
