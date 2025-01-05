package org.endless.ddd.simplified.starter.common.utils.model.string;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static org.endless.ddd.simplified.starter.common.config.endless.constant.EndlessConstant.SENSITIVE_KEYS;

/**
 * StringTools
 * <p>
 * create 2024/11/01 23:14
 * <p>
 * update 2024/11/01 23:14
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class StringTools {

    /**
     * 工具方法: 驼峰转蛇形
     *
     * @param string 字符串
     * @return {@link String }
     */
    public static String toSnake(String string) {
        if (StringUtils.hasText(string)) {
            return string.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
        }
        return string;
    }

    public static Boolean isEmailAddress(String string) {
        if (!StringUtils.hasText(string)) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, string);
    }

    public static String addBrackets(String string) {
        if (StringUtils.hasText(string)) {
            if (string.startsWith("[") && string.endsWith("]")) {
                return string;
            } else if (string.startsWith("<") && string.endsWith(">")) {
                return string;
            } else if ((string.startsWith("[") && string.contains("]"))) {
                return string;
            } else {
                return "<" + string + ">";
            }
        }
        return "<" + string + ">";
    }

    /**
     * 判断是否包含任意敏感字段
     *
     * @param string 待判断字符串
     * @return 如果 key 包含任意敏感字段，返回 true；否则返回 false
     */
    public static boolean containsSensitive(String string) {
        return SENSITIVE_KEYS.stream().anyMatch(string.toLowerCase()::contains);
    }

    public static String duplicateMessage(String message) {
        String regex = "Duplicate entry '(.+?)' for key '(.+?)'";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String duplicateValue = matcher.group(1);
            String keyName = matcher.group(2);
            return String.format("存在相同的 '%s' 为 '%s' 的记录", keyName, duplicateValue);
        }
        return message;
    }

    public static String tableMessage(String message) {
        String regex = "Table '(.+?)' doesn't exist";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            return String.format("数据库表 '%s' 不存在", tableName);
        }
        return message;
    }

    public static String dataTooLong(String message) {
        String regex = "Data too long for column '(.+?)'";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String column = matcher.group(1);
            return String.format("字段 '%s' 超长", column);
        }
        return message;
    }

    public static String unknownColumn(String message) {
        String regex = "Unknown column '(.+?)'";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String column = matcher.group(1);
            return String.format("字段 '%s' 不存在，请检查字段名是否正确", column);
        }
        return message;
    }

    public static String nullColumn(String message) {
        String regex = "Field '(.+?)' doesn't have a default value";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String column = matcher.group(1);
            return String.format("字段 '%s' 不能为空", column);
        }
        return message;
    }
}
