package org.shield.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Table(name = "audit_log")
public class AuditLog {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    @JsonIgnore
    private Long id;

    /**
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识", example = "org.shield.admin")
    @Column(name = "`tenant`")
    private String tenant;

    /**
     * 日志业务标识
     */
    @ApiModelProperty(value = "日志业务标识", example = "ORDER")
    @Column(name = "`biz_key`")
    @NotBlank(message = "bizKey required")
    @Length(max = 200, message = "appKey max length is 200")
    private String bizKey;

    /**
     * 业务编号
     */
    @ApiModelProperty(value = "业务编号", example = "ODER140477CEA9821000")
    @Column(name = "`biz_no`")
    private String bizNo;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人", example = "ADMI13EBB25728C21000")
    @Column(name = "`operator`")
    @NotBlank(message = "operator required")
    @Length(max = 63, message = "operator max length 63")
    private String operator;

    /**
     * 动作
     */
    @ApiModelProperty(value = "动作", example = "发货")
    @Column(name = "`action`")
    @NotBlank(message = "opAction required")
    @Length(max = 511, message = "operator max length 511")
    private String action;

    /**
     * 种类
     */
    @ApiModelProperty(value = "种类", example = "订单管理")
    @Column(name = "`category`")
    private String category;

    /**
     * 详细信息
     */
    @ApiModelProperty(value = "详细信息", example = "更新了订单ODER140477CEA9821000,更新内容为...")
    @Column(name = "`detail`")
    private String detail;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`create_time`")
    private Date createTime;
}
