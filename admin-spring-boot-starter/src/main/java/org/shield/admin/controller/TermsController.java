package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.model.Terms;
import org.shield.admin.service.TermsService;
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
 *
 * 条款
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "通用: 条款")
@RestController("AdminConsoleTermsController")
@RequestMapping("terms")
public class TermsController {

    @Autowired
    private TermsService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "TERMS_{{#form.id}}", category = "条款", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Terms create(@Valid @RequestBody Terms form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "TERMS_{{#id}}", category = "条款", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Terms> list(PageableQuery form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.findAll());
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "TERMS_{{#id}}", category = "字典对照", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public Terms update(@PathVariable("id") Integer id, @RequestBody Terms form) {
        form.setId(id);
        return service.update(form);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Terms view(@PathVariable Integer id) {
        return service.findById(id);
    }
}
