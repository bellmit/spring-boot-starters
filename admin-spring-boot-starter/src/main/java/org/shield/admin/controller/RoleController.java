package org.shield.admin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.model.Role;
import org.shield.admin.service.RoleService;
import org.shield.mybatis.form.PageableQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "角色")
@RestController("AdminConsoleRoleController")
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "{{#form.id}}", category = "角色", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Role create(@RequestBody Role form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "{{#id}}", category = "角色", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Role> list(PageableQuery form) {
        Page<Role> page = PageMethod.startPage(form);
        service.findAll();
        return PageInfo.of(page);
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "{{#id}}", category = "角色", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public Role update(@PathVariable String id, @RequestBody Role role) {
        return service.update(id, role);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Role view(@PathVariable String id) {
        return service.findById(id);
    }
}