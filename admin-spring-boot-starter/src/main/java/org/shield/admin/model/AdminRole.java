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
@Table(name = "`admin_role`")
public class AdminRole {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Long id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    @Column(name = "`admin_role_id`")
    @LogicId(prefix = "ARID")
    private String adminRoleId;

    /**
     * 管理员编号
     */
    @ApiModelProperty("管理员编号")
    @Column(name = "`admin_id`")
    private String adminId;

    /**
     * 角色编号
     */
    @ApiModelProperty("角色编号")
    @Column(name = "`role_id`")
    private String roleId;

    /**
     * 是否删除: 0未删除 1已删除
     */
    @ApiModelProperty("是否删除: 0未删除 1已删除")
    @Column(name = "`is_deleted`")
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
