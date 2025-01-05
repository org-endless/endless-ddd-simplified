package org.endless.ddd.simplified.starter.common.utils.model.object;

import org.endless.ddd.simplified.starter.common.exception.utils.model.ObjectToolsException;
import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.endless.ddd.simplified.starter.common.config.endless.constant.EndlessConstant.SENSITIVE_KEYS;

/**
 * ObjectTools
 * <p>
 * create 2024/11/19 02:13
 * <p>
 * update 2024/11/19 02:13
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class ObjectTools {

    /**
     * 对象脱敏
     *
     * @param object 要处理的对象
     * @return {@link String } 脱敏后的字符串
     */
    public static String maskSensitive(Object object) {
        if (object == null) {
            return "null";
        }
        // 如果是基础类型或字符串，直接返回 toString()
        if (isPrimitiveOrWrapper(object) || object instanceof String) {
            return object.toString();
        }
        // 如果是集合，递归处理每个元素
        if (object instanceof Collection<?> collection) {
            StringBuilder maskedResult = new StringBuilder("[");
            for (Object item : collection) {
                maskedResult.append(maskSensitive(item)).append(", ");
            }
            if (!collection.isEmpty()) {
                maskedResult.setLength(maskedResult.length() - 2); // 去掉最后的逗号和空格
            }
            maskedResult.append("]");
            return maskedResult.toString();
        }
        // 如果是 Map，递归处理每个键值对
        if (object instanceof Map<?, ?> map) {
            StringBuilder maskedResult = new StringBuilder("{");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                maskedResult.append(maskSensitive(entry.getKey())).append("=")
                        .append(maskSensitive(entry.getValue())).append(", ");
            }
            if (!map.isEmpty()) {
                maskedResult.setLength(maskedResult.length() - 2); // 去掉最后的逗号和空格
            }
            maskedResult.append("}");
            return maskedResult.toString();
        }
        // // 如果类名包含敏感字符，直接返回 "******"
        // String className = model.getClass().getSimpleName().toLowerCase();
        // for (String sensitiveKey : SENSITIVE_KEYS) {
        //     if (className.contains(sensitiveKey.toLowerCase())) {
        //         return "******";
        //     }
        // }
        // 处理普通对象，递归解析其 toString() 输出
        String originalString = object.toString();
        for (String sensitiveKey : SENSITIVE_KEYS) {
            String lowerCaseKey = sensitiveKey.toLowerCase();
            // 匹配格式：key=value 或 key: value
            String regex = "(?i)(" + lowerCaseKey + "\\s*[=:]\\s*)[^,}]+";
            originalString = originalString.replaceAll(regex, "$1******");
        }
        return originalString;
    }

    /**
     * 对象脱敏
     *
     * @param object 要处理的对象
     */
    private static void maskObjectSensitive(Object object) {
        if (object == null) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(object);
                if (isPrimitiveOrWrapper(field) || field.getType().equals(String.class)) {
                    if (StringTools.containsSensitive(fieldName)) {
                        field.set(object, "******");
                    }
                } else if (fieldValue != null) {
                    if (fieldValue instanceof List<?> list) {
                        for (Object item : list) {
                            maskObjectSensitive(item);
                        }
                    } else if (fieldValue instanceof Map<?, ?> map) {
                        for (Object mapValue : map.values()) {
                            maskObjectSensitive(mapValue);
                        }
                    } else {
                        maskObjectSensitive(fieldValue);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new ObjectToolsException("对象脱敏异常: " + e.getMessage(), e);
            }
        }
    }

    public static boolean isPrimitiveOrWrapper(Object object) {
        return object.getClass().isPrimitive() || isWrapperType(object.getClass());
    }

    private static boolean isWrapperType(Class<?> clazz) {
        return clazz.equals(Boolean.class) || clazz.equals(Byte.class) ||
                clazz.equals(Character.class) || clazz.equals(Short.class) ||
                clazz.equals(Integer.class) || clazz.equals(Long.class) ||
                clazz.equals(Float.class) || clazz.equals(Double.class);
    }
}
