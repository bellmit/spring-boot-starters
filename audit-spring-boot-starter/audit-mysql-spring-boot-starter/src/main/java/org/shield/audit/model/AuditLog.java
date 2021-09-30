package org.shield.audit.model;

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
     * 业务模块
     */
    @ApiModelProperty(value = "业务模块", example = "ORDER")
    @Column(name = "`module`")
    @NotBlank(message = "业务模块不能为空")
    @Length(max = 128, message = "业务模块最长为128")
    private String module;

    /**
     * 业务编号
     */
    @ApiModelProperty(value = "业务编号", example = "ODER140477CEA9821000")
    @Column(name = "`biz_no`")
    private String bizNo;

    /**
     * 动作
     */
    @ApiModelProperty(value = "动作", example = "发货")
    @Column(name = "`action`")
    @NotBlank(message = "动作不能为空")
    @Length(max = 128, message = "动作最长不能为128")
    private String action;

    /**
     * 种类
     */
    @ApiModelProperty(value = "种类", example = "订单管理")
    @Column(name = "`catalog`")
    private String catalog;

    /**
     * 操作人编号
     */
    @ApiModelProperty(value = "操作人编号", example = "ADMI13EBB25728C21000")
    @Column(name = "`operator_id`")
    @NotBlank(message = "操作人编号不能为空")
    @Length(max = 64, message = "操作人编号最长为64")
    private String operatorId;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称", example = "ADMI13EBB25728C21000")
    @Column(name = "`operator_name`")
    @NotBlank(message = "操作人名称不能为空")
    @Length(max = 64, message = "操作人名称最长为64")
    private String operatorName;

    /**
     * 详细信息
     */
    @ApiModelProperty(value = "详细信息", example = "更新了订单ODER140477CEA9821000,更新内容为...")
    @Column(name = "`remark`")
    private String remark;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`recordTime`")
    private Date recordTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`create_time`")
    private Date createTime;
}
