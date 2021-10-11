package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.model.Config;
import org.shield.admin.service.ConfigService;
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
 * 配置
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "配置")
@RestController("AdminConsoleConfigController")
@RequestMapping("configs")
public class ConfigController {

    @Autowired
    private ConfigService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "CONFIG_{{#form.id}}", category = "配置", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Config create(@Valid @RequestBody Config form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "CONFIG_{{#id}}", category = "配置", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Config> list(PageableQuery form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.list());
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "CONFIG_{{#id}}", category = "配置", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public Config update(@PathVariable("id") Integer id, @RequestBody Config form) {
        form.setId(id);
        service.update(form);
        return form;
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Config view(@PathVariable Integer id) {
        return service.findById(id);
    }
}