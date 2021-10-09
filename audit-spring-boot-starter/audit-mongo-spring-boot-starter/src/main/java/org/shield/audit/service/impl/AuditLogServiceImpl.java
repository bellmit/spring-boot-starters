package org.shield.audit.service.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mzt.logapi.beans.LogRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.shield.audit.model.AuditLog;
import org.shield.audit.model.LoginLog;
import org.shield.audit.annotation.EnableAuditLog;
import org.shield.audit.form.AuditLogQueryForm;
import org.shield.audit.repository.AuditLogRepository;
import org.shield.audit.repository.LoginLogRepository;
import org.shield.audit.service.AuditLogService;
import org.shield.audit.util.ServletRequestUtil;
import org.shield.mongo.domain.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService, ImportAware {

    private AnnotationAttributes enableAuditLog;

    @Value("${spring.application.name:''}")
    private String applicationName;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HttpServletRequest request;

    @Override
    public AuditLog create(AuditLog model) {
        return auditLogRepository.save(model);
    }

    @Override
    public PageInfo<AuditLog> list(AuditLogQueryForm request) {
        Query query = new Query();
        if (request.getTenant() != null) {
            query.addCriteria(Criteria.where("tenant").is(request.getTenant()));
        }
        if (request.getModule() != null) {
            query.addCriteria(Criteria.where("module").is(request.getModule()));
        }
        if (request.getBizNo() != null) {
            query.addCriteria(Criteria.where("bizNo").is(request.getBizNo()));
        }
        if (request.getCatalog() != null) {
            query.addCriteria(Criteria.where("catalog").is(request.getCatalog()));
        }
        if (request.getOperatorId() != null) {
            query.addCriteria(Criteria.where("operatorId").is(request.getOperatorId()));
        }
        if (request.getOperatorName() != null) {
            query.addCriteria(Criteria.where("operatorName").is(request.getOperatorName()));
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            query.addCriteria(Criteria.where("recordTime").gte(request.getStartTime()).lte(request.getEndTime()));
        }
        query.with(request);

        List<AuditLog> list = mongoTemplate.find(query, AuditLog.class);
        return PageInfo.of(PageableExecutionUtils.getPage(list, request,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), AuditLog.class)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void record(LogRecord logRecord) {
        if ("LOGIN".equals(logRecord.getCategory())) {
            saveLoginLog(logRecord);
        } else {
            saveAuditLog(logRecord);
        }
    }

    private void saveLoginLog(LogRecord logRecord) {
        LoginLog model = new LoginLog();
        UserAgent ua = UserAgentUtil.parse(ServletUtil.getHeader(request, "User-Agent", "UTF-8"));
        model.setTenant(logRecord.getTenant());
        model.setUsername(logRecord.getBizNo());
        model.setIp(ServletUtil.getClientIP(request));
        model.setBrowser(ua.getBrowser().toString() + " " + ua.getVersion());
        model.setOs(ua.getOs().toString());
        model.setRemark(logRecord.getAction());
        model.setRecordTime(logRecord.getCreateTime());
        loginLogRepository.save(model);
    }

    private void saveAuditLog(LogRecord logRecord) {
        AuditLog model = new AuditLog();
        model.setTenant(logRecord.getTenant());
        model.setModule(getModule());
        model.setBizNo(logRecord.getBizNo());
        model.setOperatorId(ServletRequestUtil.getHeader("auth-userId"));
        model.setOperatorName(ServletRequestUtil.getHeader("auth-userName"));
        model.setAction(logRecord.getAction());
        model.setCatalog(logRecord.getCategory());
        model.setRemark(logRecord.getDetail());
        model.setRecordTime(logRecord.getCreateTime());
        auditLogRepository.save(model);
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