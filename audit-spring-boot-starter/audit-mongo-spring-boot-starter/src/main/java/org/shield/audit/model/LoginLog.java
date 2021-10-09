package org.shield.audit.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
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
@Document("login_log")
public class LoginLog {

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
     * 类别
     */
    @ApiModelProperty(value = "类别", example = "订单管理")
    @Field(name = "`catalog`")
    @Indexed
    private String catalog;

    /**
     * 操作人编号
     */
    @ApiModelProperty(value = "操作人编号", example = "ADMI13EBB25728C21000")
    @Field(name = "`operator_id`")
    @NotBlank(message = "操作人编号不能为空")
    @Length(max = 64, message = "操作人编号最长为64")
    @Indexed
    private String operatorId;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称", example = "ADMI13EBB25728C21000")
    @Field(name = "`operator_name`")
    @NotBlank(message = "操作人名称不能为空")
    @Length(max = 64, message = "操作人名称最长为64")
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
