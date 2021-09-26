package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import org.shield.admin.model.AdPosition;
import org.shield.admin.service.AdPositionService;
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
 * 广告位
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "广告位")
@RestController
@RequestMapping("ad-positions")
public class AdPositionController {

    @Autowired
    private AdPositionService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AdPosition create(@Valid @RequestBody AdPosition form) {
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
    public PageInfo<AdPosition> list(PageableQuery form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.list());
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    public AdPosition update(@PathVariable("id") Integer id, @RequestBody AdPosition form) {
        form.setId(id);
        return service.update(form);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public AdPosition view(@PathVariable Integer id) {
        return service.findById(id);
    }
}
