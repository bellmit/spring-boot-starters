package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import org.shield.admin.form.AppReleaseQueryForm;
import org.shield.admin.model.AppRelease;
import org.shield.admin.service.AppReleaseService;
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
 * 版本发布
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "版本发布")
@RestController("AdminConsoleAppReleaseController")
@RequestMapping("app-releases")
public class AppReleaseController {

    @Autowired
    private AppReleaseService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AppRelease create(@Valid @RequestBody AppRelease model) {
        return service.create(model);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<AppRelease> list(AppReleaseQueryForm form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.list(form));
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    public AppRelease update(@PathVariable("id") Integer id, @RequestBody AppRelease form) {
        form.setId(id);
        service.update(form);
        return form;
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public AppRelease view(@PathVariable Integer id) {
        return service.findById(id);
    }
}