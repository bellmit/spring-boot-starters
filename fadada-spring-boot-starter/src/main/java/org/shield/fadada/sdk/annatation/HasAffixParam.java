package org.shield.fadada.sdk.annatation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zacksleo <zacksleo@gmail.com>
 *
 * 该参数参与 Sha1 签名
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasAffixParam {
    /**
     * 默认占位值
     */
    public static final String DEFAULT_VALUE = "__NONE__";
}
