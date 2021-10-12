package org.shield.audit.controller;

import com.github.pagehelper.PageInfo;

import org.shield.audit.form.LoginLogQueryForm;
import org.shield.audit.model.LoginLog;
import org.shield.audit.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * 登录日志
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "日志: 登录日志")
@RestController("AuditLoginLogController")
@RequestMapping("logs/login-logs")
public class LoginLogController {

    @Autowired
    private LoginLogService service;

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<LoginLog> list(LoginLogQueryForm form) {
        return service.list(form);
    }
}
