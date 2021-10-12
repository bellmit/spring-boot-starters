package org.shield.audit.service;

import org.shield.audit.model.LoginLog;

import com.github.pagehelper.PageInfo;

import org.shield.audit.form.LoginLogQueryForm;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface LoginLogService {

    /**
     * 分页查询
     *
     * @param form
     * @return
     */
    PageInfo<LoginLog> list(LoginLogQueryForm form);
}
