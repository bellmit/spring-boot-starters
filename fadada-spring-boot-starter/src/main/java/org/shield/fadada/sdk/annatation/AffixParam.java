package org.shield.fadada.sdk.annatation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zacksleo <zacksleo@gmail.com>
 *
 * 该参数参拼接到最后
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AffixParam {

    /**
     * 该参数参拼接到最后
     */
    public static final String HEADER_PREFIX = "__AFFIX__";

    /**
     * 字段名称，默认使用参数名称
     */
    String value() default "";

    /**
     * 字段序号，默认使用出现顺序
     */
    String order() default "";
}
