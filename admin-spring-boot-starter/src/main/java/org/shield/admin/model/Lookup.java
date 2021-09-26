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
@Table(name = "`lookup`")
public class Lookup {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", example = "SimProviderType", required = true)
    @Column(name = "`catalog`")
    @NotBlank(message = "类型不能为空")
    private String catalog;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "中国移动", required = true)
    @Column(name = "`name`")
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 值
     */
    @ApiModelProperty(value = "值", example = "1", required = true)
    @Column(name = "`code`")
    @NotNull(message = "值不能为空")
    private Integer code;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", required = true)
    @Column(name = "`sort`")
    @NotNull(message = "排序不能为空")
    private Byte sort;

    /**
     * 发布说明
     */
    @ApiModelProperty(value = "发布说明", example = "1.新增认证图片预览", required = true)
    @Column(name = "`remark`")
    @NotBlank(message = "发布说明不能为空")
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
