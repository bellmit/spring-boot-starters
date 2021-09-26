package org.shield.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@ApiModel
@Data
@Table(name = "`config`")
public class Config {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别", example = "Line", required = true)
    @Column(name = "`catalog`")
    @NotNull(message = "类别不能为空")
    private String catalog;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "CvrpConfig", required = true)
    @Column(name = "`name`")
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 广告内容
     */
    @ApiModelProperty(value = "广告内容", example = "{\"saveNumber\":20,\"times\":20,\"numberAnt\":100,\"capacity\":13.6}", required = false)
    @Column(name = "`content`")
    @NotNull(message = "广告内容不能为空")
    private String content;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "提现服务描述文案", required = true)
    @Column(name = "`remark`")
    @NotBlank(message = "备注")
    private String remark;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty(value = "是否有效：1有效 0无效", example = "1", required = true)
    @Column(name = "`is_active`")
    @NotNull(message = "是否有效不能为空")
    private Boolean isActive;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`update_time`")
    private Date updateTime;
}
