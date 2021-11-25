package org.shield.admin.service.impl;

import org.shield.admin.enums.AccountAuthSource;
import org.shield.admin.form.PasswordChangeForm;
import org.shield.admin.mapper.AdminAccountAuthMapper;
import org.shield.admin.model.Admin;
import org.shield.admin.model.AdminAccountAuth;
import org.shield.admin.service.AdminService;
import org.shield.admin.service.PasswordService;
import org.shield.rest.exception.BadRequestException;
import org.shield.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    AdminService service;

    @Autowired
    AdminAccountAuthMapper mapper;

    @Override
    public void change(String accountId, PasswordChangeForm form) {
        AdminAccountAuth auth = mapper.findByIdAndSource(AccountAuthSource.USERNAME.getValue(), accountId);
        if (auth == null) {
            throw new NotFoundException("用户不存在");
        }
        Admin admin = service.findById(accountId);
        if (admin == null) {
            throw new NotFoundException("用户不存在");
        }
        if (!admin.getIsActive()) {
            throw new BadRequestException("该用户已停用");
        }
        if (!DigestUtil.bcryptCheck(form.getOldPassword(), auth.getSourceToken())) {
            throw new BadRequestException("原密码错误");
        }
        auth.setSourceToken(DigestUtil.bcrypt(form.getNewPassword()));
        mapper.updateByPrimaryKeySelective(auth);
    }
}
