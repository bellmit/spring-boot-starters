package org.shield.validation.validator.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

import org.shield.validation.contract.IntegerEnum;
import org.shield.validation.validator.IntegerEnumValidator;

/**
 * 枚举类型校验 枚举类型需要实现接口 IntegerEnum
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = IntegerEnumValidator.class)
public @interface ValidIntegerEnum {

    String message() default "字段取值不合法";

    /**
     * 默认必填
     */
    boolean optional() default false;

    /**
     * 显示允许的值
     * @return
     */
    boolean showAllowValues() default false;

    /**
     * 对应的枚举类
     */
    Class<? extends IntegerEnum> value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
