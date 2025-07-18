package org.endless.ddd.simplified.generator.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringTools
 * <p>
 * create 2024/09/18 02:00
 * <p>
 * update 2024/09/18 02:00
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class StringTools {

    /**
     * 工具方法：移除最后N个大写字母后的字符串的后缀
     *
     * @param string    字符串
     * @param uppercase 大写字母数量
     * @return {@link String }
     */
    public static String removeSuffix(String string, int uppercase) {
        String suffix = getLastCamelCase(string, uppercase);
        return removeSuffix(string, suffix);
    }

    /**
     * 工具方法：移除后缀
     *
     * @param string 字符串
     * @param suffix 后缀
     * @return {@link String }
     */
    public static String removeSuffix(String string, String suffix) {
        if (string != null && string.endsWith(suffix)) {
            return string.substring(0, string.length() - suffix.length());
        }
        return string;
    }

    /**
     * 工具方法：替换后缀
     *
     * @param string    字符串
     * @param oldSuffix 旧后缀
     * @param newSuffix 新后缀
     * @return {@link String }
     */
    public static String exchangeSuffix(String string, String oldSuffix, String newSuffix) {
        return removeSuffix(string, oldSuffix) + newSuffix;
    }

    /**
     * 工具方法：替换最后N个大写字母后的字符串的后缀
     *
     * @param string    字符串
     * @param newSuffix 新后缀
     * @param uppercase 大写字母数量
     * @return {@link String }
     */
    public static String exchangeSuffix(String string, String newSuffix, int uppercase) {
        return removeSuffix(string, uppercase) + newSuffix;
    }

    /**
     * 工具方法：移除前N个大写字母后的字符串的前缀
     *
     * @param string    字符串
     * @param uppercase 大写字母数量
     * @return {@link String }
     */
    public static String removePrefix(String string, int uppercase) {
        String prefix = getLastCamelCase(string, uppercase);
        return removePrefix(string, prefix);
    }

    /**
     * 工具方法：移除前缀
     *
     * @param string 字符串
     * @param prefix 前缀
     * @return {@link String }
     */
    public static String removePrefix(String string, String prefix) {
        if (string != null && string.startsWith(prefix)) {
            return string.substring(prefix.length());
        }
        return string; // 如果不以指定前缀开头，则返回原字符串
    }

    /**
     * 工具方法：替换前缀
     *
     * @param string    字符串
     * @param oldPrefix 旧前缀
     * @param newPrefix 新前缀
     * @return {@link String }
     */
    public static String exchangePrefix(String string, String oldPrefix, String newPrefix) {
        return newPrefix + removePrefix(string, oldPrefix);
    }

    /**
     * 工具方法：替换前N个大写字母后的字符串的前缀
     *
     * @param string    字符串
     * @param newPrefix 新前缀
     * @param uppercase 大写字母数量
     * @return {@link String }
     */
    public static String exchangePrefix(String string, String newPrefix, int uppercase) {
        return newPrefix + removeSuffix(string, uppercase);
    }


    /**
     * 工具方法： 获取最后N个大写字母后的字符串
     *
     * @param string    字符串
     * @param uppercase 大写字母数量
     * @return {@link String }
     */
    public static String getLastCamelCase(String string, int uppercase) {

        int upperCaseCount = 0;
        for (int i = string.length() - 1; i > 0; i--) {
            if (Character.isUpperCase(string.charAt(i))) {
                upperCaseCount++;
                if (upperCaseCount == uppercase) {
                    return string.substring(i);
                }
            }
        }
        return string;  // 如果没有大写字母，返回整个字符串
    }

    /**
     * 工具方法： 获取前N个大写字母后的字符串
     *
     * @param string    字符串
     * @param uppercase 大写字母数量
     * @return {@link String }
     */
    public static String getFirstCamelCase(String string, int uppercase) {
        int upperCaseCount = 0;

        for (int i = 1; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                upperCaseCount++;
                if (upperCaseCount == uppercase) {
                    return string.substring(0, i); // 截取前N个大写字母部分
                }
            }
        }
        return string;  // 如果大写字母数量不足，返回整个字符串
    }

    /**
     * 工具方法：获取泛型类型
     *
     * @param string 泛型字符串
     * @return {@link String }
     */
    public static String generics(String string) {
        if (string == null || !string.contains("<") || !string.contains(">")) {
            return string;
        }
        return string.substring(string.indexOf('<') + 1, string.indexOf('>'));
    }

    /**
     * 处理泛型后缀的修改
     *
     * @param oldSuffix 旧的后缀
     * @param newSuffix 新的后缀
     * @return 修改后 List 类型的字符串
     */
    public static String exchangeGenerics(String string, String oldSuffix, String newSuffix) {
        if (string == null || oldSuffix == null || newSuffix == null || !string.contains("<") || !string.contains(">")) {
            return string;  // 返回原字符串，如果不符合格式或是 null
        }
        return "List<" + exchangeSuffix(generics(string), oldSuffix, newSuffix) + ">";
    }


    /**
     * 工具方法：驼峰转下划线
     *
     * @param camelCase 驼峰字符串
     * @return {@link String }
     */
    public static String snakeCase(String camelCase) {
        return convertToSeparator(camelCase, "_");
    }

    /**
     * 工具方法： 将驼峰形式的类名转换为点号形式
     *
     * @param camelCase 驼峰形式
     * @return {@link String }
     */
    public static String convertToDot(String camelCase) {
        return convertToSeparator(camelCase, ".");
    }

    public static String convertToSlash(String camelCase) {
        return convertToSeparator(camelCase, "/");
    }

    public static String convertToSeparator(String camelCase, String separator) {
        StringBuilder result = new StringBuilder();

        // 将第一个字母转换为小写并添加到结果中
        result.append(Character.toLowerCase(camelCase.charAt(0)));

        // 遍历剩下的字符
        for (int i = 1; i < camelCase.length(); i++) {
            char ch = camelCase.charAt(i);

            // 如果是大写字母，先添加点号再转换为小写
            if (Character.isUpperCase(ch)) {
                result.append(separator);
                result.append(Character.toLowerCase(ch));
            } else {
                // 否则直接添加
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static String getLength(String description) {
        String regex = "\\((\\d+)B\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(description);

        if (matcher.find()) {
            return String.valueOf(Integer.parseInt(matcher.group(1)));
        } else {
            return "255";
        }
    }

    public static Map<String, Integer> toDecimal(String decimal) {
        // 正则表达式：匹配 "(M, D)"，M 和 D 为整数
        // 1. (\\d+) 表示匹配一个或多个数字
        // 2. (,\\s*) 匹配逗号后可选的空格，\\s* 表示空格出现零次或多次
        String regex = "\\((\\d+),\\s*(\\d+)\\)|\\((\\d+),(\\d+)\\)"; // 两种情况
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(decimal);
        Map<String, Integer> result = toDecimal(matcher);

        if (result != null) {
            return result;
        }
        // 如果没有匹配到，抛出异常
        throw new IllegalArgumentException("请在描述中添加正确的BigDecimal类型，格式为：(M, D) 或 (M,D)");
    }

    private static Map<String, Integer> toDecimal(Matcher matcher) {
        // 匹配并提取精度和小数位数
        if (matcher.find()) {
            // 如果匹配到了带空格的情况
            if (matcher.group(1) != null && matcher.group(2) != null) {
                Integer M = Integer.parseInt(matcher.group(1));
                Integer D = Integer.parseInt(matcher.group(2));
                Map<String, Integer> result = new HashMap<>();
                result.put("precision", M);
                result.put("scale", D);
                return result;
            // 如果匹配到了不带空格的情况
            } else if (matcher.group(3) != null && matcher.group(4) != null) {
                Integer M = Integer.parseInt(matcher.group(3));
                Integer D = Integer.parseInt(matcher.group(4));
                Map<String, Integer> result = new HashMap<>();
                result.put("precision", M);
                result.put("scale", D);
                return result;
            }
        }
        return null;
    }
}
