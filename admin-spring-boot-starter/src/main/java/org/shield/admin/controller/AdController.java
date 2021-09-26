package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import org.shield.admin.form.AdQueryForm;
import org.shield.admin.model.Ad;
import org.shield.admin.service.AdService;
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
import io.swagger.annotations.ApiParam;

/**
 *
 * 广告位
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "广告")
@RestController
@RequestMapping("ad-positions/{positionId}/ads")
public class AdController {

    @Autowired
    private AdService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Ad create(@ApiParam("广告位编号") @PathVariable("positionId") Integer positionId, @Valid @RequestBody Ad form) {
        form.setPositionId(positionId);
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Ad> list(AdQueryForm form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.list(form));
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    public Ad update(@PathVariable("id") Integer id, @RequestBody Ad form) {
        form.setId(id);
        return service.update(form);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Ad view(@PathVariable Integer id) {
        return service.findById(id);
    }
}
