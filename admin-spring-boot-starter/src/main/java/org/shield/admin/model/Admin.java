package org.shield.admin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.shield.mybatis.plugin.LogicId;
import org.shield.security.user.User;
import org.shield.security.user.impl.AdminUser;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel("管理员")
@Table(name = "admin")
public class Admin {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    @JsonIgnore
    private Long id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    @Column(name = "`admin_id`")
    @LogicId(prefix = "ADMI")
    private String adminId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @Column(name = "`name`")
    private String name;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @Transient
    private String username;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @Transient
    private String phone;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty("是否有效：1有效 0无效")
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
    @ApiModelProperty("创建时间")
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @Column(name = "`update_time`")
    private Date updateTime;

    public User toSecurityUser(){
        User appUser = new AdminUser();
        appUser.setName(getName());
        appUser.setUserId(getAdminId());
        appUser.setUid(Long.valueOf(getId()));
        return appUser;
    }
}
