package org.shield.admin.service;

import java.util.List;

import org.shield.admin.form.BannerQueryForm;
import org.shield.admin.model.Banner;
import org.shield.crud.service.Service;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface BannerService extends Service<Banner> {

    /**
     * 列表查询
     *
     * @param form
     * @return
     */
    List<Banner> list(BannerQueryForm form);
}