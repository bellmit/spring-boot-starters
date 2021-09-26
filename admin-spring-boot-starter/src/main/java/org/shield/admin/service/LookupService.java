package org.shield.admin.service;

import java.util.List;

import org.shield.admin.form.LookupQueryForm;
import org.shield.admin.model.Lookup;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface LookupService extends Service<Lookup> {

    /**
     * 列表查询
     *
     * @param form
     * @return
     */
    List<Lookup> list(LookupQueryForm form);
}
