package org.shield.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.shield.mybatis.plugin.LogicId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel("条款")
@Table(name = "terms")
public class Terms {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 条款编号
     */
    @ApiModelProperty(value = "条款编号", example = "privacy")
    @Column(name = "`terms_id`")
    @LogicId(prefix = "TERM")
    @NotBlank(message = "条款编号不能为空")
    private String termsId;

    /**
     * 条款名称
     */
    @ApiModelProperty(value = "条款名称", example = "隐私协议")
    @Column(name = "`name`")
    @NotBlank(message = "条款名称不能为空")
    private String name;

    /**
     * 条款内容
     */
    @ApiModelProperty(value = "条款内容", example = "隐私政策")
    @Column(name = "`content`")
    @NotBlank(message = "条款内容不能为空")
    private String content;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty(value = "是否有效：true有效 false无效", example = "true")
    @Column(name = "`is_active`")
    private Boolean isActive;

    /**
     * 是否删除: 0未删除 1已删除
     */
    @ApiModelProperty("是否删除: 0未删除 1已删除")
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
