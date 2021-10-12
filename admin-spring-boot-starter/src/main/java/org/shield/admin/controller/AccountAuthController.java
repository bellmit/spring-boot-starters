package org.shield.admin.controller;

import java.util.List;
import org.shield.admin.model.AdminAccountAuth;
import org.shield.admin.service.AdminAccountAuthService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 管理员账号登录方式
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "系统账号: 管理员账号登录方式")
@RestController("AdminConsoleAccountAuthController")
@RequestMapping("admins/{adminId}/accountAuths")
public class AccountAuthController {

    @Autowired
    private AdminAccountAuthService service;

    @ApiOperation("查询")
    @GetMapping
    public List<AdminAccountAuth> list(@PathVariable("adminId") String checkerId) {
        Condition condition = new Condition(AdminAccountAuth.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("accountId", checkerId);
        return service.findByCondition(condition);
    }
}
