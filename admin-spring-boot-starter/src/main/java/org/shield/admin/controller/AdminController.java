package org.shield.admin.controller;

import java.util.List;
import org.shield.admin.model.Admin;
import org.shield.admin.form.AdminForm;
import org.shield.admin.form.AdminQueryForm;

import org.shield.admin.service.AdminService;
import org.shield.admin.service.PermissionService;
import com.github.pagehelper.PageInfo;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.validation.validator.annotation.OnCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理员
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "系统账号: 管理员")
@RestController("AdminConsoleConsoleAdminController")
@RequestMapping("admins")
public class AdminController {

    @Autowired
    private AdminService service;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "{{#_ret.adminId}}", category = "管理员", detail = "{{#form}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Admin create(@Validated(OnCreate.class) @RequestBody AdminForm form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "{{#id}}", category = "管理员", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Admin> list(AdminQueryForm form) {
        return new PageInfo<>(service.list(form));
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "{{#id}}", category = "管理员", detail = "{{#form}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public AdminForm update(@PathVariable("id") String id, @RequestBody AdminForm form) {
        form.setAccountId(id);
        service.update(form);
        return form;
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Admin view(@PathVariable String id) {
        return service.findById(id);
    }

    @ApiOperation("管理员的路由权限")
    @Deprecated
    @GetMapping("/{id}/routes")
    public List<String> routes(@PathVariable("id") String id) {
        return permissionService.routes(id);
    }
}
