package org.shield.admin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.shield.admin.enums.AccountAuthSource;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel("管理员账号认证信息")
@Table(name = "`admin_account_auth`")
public class AdminAccountAuth {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    private Integer id;

    /**
     * 账号编号
     */
    @ApiModelProperty("账号编号")
    @Column(name = "`account_id`")
    @NotBlank(message = "账号编号不能为空")
    @JsonIgnore
    private String accountId;

    /**
     * 认证方式: 0用户名 10手机号 20邮箱 30微信 40QQ 50Apple
     */
    @ApiModelProperty(value = "登录方式: 0用户名 10手机号 20邮箱 30微信 40QQ 50Apple", example = "0", allowableValues = "range[0,60]")
    @Column(name = "`source`")
    @NotNull(message = "登录方式不能为空")
    private Integer source;

    /**
     * 认证唯一标识
     */
    @ApiModelProperty("认证唯一标识")
    @Column(name = "`source_id`")
    @NotBlank(message = "认证唯一标识不能为空")
    private String sourceId;

    /**
     * 认证凭证: 密码或令牌
     */
    @ApiModelProperty("认证凭证: 密码或令牌")
    @Column(name = "`source_token`")
    @JsonIgnore
    private String sourceToken;

    /**
     * 是否有效：0禁用 1启用
     */
    @ApiModelProperty("是否有效：0禁用 1启用")
    @Column(name = "`is_active`")
    @NotNull(message = "是否有效：0禁用 1启用不能为空")
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
    @ApiModelProperty(value = "创建时间", example = "2020-10-18 03:20:20", dataType = "date")
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2020-10-18 03:20:20", dataType = "date")
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 判断当前授权方式是否为手机号认证
     *
     * @return
     */
    public boolean isPhone() {
        return AccountAuthSource.PHONE.getValue().equals(source);
    }

    /**
     * 判断当前授权方式是否为用户名认证
     *
     * @return
     */
    public boolean isUsername() {
        return AccountAuthSource.USERNAME.getValue().equals(source);
    }
}
