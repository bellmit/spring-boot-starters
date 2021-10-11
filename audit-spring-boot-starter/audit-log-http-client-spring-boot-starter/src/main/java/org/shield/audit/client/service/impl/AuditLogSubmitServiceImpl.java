package org.shield.audit.client.service.impl;

import java.util.Collections;
import java.util.List;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;

import org.shield.audit.annotation.EnableAuditLog;
import org.shield.audit.client.feign.AuditLogHttpClient;
import org.shield.audit.client.model.AuditLogForm;
import org.shield.audit.util.ServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
@Slf4j
public class AuditLogSubmitServiceImpl implements ILogRecordService, ImportAware {

    private AnnotationAttributes enableAuditLog;

    @Value("${spring.application.name:''}")
    private String applicationName;

    @Autowired
    private AuditLogHttpClient auditLogClient;

    @Override
    public void record(LogRecord logRecord) {
        AuditLogForm form = new AuditLogForm();
        form.setTenant(logRecord.getTenant());
        form.setModule(getModule());
        form.setBizNo(logRecord.getBizNo());
        form.setOperatorId(ServletRequestUtil.getHeader("auth-userId"));
        form.setOperatorName(ServletRequestUtil.getHeader("auth-userName"));
        form.setAction(logRecord.getAction());
        form.setCatalog(logRecord.getCategory());
        form.setRemark(logRecord.getDetail());
        form.setRecordTime(logRecord.getCreateTime());
        auditLogClient.createAuditLogs(form);
    }

    @Override
    public List<LogRecord> queryLog(String bizKey) {
        return Collections.emptyList();
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo) {
        return Collections.emptyList();
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableAuditLog = AnnotationAttributes
                .fromMap(importMetadata.getAnnotationAttributes(EnableAuditLog.class.getName(), false));
        if (this.enableAuditLog == null) {
            log.info("@EnableCaching is not present on importing class");
        }
    }

    private String getModule() {
        if (this.enableAuditLog == null) {
            return applicationName;
        }
        if (StringUtils.hasText(this.enableAuditLog.getString("module"))) {
            return this.enableAuditLog.getString("module");
        }
        return applicationName;
    }
}
