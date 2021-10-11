package org.shield.admin.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.shield.admin.model.Admin;
import org.shield.admin.enums.AccountAuthSource;
import org.shield.admin.model.AdminAccountAuth;
import org.shield.validation.validator.annotation.CharLength;
import org.shield.validation.validator.annotation.OnCreate;
import org.shield.validation.validator.annotation.OnUpdate;
import org.shield.validation.validator.annotation.Password;
import org.shield.validation.validator.annotation.Phone;
import org.springframework.util.ObjectUtils;
import cn.hutool.crypto.digest.DigestUtil;
import org.shield.mybatis.validator.annotation.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@ApiModel
@Data
@Unique(table = "admin", field = "name", message = "姓名已存在", sn = "accountId", tableSn = "adminId", exceptDeleted = true, groups = {
        OnCreate.class, OnUpdate.class })
@Unique(table = "admin_account_auth", field = "username", tableField = "sourceId", message = "用户名已存在", sn = "accountId", affixCondition = "AND source="
        + AccountAuthSource.USERNAME_VAL, exceptDeleted = true, groups = { OnCreate.class, OnUpdate.class })
@Unique(table = "admin_account_auth", field = "phone", tableField = "sourceId", message = "手机号已存在", sn = "accountId", affixCondition = "AND source="
        + AccountAuthSource.PHONE_VAL, exceptDeleted = true, groups = { OnCreate.class, OnUpdate.class })
public class AdminForm {

    @ApiModelProperty(hidden = true)
    private String accountId;

    @ApiModelProperty(value = "姓名", example = "张明", required = true)
    @CharLength(min = 2, max = 20, message = "姓名介于2-20个字符之间", groups = { OnCreate.class, OnUpdate.class })
    @NotBlank(message = "姓名不能为空", groups = { OnCreate.class, OnUpdate.class })
    private String name;

    @ApiModelProperty(value = "手机号码", example = "18000000001", required = true)
    @NotBlank(message = "手机号码不能为空", groups = { OnCreate.class, OnUpdate.class })
    @Phone(groups = { OnCreate.class, OnUpdate.class })
    private String phone;

    @ApiModelProperty(value = "用户名", example = "zhangming", required = true)
    @CharLength(min = 3, max = 20, message = "用户名介于3-20个字符之间", groups = { OnCreate.class, OnUpdate.class })
    @NotBlank(message = "用户名不能为空", groups = { OnCreate.class, OnUpdate.class })
    private String username;

    @JsonIgnore
    private String sourceId;

    @ApiModelProperty(value = "密码", example = "AChgHeHF")
    @Password(groups = OnCreate.class)
    @Password(failOnEmpty = false, groups = OnUpdate.class)
    private String password;

    @ApiModelProperty(value = "是否有效：1有效 0无效", example = "1", required = true)
    @NotNull(groups = { OnCreate.class, OnUpdate.class })
    private Boolean isActive = true;

    /**
     * 创建 Admin
     *
     * @return
     */
    public Admin toAdmin() {
        Admin model = new Admin();
        model.setName(getName());
        model.setIsActive(getIsActive());
        return model;
    }

    public AdminAccountAuth toUsernameAuth() {
        AdminAccountAuth auth = new AdminAccountAuth();
        auth.setAccountId(getAccountId());
        auth.setSource(AccountAuthSource.USERNAME.value());
        auth.setSourceId(getUsername());
        if (!ObjectUtils.isEmpty(getPassword())) {
            auth.setSourceToken(DigestUtil.bcrypt(getPassword()));
        }
        return auth;
    }

    public AdminAccountAuth toPhoneAuth() {
        AdminAccountAuth auth = new AdminAccountAuth();
        auth.setAccountId(getAccountId());
        auth.setSource(AccountAuthSource.PHONE.value());
        auth.setSourceId(getPhone());
        return auth;
    }

    public String toString() {
        return String.format("AdminForm(accountId=%s, name=%s, phone=%s, username=%s, sourceId=%s, isActive=%b)",
                accountId, name, phone, username, sourceId, isActive);
    }
}
