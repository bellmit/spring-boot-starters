package org.shield.audit.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import org.shield.audit.form.LoginLogQueryForm;
import org.shield.audit.mapper.LoginLogMapper;
import org.shield.audit.model.LoginLog;
import org.shield.audit.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapoper;

    @Override
    public PageInfo<LoginLog> list(LoginLogQueryForm form) {

        PageMethod.startPage(form);
        Condition condition = new Condition(LoginLog.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("tenant", form.getTenant());
        criteria.andEqualTo("ip", form.getIp());
        criteria.andEqualTo("username", form.getUsername());
        if (form.getStartTime() != null && form.getEndTime() != null) {
            criteria.andBetween("recordTime", form.getStartTime(), form.getEndTime());
        }
        List<LoginLog> list = criteria.getAllCriteria().isEmpty() ? loginLogMapoper.selectAll()
                : loginLogMapoper.selectByCondition(condition);
        return new PageInfo<>(list);
    }
}