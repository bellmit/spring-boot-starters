package org.shield.admin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.shield.mybatis.plugin.LogicId;
import org.shield.validation.validator.annotation.ValidRegex;

import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Table(name = "`permission`")
public class Permission {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    @JsonIgnore
    private Integer id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    @Column(name = "`permission_id`")
    @LogicId(prefix = "PERM")
    private String permissionId;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", example = "ANY")
    @Column(name = "`http_method`")
    @NotBlank(message = "请求方式不能为空")
    @Pattern(regexp = "(GET)|(POST)|(PUT)|(DELETE)|(PATCH)|(ANY)", message = "请求方式不正确")
    private String httpMethod;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    @Column(name = "`name`")
    @NotBlank(message = "权限名称不能为空")
    private String name;

    /**
     * 路由表达式
     */
    @ApiModelProperty(value = "路由表达式", example = "/iot/admin/.*")
    @Column(name = "`uri_regex`")
    @NotBlank(message = "路由表达式不能为空")
    @ValidRegex(message = "正则表达式不合法")
    private String uriRegex;

    /**
     * 父级
     */
    @ApiModelProperty(value = "父级编号, 使用 ROOT 表示根权限", example = "ROOT")
    @Column(name = "`parent_id`")
    @NotNull(message = "父级不能为空")
    private String parentId;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    @Column(name = "`sort`")
    @NotNull(message = "序号不能为空")
    private Short sort;

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
    @ApiModelProperty("创建时间")
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 子权限列表
     */
    @Transient
    private List<Permission> children;
}
