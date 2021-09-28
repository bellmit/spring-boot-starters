package org.shield.admin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import org.shield.mybatis.plugin.LogicId;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Table(name = "`role_permission`")
public class RolePermission {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    @Column(name = "`role_permission_id`")
    @LogicId(prefix = "RPID")
    private String rolePermissionId;

    /**
     * 角色编号
     */
    @ApiModelProperty("角色编号")
    @Column(name = "`role_id`")
    private String roleId;

    /**
     * 权限编号
     */
    @ApiModelProperty("权限编号")
    @Column(name = "`permission_id`")
    private String permissionId;

    /**
     * 是否删除: 0未删除 1已删除
     */
    @ApiModelProperty("是否删除: 0未删除 1已删除")
    @Column(name = "`is_deleted`")
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
}
