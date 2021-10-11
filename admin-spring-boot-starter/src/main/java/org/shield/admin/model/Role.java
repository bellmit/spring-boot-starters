package org.shield.admin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.shield.mybatis.plugin.LogicId;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Table(name = "`role`")
public class Role {

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
    @Column(name = "`role_id`")
    @LogicId(prefix = "ROLE")
    private String roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @Column(name = "`name`")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @Column(name = "`remark`")
    private String remark;

    /**
     * 是否删除: 0未删除 1已删除
     */
    @ApiModelProperty("是否删除: 0未删除 1已删除")
    @Column(name = "`is_deleted`")
    @LogicDelete
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
