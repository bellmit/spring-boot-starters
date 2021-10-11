package org.shield.admin.vo;

import org.shield.admin.model.Admin;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class AdminVo extends Admin {

    /**
     * 密码是否过期
     */
    @ApiModelProperty(value = "密码是否过期", example = "false")
    private boolean passwordExpired;

    public AdminVo() {
    }

    public AdminVo(Admin admin) {
        BeanUtils.copyProperties(admin, this);
    }
}
