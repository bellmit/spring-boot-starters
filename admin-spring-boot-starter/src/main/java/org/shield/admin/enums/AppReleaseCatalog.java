package org.shield.admin.enums;

import org.shield.validation.contract.IntegerEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Getter
@AllArgsConstructor
public enum AppReleaseCatalog implements IntegerEnum {

    // ios
    ANDROID(1, "android"),
    // android
    IOS(2, "ios");

    private Integer value;
    private String description;

    public static AppReleaseCatalog valueOf(Integer value) {
        return IntegerEnum.valueOf(AppReleaseCatalog.class, value);
    }

    public static AppReleaseCatalog descriptionOf(String value) {
        return IntegerEnum.descriptionOf(AppReleaseCatalog.class, value);
    }
}
