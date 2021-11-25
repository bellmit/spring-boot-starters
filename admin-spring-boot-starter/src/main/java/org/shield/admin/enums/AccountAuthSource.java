package org.shield.admin.enums;

import org.shield.validation.contract.IntegerEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号认证来源
 *
 * @author zacksleo <zacksleo@gmail.com>
 */
@Getter
@AllArgsConstructor
public enum AccountAuthSource implements IntegerEnum {


    /**
     * 用户名
     */
    USERNAME(0, "用户名"),
    /**
     * 手机号
     */
    PHONE(10, "手机号"),
    /**
     * 邮箱
     */
    EMAIL(20, "邮箱"),
    /**
     * 微信
     */
    WECHAT(30, "微信"),
    /**
     * QQ
     */
    QQ(40, "QQ"),
    /**
     * Apple
     */
    APPLE(50, "Apple");

    public static final String USERNAME_VAL = "0";
    public static final String PHONE_VAL = "10";
    public static final String EMAIL_VAL = "20";
    public static final String WECHAT_VAL = "30";
    public static final String QQ_VAL = "40";
    public static final String APPLE_VAL = "50";

    private Integer value;
    private String description;

    public static AccountAuthSource valueOf(Integer value) {
        return IntegerEnum.valueOf(AccountAuthSource.class, value);
    }
}
