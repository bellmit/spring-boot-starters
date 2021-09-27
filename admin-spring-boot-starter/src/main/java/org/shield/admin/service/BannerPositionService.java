package org.shield.admin.service;

import java.util.List;

import org.shield.admin.model.BannerPosition;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface BannerPositionService extends Service<BannerPosition> {

    /**
     * 列表查询
     *
     * @return
     */
    List<BannerPosition> list();
}