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
@Table(name = "`ad`")
public class Ad {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 广告位编号
     */
    @ApiModelProperty(value = "广告位编号", example = "1", required = true)
    @Column(name = "`position_id`")
    private Integer positionId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "国庆节快乐", required = true)
    @Column(name = "`name`")
    private Integer name;

    /**
     * 类别: 0图片 1文本 2视频
     */
    @ApiModelProperty(value = "类别: 0图片 1文本 2视频", example = "1", required = true)
    @Column(name = "`catalog`")
    private Integer catalog;

    /**
     * 广告内容
     */
    @ApiModelProperty(value = "广告内容", example = "http://ad.com/ad.png", required = false)
    @Column(name = "`content`")
    @NotNull(message = "广告内容不能为空")
    private String content;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", example = "https://apps.apple.com/us/app/%E4%B9%89%E4%B9%8C%E5%A5%BD%E8%BF%90%E5%8F%B8%E6%9C%BA%E7%AB%AF/id1501142681", required = true)
    @Column(name = "`url`")
    private String url;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", example = "1", required = true)
    @Column(name = "`sort`")
    @NotNull(message = "排序不能为空")
    private Byte sort;

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
