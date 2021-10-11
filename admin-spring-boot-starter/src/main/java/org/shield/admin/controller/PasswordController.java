package org.shield.admin.controller;

import javax.validation.Valid;

import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.form.PasswordChangeForm;
import org.shield.admin.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Api(tags = "密码管理")
@RestController("AdminConsolePasswordController")
@RequestMapping("admins/passwords")
public class PasswordController {

    @Autowired
    private PasswordService service;

    @ApiOperation("修改密码")
    @PutMapping("/change")
    @LogRecordAnnotation(bizNo = "{{#userId}}", category = "管理员密码", detail = "", success = "修改密码", fail = "{{#_errorMsg}}", prefix = "")
    public void change(@RequestHeader("auth-userId") String userId, @Valid @RequestBody PasswordChangeForm form) {
        service.change(userId, form);
    }

    @ApiOperation("重置密码")
    public void reset() {

    }
}
