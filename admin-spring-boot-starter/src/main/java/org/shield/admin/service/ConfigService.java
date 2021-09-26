package org.shield.admin.service;

import java.util.List;

import org.shield.admin.model.Config;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface ConfigService extends Service<Config> {

    /**
     * 列表查询
     *
     * @return
     */
    List<Config> list();
}