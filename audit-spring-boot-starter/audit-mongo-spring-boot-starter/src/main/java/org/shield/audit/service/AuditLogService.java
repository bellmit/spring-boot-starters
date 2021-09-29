package org.shield.audit.service;

import com.github.pagehelper.PageInfo;
import com.mzt.logapi.service.ILogRecordService;

import org.shield.audit.common.model.AuditLog;
import org.shield.audit.form.AuditLogQueryForm;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public interface AuditLogService extends ILogRecordService {

    /**
     * 创建
     *
     * @param model
     * @return
     */
    public AuditLog create(AuditLog model);

    /**
     * 分页查询
     *
     * @param form
     * @return
     */
    PageInfo<AuditLog> list(AuditLogQueryForm form);
}
