package org.shield.audit.client.gateway.feign;

import org.shield.audit.client.gateway.model.AccessLogForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@FeignClient(name = "${audit.log.hostname:admin}")
public interface AccessLogHttpClient {

    /**
     *
     * 提交访问日志
     *
     * @param form
     * @return
     */
    @PostMapping("/logs/access-logs")
    AccessLogForm createAccesLogs(@RequestBody AccessLogForm form);
}
