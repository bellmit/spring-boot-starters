package org.shield.admin.controller;

import org.shield.admin.enums.AppReleaseCatalog;
import org.shield.admin.mapper.AppReleaseMapper;
import org.shield.admin.model.AppRelease;
import org.shield.validation.validator.annotation.ValidEnumDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 版本查询
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "App: 版本查询")
@RestController("AppAppReleaseController")
@RequestMapping("app/app-releases")
@Validated
public class AppAppReleaseController {

    @Autowired
    private AppReleaseMapper appReleaseMapper;

    @ApiOperation("获取App最新版本")
    @GetMapping("/{catalog}/latest")
    public AppRelease getLatestVersion(@ApiParam(value="平台: android/ios", example = "android") @PathVariable @ValidEnumDescription(AppReleaseCatalog.class) String catalog) {
        return appReleaseMapper.findLatestByCatalog(AppReleaseCatalog.descriptionOf(catalog).getValue());
    }
}