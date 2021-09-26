package org.shield.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@ApiModel
@Data
@Table(name = "`ad_position`")
public class AdPosition {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "首页banner", required = true)
    @Column(name = "`name`")
    private String name;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识", example = "home-banner", required = true)
    @Column(name = "`slug`")
    private String slug;

    /**
     * 尺寸
     */
    @ApiModelProperty(value = "广告内容", example = "750*200", required = false)
    @Column(name = "`size`")
    @NotNull(message = "尺寸")
    private String size;

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
