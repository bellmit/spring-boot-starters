package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.form.AdQueryForm;
import org.shield.admin.model.Ad;
import org.shield.admin.service.AdService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

/**
 * 广告
 *
 * @author zacksleo@gmail.com
 */
@Service
public class AdServiceImpl extends AbstractService<Ad> implements AdService {

    @Override
    public List<Ad> list(AdQueryForm form) {
        Condition condition = form.toCondition();
        return condition.getOredCriteria().isEmpty() ? findAll() : findByCondition(condition);
    }
}