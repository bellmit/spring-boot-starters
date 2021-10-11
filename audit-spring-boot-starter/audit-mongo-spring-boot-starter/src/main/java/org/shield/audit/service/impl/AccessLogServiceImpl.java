package org.shield.audit.service.impl;

import java.util.List;

import org.shield.audit.form.AccessLogQueryForm;
import org.shield.audit.model.AccessLog;
import org.shield.audit.repository.AccessLogRepository;
import org.shield.audit.service.AccessLogService;
import org.shield.mongo.domain.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AccessLog create(AccessLog model) {
        return accessLogRepository.save(model);
    }

    @Override
    public PageInfo<AccessLog> list(AccessLogQueryForm request) {
        Query query = new Query();
        if (request.getTenant() != null) {
            query.addCriteria(Criteria.where("tenant").is(request.getTenant()));
        }
        if (request.getIp() != null) {
            query.addCriteria(Criteria.where("ip").is(request.getIp()));
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

        List<AccessLog> list = mongoTemplate.find(query, AccessLog.class);
        return PageInfo.of(PageableExecutionUtils.getPage(list, request,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), AccessLog.class)));
    }
}