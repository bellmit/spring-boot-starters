package org.shield.admin.service;

import java.util.List;

import org.shield.admin.form.AdQueryForm;
import org.shield.admin.model.Ad;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface AdService extends Service<Ad> {

    /**
     * 列表查询
     *
     * @param form
     * @return
     */
    List<Ad> list(AdQueryForm form);
}