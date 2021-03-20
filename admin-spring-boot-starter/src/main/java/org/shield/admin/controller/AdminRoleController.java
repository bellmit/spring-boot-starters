package org.shield.admin.controller;

import java.util.List;
import javax.validation.Valid;
import org.shield.admin.form.AdminRoleForm;
import org.shield.admin.service.AdminRoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 角色的权限
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Api(tags = "管理员的角色")
@RestController("AdminConsoleAdminRoleController")
@RequestMapping("admins/{adminId}/roles")
public class AdminRoleController {

    @Autowired
    private AdminRoleService service;

    @ApiOperation("查询")
    @GetMapping
    public List<String> list(@PathVariable("adminId") String adminId) {
        return service.list(adminId);
    }

    @ApiOperation("更新")
    @PutMapping
    public List<String> update(@PathVariable("adminId") String adminId, @Valid @RequestBody AdminRoleForm form) {
        return service.update(adminId, form);
    }
}
