package org.shield.audit.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Document("access_log")
public class AccessLog {

    /**
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识", example = "org.shield.admin", required = true)
    @Field(name = "`tenant`")
    @Indexed
    private String tenant;

    /**
     * 业务模块
     */
    @ApiModelProperty(value = "IP地址", example = "183.6.7.210")
    @Field(name = "`ip`")
    @Indexed
    @NotBlank
    private String ip;

    /**
     * 位置
     */
    @ApiModelProperty(value = "位置", example = "河北省 衡水市")
    @Field(name = "`location`")
    @Indexed
    private String location;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器", example = "Chrome 91")
    @Field(name = "`browser`")
    @Indexed
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统", example = "Windows 10")
    @Field(name = "`os`")
    @Indexed
    private String os;

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址", example = "https://github.com")
    @Field(name = "`url`")
    @NotBlank
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", example = "POST")
    @Field(name = "`request_method`")
    @NotBlank
    private String requestMethod;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", example = "{}")
    @Field(name = "`request_params`")
    private String requestParams;

    /**
     * 请求编号
     */
    @ApiModelProperty(value = "请求编号", example = "REQUEST_ID")
    @Field(name = "`request_id`")
    private String requestId;

    /**
     * 操作人编号
     */
    @ApiModelProperty(value = "操作人编号", example = "ADMI13EBB25728C21000")
    @Field(name = "`operator_id`")
    @Indexed
    private String operatorId;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称", example = "管理员")
    @Field(name = "`operator_name`")
    @Indexed
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "备注")
    @Field(name = "`remark`")
    private String remark;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Field(name = "`record_time`")
    private Date recordTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Field(name = "`create_time`")
    private Date createTime;
}
