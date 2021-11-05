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
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sha1Param {

    /**
     * 参与签名的参数名前缀
     */
    public static final String HEADER_PREFIX = "__SHA1__";

    /**
     * 字段名称，默认使用参数名称
     */
    String value() default "";

    /**
     * 字段序号，默认使用出现顺序
     */
    String order() default "";
}
