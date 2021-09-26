package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.model.AdPosition;
import org.shield.admin.service.AdPositionService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

/**
 * 广告位
 *
 * @author zacksleo@gmail.com
 */
@Service
public class AdPositionServiceImpl extends AbstractService<AdPosition> implements AdPositionService {

    @Override
    public List<AdPosition> list() {
        return findAll();
    }
}