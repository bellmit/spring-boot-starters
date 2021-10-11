package org.shield.audit.client.feign;

import org.shield.audit.client.model.AuditLogForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@FeignClient(name = "${audit.log.hostname:admin}")
public interface AuditLogHttpClient {

    /**
     *
     * 提交操作日志
     *
     * @param form
     */
    @PostMapping("/logs/audit-logs")
    void createAuditLogs(@RequestBody AuditLogForm form);
}
