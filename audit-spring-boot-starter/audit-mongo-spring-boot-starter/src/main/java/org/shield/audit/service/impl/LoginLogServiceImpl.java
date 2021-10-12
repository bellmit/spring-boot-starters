package org.shield.audit.service.impl;

import java.util.List;

import org.shield.audit.form.LoginLogQueryForm;
import org.shield.audit.model.LoginLog;
import org.shield.audit.service.LoginLogService;
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
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PageInfo<LoginLog> list(LoginLogQueryForm request) {
        Query query = new Query();
        if (request.getTenant() != null) {
            query.addCriteria(Criteria.where("tenant").is(request.getTenant()));
        }
        if (request.getIp() != null) {
            query.addCriteria(Criteria.where("ip").is(request.getIp()));
        }
        if (request.getUsername() != null) {
            query.addCriteria(Criteria.where("username").is(request.getUsername()));
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            query.addCriteria(Criteria.where("recordTime").gte(request.getStartTime()).lte(request.getEndTime()));
        }
        query.with(request);

        List<LoginLog> list = mongoTemplate.find(query, LoginLog.class);
        return PageInfo.of(PageableExecutionUtils.getPage(list, request,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), LoginLog.class)));
    }
}