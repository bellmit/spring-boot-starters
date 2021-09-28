package org.shield.audit.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;

import org.shield.audit.model.AuditLog;
import org.shield.audit.repository.AuditLogRepository;
import org.shield.audit.util.ServletRequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
public class MongoAuditLogServiceImpl implements ILogRecordService {

    @Resource
    private AuditLogRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void record(LogRecord logRecord) {
        AuditLog model = new AuditLog();
        model.setTenant(logRecord.getTenant());
        model.setModule(logRecord.getBizKey());
        model.setBizNo(logRecord.getBizNo());
        model.setOperatorId(ServletRequestUtil.getHeader("userId", logRecord.getOperator()));
        model.setOperatorName(ServletRequestUtil.getHeader("userName", logRecord.getOperator()));
        model.setAction(logRecord.getAction());
        model.setCatalog(logRecord.getCategory());
        model.setRemark(logRecord.getDetail());
        model.setRecordTime(logRecord.getCreateTime());
        repository.save(model);
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