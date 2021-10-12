package org.shield.admin.controller;

import java.util.List;
import javax.validation.Valid;
import org.shield.admin.model.Permission;
import org.shield.mybatis.form.PageableQuery;
import org.shield.admin.service.PermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "权限: 权限")
@RestController("AdminConsolePermissionController")
@RequestMapping("permissions")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "{{#form.id}}", category = "权限", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Permission create(@Valid @RequestBody Permission form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "{{#id}}", category = "权限", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public List<Permission> list() {
        return service.list();
    }

    @ApiOperation("分页查询")
    @GetMapping
    public PageInfo<Permission> list(PageableQuery form) {
        Page<Permission> page = PageMethod.startPage(form);
        service.findAll();
        return PageInfo.of(page);
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "{{#id}}", category = "权限", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public Permission update(@PathVariable String id, @RequestBody Permission permission) {
        return service.update(id, permission);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Permission view(@PathVariable String id) {
        return service.findById(id);
    }
}