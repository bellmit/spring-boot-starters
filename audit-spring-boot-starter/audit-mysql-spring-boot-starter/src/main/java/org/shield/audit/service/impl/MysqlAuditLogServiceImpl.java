package org.shield.audit.service.impl;

import java.util.Collections;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.mzt.logapi.beans.LogRecord;

import org.shield.audit.model.AuditLog;
import org.shield.audit.form.AuditLogQueryForm;
import org.shield.audit.mapper.AuditLogMapper;
import org.shield.audit.service.AuditLogService;
import org.shield.audit.util.ServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
public class MysqlAuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Override
    public AuditLog create(AuditLog model) {
        auditLogMapper.insert(model);
        return null;
    }

    @Override
    public PageInfo<AuditLog> list(AuditLogQueryForm form) {
        PageMethod.startPage(form);
        Condition condition = new Condition(AuditLog.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("tenant", form.getTenant());
        criteria.andEqualTo("module", form.getModule());
        criteria.andEqualTo("bizNo", form.getBizNo());
        criteria.andEqualTo("catalog", form.getCatalog());
        criteria.andEqualTo("operatorId", form.getOperatorId());
        criteria.andEqualTo("operatorName", form.getOperatorName());
        if (form.getStartTime() != null && form.getEndTime() != null) {
            criteria.andBetween("recordTime", form.getStartTime(), form.getEndTime());
        }
        List<AuditLog> list = criteria.getAllCriteria().isEmpty() ? auditLogMapper.selectAll()
                : auditLogMapper.selectByCondition(condition);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void record(LogRecord logRecord) {
        AuditLog model = new AuditLog();
        model.setTenant(logRecord.getTenant());
        model.setModule(logRecord.getBizKey());
        model.setBizNo(logRecord.getBizNo());
        model.setOperatorId(ServletRequestUtil.getHeader("userId", logRecord.getOperator()));
        model.setOperatorName(ServletRequestUtil.getHeader("userName", logRecord.getOperator()));
        ;
        model.setAction(logRecord.getAction());
        model.setCatalog(logRecord.getCategory());
        model.setRemark(logRecord.getDetail());
        model.setRecordTime(logRecord.getCreateTime());
        auditLogMapper.insert(model);
    }

    @Override
    public List<LogRecord> queryLog(String bizKey) {
        return Collections.emptyList();
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo) {
        return Collections.emptyList();
    }
}