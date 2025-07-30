package org.endless.ddd.simplified.generator.components.utils;

import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Value;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tansun.ddd.generator.utils.StringTools.*;

/**
 * DDDUtils
 * <p>
 * create 2024/09/16 12:45
 * <p>
 * update 2024/09/16 12:45
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
@Slf4j
public class DDDUtils {

    private static final int MAX_BACKUP_FILES = 10;

    private static final String PROJECT_ROOT = "..";

    public static Boolean isValidateDecimalField(String type, String name, String description) {
        if (type.equals("BigDecimal") || type.equals("String")) {
            return name.endsWith("Amount") || name.equals("amount") ||
                    name.endsWith("Rate") || name.equals("rate") ||
                    name.endsWith("Percentage") || name.equals("percentage");
        }
        return false;
    }

    public static String domainPackage(Aggregate aggregate) {
        String contextName = aggregate.getContextName().toLowerCase();
        String domainPackage = aggregate.getDomainName().toLowerCase();
        if (domainPackage.contains(contextName)) {
            domainPackage = convertToDot(aggregate.getDomainName()).replace("." + contextName, "")
                    .replace(contextName + ".", "");
        }
        if (!contextName.equals(domainPackage)) {
            domainPackage = convertToDot(aggregate.getDomainName()).replace("." + contextName, "")
                    .replace(contextName + ".", "");
        }
        return servicePackage(aggregate) + "." + aggregate.getServiceSubPackage() + "." + contextName + "."
                + domainPackage;
    }

    public static String servicePackage(Aggregate aggregate) {
        return aggregate.getGroupId() + "." + convertToDot(aggregate.getServiceName());
    }

    public static String packageName(Aggregate aggregate, String subPackage) {
        return domainPackage(aggregate) + "." + subPackage;
    }

    /**
     * 工具方法: 获取类的ID属性名
     *
     * @param className 类名
     * @return {@link String }
     */
    public static String id(String className, int uppercase) {
        return StringUtils.uncapitalize(removeSuffix(className, classType(className, uppercase)) + "Id");
    }

    public static String getter(String fieldName) {
        return "get" + StringUtils.capitalize(fieldName);
    }

    /**
     * 工具方法: 获取类的类型
     *
     * @param className 类名
     * @return {@link String }
     */
    public static String classType(String className, int uppercase) {
        return getLastCamelCase(className, uppercase);
    }

    /**
     * 工具方法: 获取父类名
     *
     * @param serviceName 服务名
     * @param className   类名
     * @return {@link String }
     */
    public static String superClassName(String serviceName, String className, int uppercase) {
        return serviceName + classType(className, uppercase);
    }

    /**
     * 工具方法: 获取父类名并带有泛型
     *
     * @param superClassName 父类名
     * @param generics       泛型
     * @return {@link String }
     */
    public static String superClassNameWithGenerics(String superClassName, String... generics) {
        return superClassName + "<" + String.join(", ", generics) + ">";
    }

    /**
     * 工具方法: 获取字段方法名
     *
     * @param fieldName 字段名
     * @return {@link String }
     */
    public static String fieldMethod(String fieldName) {
        return StringUtils.capitalize(fieldName) + "()";
    }

    public static Set<String> entityNames(Aggregate aggregate) {
        return Optional.ofNullable(aggregate.getEntities())
                .orElse(Collections.emptyList()).stream()
                .map(Entity::getName)
                .collect(Collectors.toSet());
    }

    public static Set<String> valueNames(Aggregate aggregate) {
        return Optional.ofNullable(aggregate.getValues())
                .orElse(Collections.emptyList()).stream()
                .map(Value::getName)
                .collect(Collectors.toSet());
    }

    public static Set<String> enumNames(Aggregate aggregate) {
        return Optional.ofNullable(aggregate.getEnums())
                .orElse(Collections.emptyList()).stream()
                .map(Enum::getName)
                .collect(Collectors.toSet());
    }

    /**
     * 工具方法: 获取当前日期
     *
     * @return {@link String }
     */
    public static String currentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 工具方法: 获取方法修饰符
     *
     * @param className 类名
     * @return {@link String }
     */
    public static String access(String className, Boolean isAggregateInner, Boolean isEntityInner) {

        if (className.endsWith("Aggregate") && isAggregateInner) {
            return "private";
        } else if (className.endsWith("Entity") && isEntityInner) {
            return "protected";
        }
        return "public";
    }
}
