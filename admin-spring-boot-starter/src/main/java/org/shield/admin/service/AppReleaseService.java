package org.shield.admin.service;

import java.util.List;

import org.shield.admin.form.AppReleaseQueryForm;
import org.shield.admin.model.AppRelease;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface AppReleaseService extends Service<AppRelease> {

    /**
     * 列表查询
     *
     * @param form
     * @return
     */
    List<AppRelease> list(AppReleaseQueryForm form);
}