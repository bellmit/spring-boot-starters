package org.shield.admin.controller;

import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;

import org.shield.admin.model.BannerPosition;
import org.shield.admin.service.BannerPositionService;
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
@Api(tags = "广告: 广告位")
@RestController("AdminConsoleBannerPositionController")
@RequestMapping("banner-positions")
public class BannerPositionController {

    @Autowired
    private BannerPositionService service;

    @ApiOperation("创建")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogRecordAnnotation(bizNo = "{{#form.bannerPositionId}}", category = "广告位", detail = "{{#_ret}}", success = "创建", fail = "{{#_errorMsg}}", prefix = "")
    public BannerPosition create(@Valid @RequestBody BannerPosition form) {
        return service.create(form);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{bannerPositionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @LogRecordAnnotation(bizNo = "{{#bannerPositionId}}", category = "广告位", detail = "", success = "删除", fail = "{{#_errorMsg}}", prefix = "")
    public void delete(@PathVariable String bannerPositionId) {
        service.deleteById(bannerPositionId);
    }

    @ApiOperation("查询")
    @GetMapping
    public PageInfo<BannerPosition> list(PageableQuery form) {
        PageMethod.startPage(form);
        return new PageInfo<>(service.list());
    }

    @ApiOperation("更新")
    @PutMapping("/{bannerPositionId}")
    @LogRecordAnnotation(bizNo = "{{#bannerPositionId}}", category = "广告位", detail = "{{#_ret}}", success = "更新", fail = "{{#_errorMsg}}", prefix = "")
    public BannerPosition update(@PathVariable("bannerPositionId") String bannerPositionId,
            @RequestBody BannerPosition form) {
        return service.update(bannerPositionId, form);
    }

    @ApiOperation("详情")
    @GetMapping("/{bannerPositionId}")
    public BannerPosition view(@PathVariable String bannerPositionId) {
        return service.findById(bannerPositionId);
    }
}
