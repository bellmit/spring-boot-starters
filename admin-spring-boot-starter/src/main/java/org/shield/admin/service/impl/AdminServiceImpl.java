
package org.shield.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.shield.admin.model.Admin;
import org.shield.admin.enums.AccountAuthSource;
import org.shield.admin.form.AdminForm;
import org.shield.admin.form.AdminQueryForm;
import org.shield.admin.mapper.AdminAccountAuthMapper;
import org.shield.admin.model.AdminAccountAuth;
import org.shield.admin.repository.AccountAuthRepository;
import org.shield.admin.vo.AdminVo;
import com.github.pagehelper.page.PageMethod;
import org.shield.admin.service.AdminAccountAuthService;
import org.shield.admin.service.AdminService;
import org.shield.crud.service.AbstractService;
import org.shield.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 管理员
 *
 * @author zacksleo@gmail.com
 */
@Service
public class AdminServiceImpl extends AbstractService<Admin> implements AdminService {

    @Autowired
    AdminAccountAuthService authService;

    @Autowired
    AdminAccountAuthMapper authMapper;

    @Value("${password.expiredDays:90}")
    private int passwordExpiredDays;

    @Autowired
    private AccountAuthRepository authRepository;

    /**
     * 创建管理员
     *
     * @param form
     * @return
     */
    public Admin create(AdminForm form) {
        Admin model = create(form.toAdmin());
        form.setAccountId(model.getAdminId());
        authService.create(form.toUsernameAuth());
        authService.create(form.toPhoneAuth());
        return model;
    }

    /**
     * 更新
     *
     * @param form
     * @return
     */
    @Override
    public AdminForm update(AdminForm form) {
        String adminId = form.getAccountId();
        update(adminId, form.toAdmin());
        updateAuth(form.getAccountId(), AccountAuthSource.USERNAME.getValue(), form.toUsernameAuth());
        updateAuth(form.getAccountId(), AccountAuthSource.PHONE.getValue(), form.toPhoneAuth());
        return form;
    }

    private void updateAuth(String accountId, Integer source, AdminAccountAuth auth) {
        Condition condition = new Condition(AdminAccountAuth.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("source", source);
        criteria.andEqualTo("accountId", accountId);
        authMapper.updateByConditionSelective(auth, condition);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        super.deleteById(id);
        Condition condition = new Condition(AdminAccountAuth.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("accountId", id);
        authMapper.deleteByCondition(condition);
    }

    @Override
    public List<Admin> list(AdminQueryForm form) {
        Condition condition = new Condition(Admin.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("adminId", form.getAdminId());
        criteria.andEqualTo("name", form.getName());
        criteria.andEqualTo("isActive", form.getIsActive());
        if (!ObjectUtils.isEmpty(form.getUsername()) || !ObjectUtils.isEmpty(form.getPhone())) {
            Set<String> accounts = authRepository.findAccounsByUsernamePhone(form.getUsername(), form.getPhone());
            if (accounts.isEmpty()) {
                return new ArrayList<Admin>();
            }
            criteria.andIn("adminId", accounts);
        }
        PageMethod.startPage(form);
        List<Admin> list = criteria.getAllCriteria().size() < 1 ? findAll() : findByCondition(condition);
        List<AdminAccountAuth> auths =
                authRepository.findAuthsByAccounts(list.stream().map(Admin::getAdminId).collect(Collectors.toSet()));
        for (Admin admin : list) {
            auths.stream().filter(e -> e.getAccountId().equals(admin.getAdminId())).forEach(auth -> {
                if (auth.isPhone()) {
                    admin.setPhone(auth.getSourceId());
                } else if (auth.isUsername()) {
                    admin.setUsername(auth.getSourceId());
                }
            });
        }
        return list;
    }

    @Override
    public AdminVo view(String id) {
        Admin admin = findById(id);
        if (admin == null) {
            throw new NotFoundException("用户不存在");
        }
        AdminVo vo = new AdminVo(admin);
        List<AdminAccountAuth> auths = authRepository.findAuthsByAccounts(Collections.singleton(id));
        for (AdminAccountAuth auth : auths) {
            if (auth.isPhone()) {
                vo.setPhone(auth.getSourceId());
            } else if (auth.isUsername()) {
                vo.setUsername(auth.getSourceId());
                vo.setPasswordExpired(
                        DateUtil.between(auth.getUpdateTime(), new Date(), DateUnit.DAY) > passwordExpiredDays);
            }
        }
        return vo;
    }
}
