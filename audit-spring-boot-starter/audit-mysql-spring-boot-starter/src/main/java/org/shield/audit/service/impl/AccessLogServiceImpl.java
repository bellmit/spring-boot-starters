package org.shield.audit.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import org.shield.audit.form.AccessLogQueryForm;
import org.shield.audit.mapper.AccessLogMapper;
import org.shield.audit.model.AccessLog;
import org.shield.audit.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogMapper accessLogMapoper;

    @Override
    public AccessLog create(AccessLog model) {
        accessLogMapoper.insertSelective(model);
        return model;
    }

    @Override
    public PageInfo<AccessLog> list(AccessLogQueryForm form) {

        PageMethod.startPage(form);
        Condition condition = new Condition(AccessLog.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("tenant", form.getTenant());
        criteria.andEqualTo("ip", form.getIp());
        criteria.andEqualTo("operatorId", form.getOperatorId());
        criteria.andEqualTo("operatorName", form.getOperatorName());
        if (form.getStartTime() != null && form.getEndTime() != null) {
            criteria.andBetween("recordTime", form.getStartTime(), form.getEndTime());
        }
        List<AccessLog> list = criteria.getAllCriteria().isEmpty() ? accessLogMapoper.selectAll()
                : accessLogMapoper.selectByCondition(condition);
        return new PageInfo<>(list);
    }
}