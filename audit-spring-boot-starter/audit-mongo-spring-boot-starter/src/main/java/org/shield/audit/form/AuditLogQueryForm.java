package org.shield.audit.form;

import java.util.Date;

import org.shield.mongo.domain.PageableQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuditLogQueryForm extends PageableQuery {

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
     * 种类
     */
    @ApiModelProperty(value = "种类", example = "订单管理")
    private String catalog;

    /**
     * 操作人编号
     */
    @ApiModelProperty(value = "操作人编号", example = "ADMI13EBB25728C21000")
    private String operatorId;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称", example = "ADMI13EBB25728C21000")
    private String operatorName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", example = "2021-09-26 10:57:24", dataType = "date")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", example = "2021-09-26 10:57:24", dataType = "date")
    private Date endTime;
}
