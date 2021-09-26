
package org.shield.admin.service.impl;

import java.util.List;

import org.shield.admin.model.Config;
import org.shield.admin.service.ConfigService;
import org.shield.crud.service.AbstractService;
import org.springframework.stereotype.Service;

/**
 * App发布
 *
 * @author zacksleo@gmail.com
 */
@Service
public class ConfigServiceImpl extends AbstractService<Config> implements ConfigService {

    @Override
    public List<Config> list() {
        return findAll();
    }
}