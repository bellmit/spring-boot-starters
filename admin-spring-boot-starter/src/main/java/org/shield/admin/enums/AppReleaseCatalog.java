package org.shield.admin.enums;

import java.util.Objects;

import org.shield.validation.contract.IntegerEnum;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public enum AppReleaseCatalog implements IntegerEnum {

    // ios
    ANDROID(1, "android"),
    // android
    IOS(2, "ios");

    private Integer value;
    private String description;

    AppReleaseCatalog(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }

    public static AppReleaseCatalog fromValue(Integer value) {
        for (AppReleaseCatalog statusEnum : values()) {
            if (Objects.equals(value, statusEnum.value())) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException();
    }

    public static AppReleaseCatalog fromDescription(String value) {
        for (AppReleaseCatalog statusEnum : values()) {
            if (Objects.equals(value, statusEnum.description())) {
                return statusEnum;
            }
        }
        throw new IllegalArgumentException();
    }
}
