package org.shield.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@ApiModel
@Data
@Table(name = "`app_release`")
public class AppRelease {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 版本名称
     */
    @ApiModelProperty(value = "版本名称", example = "1.0.0", required = true)
    @Column(name = "`version`")
    @NotBlank(message = "版本名称不能为空")
    private String version;

    /**
     * 版本编号
     */
    @ApiModelProperty(value = "版本编号", example = "100", required = true)
    @Column(name = "`version_num`")
    @NotNull(message = "版本编号不能为空")
    private Integer versionNum;

    /**
     * 类别: 1安卓 2iOS
     */
    @ApiModelProperty(value = "类别: 1安卓 2iOS", example = "1", required = true)
    @Column(name = "`catalog`")
    @NotNull(message = "类别不能为空")
    private Integer catalog;

    /**
     * 是否强制升级: 0否 1是
     */
    @ApiModelProperty(value = "是否强制升级: 0否 1是", example = "0", required = false)
    @Column(name = "`force_upgrade`")
    @NotNull(message = "是否强制升级不能为空")
    private Boolean forceUpgrade;

    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址", example = "https://apps.apple.com/us/app/%E4%B9%89%E4%B9%8C%E5%A5%BD%E8%BF%90%E5%8F%B8%E6%9C%BA%E7%AB%AF/id1501142681", required = true)
    @Column(name = "`url`")
    @NotBlank(message = "下载地址不能为空")
    private String url;

    /**
     * 文件hash
     */
    @ApiModelProperty(value = "文件hash", example = "098f6bcd4621d373cade4e832627b4f6", required = false)
    @Column(name = "`hash`")
    private String hash;

    /**
     * 发布说明
     */
    @ApiModelProperty(value = "发布说明", example = "1.新增认证图片预览", required = true)
    @Column(name = "`description`")
    @NotBlank(message = "发布说明不能为空")
    private String description;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty(value = "是否有效：1有效 0无效", example = "1", required = true)
    @Column(name = "`is_active`")
    @NotNull(message = "是否有效不能为空")
    private Boolean isActive;

    /**
     * 是否删除: 0未删除 1已删除
     */
    @ApiModelProperty(value = "是否删除: 0未删除 1已删除", hidden = true)
    @Column(name = "`is_deleted`")
    @LogicDelete
    @JsonIgnore
    private Boolean isDeleted;

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
