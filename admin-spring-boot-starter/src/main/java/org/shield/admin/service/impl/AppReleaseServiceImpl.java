
package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.form.AppReleaseQueryForm;
import org.shield.admin.model.AppRelease;
import org.shield.admin.service.AppReleaseService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

/**
 * App发布
 *
 * @author zacksleo@gmail.com
 */
@Service
public class AppReleaseServiceImpl extends AbstractService<AppRelease> implements AppReleaseService {

    @Override
    public List<AppRelease> list(AppReleaseQueryForm form) {
        Condition condition = form.toCondition();
        return condition.createCriteria().getAllCriteria().isEmpty() ? findAll() : findByCondition(condition);
    }
}