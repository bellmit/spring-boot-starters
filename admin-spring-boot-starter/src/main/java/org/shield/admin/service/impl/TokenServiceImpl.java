package org.shield.admin.service.impl;

import org.shield.admin.model.Admin;
import org.shield.admin.model.AdminAccountAuth;
import org.shield.admin.enums.AccountAuthSource;
import org.shield.admin.form.PasswordLoginForm;
import org.shield.admin.mapper.AdminAccountAuthMapper;
import org.shield.admin.service.AdminService;
import org.shield.admin.service.PermissionService;
import org.shield.admin.service.TokenService;
import org.shield.admin.vo.TokenVo;
import org.shield.rest.exception.BadRequestException;
import org.shield.rest.exception.NotFoundException;
import org.shield.security.user.User;
import org.shield.security.user.impl.AdminUser;
import org.shield.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zacksleo@gmail.com
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService<PasswordLoginForm> {

    @Autowired
    AdminService service;

    @Autowired
    AdminAccountAuthMapper mapper;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    private PermissionService permissionService;

    public TokenVo create(PasswordLoginForm form) throws Exception {
        AdminAccountAuth auth = mapper.findAuth(AccountAuthSource.USERNAME.value(), form.getUsername());
        if (auth == null) {
            throw new NotFoundException("用户不存在");
        }
        Admin admin = service.findById(auth.getAccountId());
        if (admin == null) {
            throw new NotFoundException("用户不存在");
        }
        if (!admin.getIsActive()) {
            throw new BadRequestException("该用户已停用");
        }
        log.info("sourceToken={}", auth);
        log.info("sourceToken={}", auth.getSourceToken());
        if (!DigestUtil.bcryptCheck(form.getPassword(), auth.getSourceToken())) {
            throw new BadRequestException("用户名或密码错误");
        }
        User appUser = admin.toSecurityUser();
        appUser.setUsername(form.getUsername());
        TokenVo vo = new TokenVo();
        vo.setToken(jwtTokenUtils.createToken(appUser));
        vo.setName(admin.getName());
        vo.setPremissions(permissionService.routes(admin.getAdminId()));
        return vo;
    }
}
