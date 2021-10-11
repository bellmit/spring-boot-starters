package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.form.LookupQueryForm;
import org.shield.admin.model.Lookup;
import org.shield.admin.service.LookupService;
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
 * 字典对照
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "字典对照")
@RestController("AdminConsoleLookupController")
@RequestMapping("lookups")
public class LookupController {

    @Autowired
    private LookupService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "LOOKUP_{{#form.id}}", category = "字典对照", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Lookup create(@Valid @RequestBody Lookup form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "LOOKUP_{{#id}}", category = "字典对照", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Lookup> list(LookupQueryForm form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.list(form));
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "LOOKUP_{{#id}}", category = "字典对照", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public Lookup update(@PathVariable("id") Integer id, @RequestBody Lookup form) {
        form.setId(id);
        return service.update(form);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Lookup view(@PathVariable Integer id) {
        return service.findById(id);
    }
}
