package org.shield.audit.annotation;

import java.lang.annotation.*;

/**
 * @author zacksleo <zacksleo@gmail.com>
 *
 * 启用日志管理
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableAuditLog {
}
