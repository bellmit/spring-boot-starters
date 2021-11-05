package org.shield.fadada.sdk.client.core.request;

import javax.validation.constraints.NotBlank;
import org.shield.fadada.sdk.annatation.Md5Param;
import org.shield.fadada.sdk.annatation.RequestUrl;
import org.shield.fadada.sdk.annatation.Sha1Param;
import feign.form.FormProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手动签署
 *
 * 该接口为页面接口，接入方平台可以在合适的业务场景嵌入该接口链接，引导客户至法大大进行文档签署（比如可以在接入方平台网站上放置一个按钮，该按钮触发跳转至法大大
 * 或将法大大签章页面嵌入接入方流程）。法大大根据浏览器UA信息，自动加载签章界面对应的PC web版本或移动HTML5版本。用户在法大大的签署页面中进行签署操作
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@RequestUrl("extsign.api")
public class ExtSignRequest {

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
     * 关键字为文档中的文字内容（要能使用ctrl+f搜索到）。
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
     * 示例：[{"pagenum":0,"x":350,"y":350},{"pagenum":1,"x":350.225,"y":750}]
     * 当position_type为1时，此参数必填
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
     * 页面跳转URL（签署结果同步通知）
     *
     * 签章完成后，在签章页面停留数秒后浏览器会自动跳转到此URL，内容由接入平台自行展示；
     * 参见[页面跳转规范（return_url）](#_5.20页面跳转规范（return_url）)
     */
    @FormProperty("return_url")
    private String returnUrl;

    /**
     * 签署结果异步通知URL
     *
     * 如果指定，当签章完成后，法大大将向此URL发送签署结果。
     * 参见[签署结果异步通知规范（notify_url）](#_5.21签署结果异步通知规范（notify_url）) notify_url最大长度为500
     */
    @FormProperty("notify_url")
    private String notifyUrl;

    /**
     * 合同必读时间
     * 0-60秒
     */
    @FormProperty("read_time")
    private String readTime;

    /**
     * 页面语言：zh:中文，en:英文
     */
    private String lang;

    /**
     * 签章类型 0：全部；1：标准；2：手写
     * 当接口中该参数传入有值时，以接口传入的配置为准，否则则取运营后台配置
     */
    @FormProperty("mobile_sign_type")
    @ApiModelProperty(value = "签章类型 0：全部；1：标准；2：手写", example = "2")
    private Integer mobileSignType;

    /**
     * 是否开启手写轨迹
     *
     * 0：关闭 1：开启
     * 仅支持中文
     * 当接口中该参数传入有值时，以接口传入的配置为准，否则则取运营后台配置
     */
    @FormProperty("writing_track")
    private String writingTrack;

    /**
     * 支持pc手写印章
     * 1：pc端支持手写印章（需mobil_sign_type签章类型为2时支持）
     */
    @FormProperty("pc_hand_signature")
    private String pcHandSignature;

    /**
     * 签署意愿方式 1：短信；3：人脸识别
     * 当接口中该参数传入有值时，以接口传入的配置为准，否则则取运营后台配置
     */
    @FormProperty("sign_verify_way")
    private String signVerifyWay;

    /**
     * 签署意愿方式选择人脸识别时，人脸识别失败后自动调整为短信
     * 默认为0
     * 0：不切换；1：切换
     */
    @FormProperty("verify_way_flag")
    private String verifyWayFlag;

    /**
     * 打开环境
     *
     * 0、跳转h5；（默认）1、支持在客户小程序path中跳转，path的写法如 /page/page1； 2、跳转法大大公证处小程序
     */
    @FormProperty("open_environment")
    private String openEnvironment;

    /**
     * 时间戳显示方式
     *
     * 不传默认为1
     * 1：以后台配置为准，如果存在部分签署显示、部分签署不显示的场景，需要配置显示时间戳，以及时间戳显示样式 2：不显示时间戳
     */
    @FormProperty("signature_show_time")
    private String signatureShowTime;


    /**
     * 骑缝章印章的客户编号
     *
     * 需盖骑缝章合同的页数必须大于1页。
     */
    @FormProperty("across_page_customer_id")
    private String acrossPageCustomerId;

    /**
     * 骑缝章id
     *
     * 该印章必须属于acrosspage_customer_id，并且该印章不会显示在签署页面，用户签署时直接加盖在合同上。不支持坐标，关键字
     */
    @FormProperty("across_signature_id")
    private String acrossSignatureId;

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
