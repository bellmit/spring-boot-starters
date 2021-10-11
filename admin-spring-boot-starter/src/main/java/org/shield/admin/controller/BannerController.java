package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.form.BannerQueryForm;
import org.shield.admin.model.Banner;
import org.shield.admin.service.BannerService;
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
@RestController("AdminConsoleAdController")
@RequestMapping("banner-positions/{bannerPositionId}/banners")
public class BannerController {

    @Autowired
    private BannerService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "BANNER_{{#form.id}}", category = "广告", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public Banner create(@ApiParam("广告位编号") @PathVariable("bannerPositionId") String bannerPositionId,
            @Valid @RequestBody Banner form) {
        form.setBannerPositionId(bannerPositionId);
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "BANNER_{{#id}}", category = "广告", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<Banner> list(@ApiParam("广告位编号") @PathVariable("bannerPositionId") String bannerPositionId,
            BannerQueryForm form) {
        PageMethod.startPage(form);
        form.setBannerPositionId(bannerPositionId);
        return new PageInfo<>(service.list(form));
    }

    @ApiOperation("更新")
    @PutMapping("/{id}")
    @LogRecordAnnotation(bizNo = "BANNER_{{#id}}", category = "广告", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public Banner update(@PathVariable("id") Integer id, @RequestBody Banner form) {
        form.setId(id);
        return service.update(form);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    public Banner view(@PathVariable Integer id) {
        return service.findById(id);
    }
}
