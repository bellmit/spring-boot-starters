package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.model.BannerPosition;
import org.shield.admin.service.BannerPositionService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

/**
 * 广告位
 *
 * @author zacksleo@gmail.com
 */
@Service
public class BannerPositionServiceImpl extends AbstractService<BannerPosition> implements BannerPositionService {

    @Override
    public List<BannerPosition> list() {
        return findAll();
    }
}