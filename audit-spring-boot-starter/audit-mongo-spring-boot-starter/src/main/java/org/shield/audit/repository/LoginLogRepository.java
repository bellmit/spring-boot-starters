package org.shield.audit.repository;

import org.shield.audit.common.model.LoginLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 操作日志
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Repository
public interface LoginLogRepository extends CrudRepository<LoginLog, String> {
}
