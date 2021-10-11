package org.shield.audit.service;

import org.shield.audit.model.AccessLog;

import com.github.pagehelper.PageInfo;

import org.shield.audit.form.AccessLogQueryForm;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface AccessLogService {

    /**
     * 创建
     *
     * @param model
     * @return
     */
    public AccessLog create(AccessLog model);

    /**
     * 分页查询
     *
     * @param form
     * @return
     */
    PageInfo<AccessLog> list(AccessLogQueryForm form);
}
