package org.shield.audit.client.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
public class AuditLogForm {

    /**
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识", example = "org.shield.admin")
    private String tenant;

    /**
     * 业务模块
     */
    @ApiModelProperty(value = "业务模块", example = "ORDER")
    private String module;

    /**
     * 业务编号
     */
    @ApiModelProperty(value = "业务编号", example = "ODER140477CEA9821000")
    private String bizNo;

    /**
     * 动作
     */
    @ApiModelProperty(value = "动作", example = "发货")
    @NotBlank(message = "动作不能为空")
    @Length(max = 128, message = "动作最长不能为128")
    private String action;

    /**
     * 种类
     */
    @ApiModelProperty(value = "种类", example = "订单管理")
    private String catalog;

    /**
     * 操作人编号
     */
    @ApiModelProperty(value = "操作人编号", example = "ADMI13EBB25728C21000")
    @NotBlank(message = "操作人编号不能为空")
    @Length(max = 64, message = "操作人编号最长为64")
    private String operatorId;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称", example = "管理员")
    @NotBlank(message = "操作人名称不能为空")
    @Length(max = 64, message = "操作人名称最长为64")
    private String operatorName;

    /**
     * 详细信息
     */
    @ApiModelProperty(value = "详细信息", example = "更新了订单ODER140477CEA9821000,更新内容为...")
    private String remark;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间", example = "2021-09-26 10:57:24", dataType = "date")
    private Date recordTime;
}
