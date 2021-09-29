package org.shield.audit.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;

import org.shield.audit.common.model.AuditLog;
import org.shield.audit.form.AuditLogQueryForm;
import org.shield.audit.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * 操作日志
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "操作日志")
@RestController("AuditLogController")
@RequestMapping("logs/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AuditLog create(@Valid @RequestBody AuditLog form) {
        return service.create(form);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<AuditLog> list(AuditLogQueryForm form) {
        return service.list(form);
    }
}
