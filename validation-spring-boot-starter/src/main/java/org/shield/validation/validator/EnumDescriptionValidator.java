package org.shield.validation.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.shield.validation.validator.annotation.ValidEnumDescription;
import org.shield.validation.contract.IntegerEnum;

/**
 *
 * 手机号校验
 *
 * @author zacksleo
 */
public class EnumDescriptionValidator implements ConstraintValidator<ValidEnumDescription, String> {

    private Set<String> values = new HashSet<>();

    private boolean showAllowValues;

    private boolean optional;

    @Override
    public void initialize(ValidEnumDescription validEnum) {

        Class<?> enumClazz = validEnum.value();
        Object[] enumConstants = enumClazz.getEnumConstants();
        if (null == enumConstants) {
            return;
        }
        for (Object object : enumConstants) {
            values.add(((IntegerEnum) object).description());
        }
        showAllowValues = validEnum.showAllowValues();
        optional = validEnum.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return optional;
        }
        if (values.contains(value)) {
            return true;
        }
        if (showAllowValues) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("字段取值不合法: " + values.toString()).addConstraintViolation();
        }
        return false;
    }
}
