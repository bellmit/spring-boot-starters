package org.shield.validation.contract;

import java.util.Arrays;
import java.util.Objects;

/**
 * IntegerEnum
 *
 * @author zacksleo@gmail.com
 */
public interface IntegerEnum {

    /**
     * 值
     *
     * @return
     */
    Integer getValue();

    /**
     * 文字描述
     *
     * @return
     */
    String getDescription();

    /**
     * 枚举名称
     *
     * @return
     */
    String name();

    /**
     * 根据值解析枚举
     *
     * @param <T>
     * @param clazz
     * @param value
     * @return
     */
    public static <T extends IntegerEnum> T valueOf(Class<T> clazz, Integer value) {
        return Arrays.asList(clazz.getEnumConstants()).stream()
                .filter(statusEnum -> Objects.equals(statusEnum.getValue(), value)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 根据描述解析枚举
     *
     * @param clazz
     * @param description
     * @return
     */
    public static <T extends IntegerEnum> T descriptionOf(Class<T> clazz, String description) {
        return Arrays.asList(clazz.getEnumConstants()).stream()
                .filter(statusEnum -> Objects.equals(statusEnum.getDescription(), description)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
