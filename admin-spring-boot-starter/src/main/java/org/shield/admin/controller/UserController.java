package org.shield.admin.controller;

import java.util.List;
import org.shield.admin.vo.AdminVo;
import org.shield.admin.service.AdminService;
import org.shield.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 查询当前用户信息
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "系统账号: 用户")
@RestController("AdminConsoleUserController")
@RequestMapping("users")
public class UserController {

    @Autowired
    private AdminService service;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("当前用户信息")
    @Authorization("JWT")
    @GetMapping("/me")
    public AdminVo view(@ApiIgnore @RequestHeader("auth-userId") String userId) {
        return service.view(userId);
    }

    @ApiOperation("当前用户的权限")
    @Authorization("JWT")
    @GetMapping("/permissions")
    public List<String> permissions(@ApiIgnore @RequestHeader("auth-userId") String userId) {
        return permissionService.routes(userId);
    }
}
