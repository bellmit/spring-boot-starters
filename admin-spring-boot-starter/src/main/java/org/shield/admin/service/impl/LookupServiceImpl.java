package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.form.LookupQueryForm;
import org.shield.admin.model.Lookup;
import org.shield.admin.service.LookupService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

/**
 * App发布
 *
 * @author zacksleo@gmail.com
 */
@Service
public class LookupServiceImpl extends AbstractService<Lookup> implements LookupService {

    @Override
    public List<Lookup> list(LookupQueryForm form) {
        Condition condition = form.toCondition();
        return condition.getOredCriteria().isEmpty() ? findAll() : findByCondition(condition);
    }
}