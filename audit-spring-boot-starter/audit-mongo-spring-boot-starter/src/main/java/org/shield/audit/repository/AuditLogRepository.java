package org.shield.audit.repository;

import org.shield.audit.model.AuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 操作日志
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, String> {
}
