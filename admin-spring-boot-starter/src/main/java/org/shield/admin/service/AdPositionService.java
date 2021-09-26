package org.shield.admin.service;

import java.util.List;

import org.shield.admin.model.AdPosition;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface AdPositionService extends Service<AdPosition> {

    /**
     * 列表查询
     *
     * @return
     */
    List<AdPosition> list();
}