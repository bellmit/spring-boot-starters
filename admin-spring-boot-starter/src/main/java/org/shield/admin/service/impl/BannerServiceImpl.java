package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.form.BannerQueryForm;
import org.shield.admin.model.Banner;
import org.shield.admin.service.BannerService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

/**
 * 广告
 *
 * @author zacksleo@gmail.com
 */
@Service
public class BannerServiceImpl extends AbstractService<Banner> implements BannerService {

    @Override
    public List<Banner> list(BannerQueryForm form) {
        Condition condition = form.toCondition();
        return condition.getOredCriteria().isEmpty() || !condition.getOredCriteria().get(0).isValid() ? findAll()
                : findByCondition(condition);
    }
}