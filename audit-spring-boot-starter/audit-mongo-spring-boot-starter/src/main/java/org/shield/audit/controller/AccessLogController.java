package org.shield.audit.controller;

import javax.validation.Valid;

import org.shield.audit.form.AccessLogQueryForm;
import org.shield.audit.model.AccessLog;
import org.shield.audit.service.AccessLogService;
import org.shield.mongo.domain.PageInfo;
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
@Api(tags = "日志: 访问日志")
@RestController("AuditAccessLogController")
@RequestMapping("logs/access-logs")
public class AccessLogController {

    @Autowired
    private AccessLogService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccessLog create(@Valid @RequestBody AccessLog form) {
        return service.create(form);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<AccessLog> list(AccessLogQueryForm form) {
        return service.list(form);
    }
}
