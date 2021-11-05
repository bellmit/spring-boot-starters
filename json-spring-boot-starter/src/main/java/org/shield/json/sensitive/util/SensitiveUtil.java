package org.shield.json.sensitive.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏处理类
 *
 * @author zacksleo <zacksleo@gmail.com>
 *
 */
public class SensitiveUtil {

    private SensitiveUtil() {}

    public static final String COMMON = "******";
    private static final String CLASSIFIED = "保密";
    private static final String SIX_STAR = "******";

    /**
     * 对手机号的处理
     *
     * @param mobile 手机号
     * @return 脱敏后的手机号
     */
    public static String mobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return "";
        }

        return StringUtils.left(mobile, 2).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(mobile, 4), StringUtils.length(mobile), "*"), "***"));
    }

    /**
     * 对身份证的处理
     *
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    public static String idCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return "";
        }

        return StringUtils.left(idCard, 2).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(idCard, 4), StringUtils.length(idCard), "*"), "***"));
    }

    /**
     * 对email的处理
     *
     * @param email 邮箱
     * @return 脱敏后的邮箱
     */
    public static String email(String email) {
        return CLASSIFIED;
    }

    /**
     * 对密码的处理
     */
    public static String password() {
        return SIX_STAR;
    }

    /**
     * 用户名
     */
    public static String userName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return "";
        }

        return StringUtils.left(userName, 2).concat(StringUtils.removeStart(
                StringUtils.leftPad(StringUtils.right(userName, 4), StringUtils.length(userName), "*"), "***"));
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
     *
     * @param fullName
     * @return
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }
}
