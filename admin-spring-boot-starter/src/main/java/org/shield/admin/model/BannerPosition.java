package org.shield.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.shield.mybatis.plugin.LogicId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@ApiModel
@Data
@Table(name = "`banner_position`")
public class BannerPosition {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "ID", hidden = true)
    @Column(name = "`id`")
    private Integer id;

    /**
     * 广告位编号
     */
    @ApiModelProperty(value = "广告位编号", example = "app-home-slides", required = true)
    @Column(name = "`banner_position_id`")
    @LogicId(prefix = "BANP")
    private String bannerPositionId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "首页banner", required = true)
    @Column(name = "`name`")
    private String name;

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
