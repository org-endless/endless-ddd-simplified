package org.endless.ddd.simplified.generator.template;

import org.endless.ddd.simplified.generator.object.entity.Field;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * DefineTemplate
 * <p>
 * create 2024/09/18 21:57
 * <p>
 * update 2024/09/18 21:57
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class DefineTemplate {

    /**
     * 生成类定义代码模版
     *
     * @param stringBuilder  字符串构建器
     * @param className      类名
     * @param superClassName 父类名
     */
    public static void classDefine(StringBuilder stringBuilder, String className, String superClassName) {

        if (className.endsWith("Aggregate") || className.endsWith("Entity") || className.endsWith("Value")) {
            stringBuilder
                    .append("@Getter\n")
                    .append("@ToString\n")
                    .append("@Builder(buildMethodName = \"innerBuild\")\n");
        } else if (className.endsWith("DataManager")) {
            stringBuilder
                    .append("@Lazy\n")
                    .append("@Component\n");
        } else if (className.endsWith("ServiceImpl")) {
            stringBuilder
                    .append("@Lazy\n")
                    .append("@Service\n");
        } else if (className.endsWith("Configuration")) {
            stringBuilder
                    .append("@Lazy\n")
                    .append("@Configuration\n");
        }
        classDefineEnd(stringBuilder, className, superClassName);
    }

    public static void classDefineRecord(StringBuilder stringBuilder, String className, String superClassName, String tableName) {
        if (className.endsWith("Record")) {
            stringBuilder
                    .append("@Getter\n")
                    .append("@ToString\n")
                    .append("@Builder\n")
                    .append("@NoArgsConstructor\n")
                    .append("@AllArgsConstructor(access = AccessLevel.PRIVATE)\n")
                    .append("@TableName(value = \"").append(tableName).append("\")\n");
        }
        classDefineEnd(stringBuilder, className, superClassName);
    }

    public static void controllerDefine(StringBuilder stringBuilder, String uri, String className, String superClassName) {
        if (className.endsWith("Controller")) {
            stringBuilder
                    .append("@Lazy\n")
                    .append("@RestController\n")
                    .append("@RequestMapping(\"/").append(uri).append("\")\n");
        }
        classDefineEnd(stringBuilder, className, superClassName);
    }

    public static void classDefineTransfer(StringBuilder stringBuilder, List<Field> fields, String className, String superClassName) {

        if (className.endsWith("Transfer")) {
            stringBuilder
                    .append("@Getter\n")
                    .append("@ToString\n")
                    .append("@Builder\n")
                    .append("@JSONType(orders = {");
            for (Field field : fields) {
                stringBuilder.append("\"").append(field.name()).append("\", ");
            }
            stringBuilder
                    .delete(stringBuilder.length() - 2, stringBuilder.length())
                    .append("})\n");
            classDefineEnd(stringBuilder, className, superClassName);
        }
    }

    public static void classDefineEnd(StringBuilder stringBuilder, String className, String superClassName) {
        stringBuilder.append("public class ")
                .append(className);
        if (StringUtils.hasText(superClassName)) {
            stringBuilder.append(" implements ")
                    .append(superClassName);
        }
        stringBuilder.append(" {\n\n");
    }

    /**
     * 生成接口定义部分
     *
     * @param stringBuilder 字符串构建器
     * @param className     类名
     */
    public static void interfaceDefine(StringBuilder stringBuilder, String className, String superClassName) {
        if (className.endsWith("Mapper")) {
            stringBuilder
                    .append("@Lazy\n")
                    .append("@Mapper\n");
        }
        stringBuilder.append("public interface ")
                .append(className)
                .append(" extends ")
                .append(superClassName)
                .append(" {\n");
    }

    /**
     * 枚举定义生成模板
     *
     * @param stringBuilder  字符串构建器
     * @param className      类名
     * @param superClassName 父类名
     */
    public static void enumDefine(StringBuilder stringBuilder, String className, String superClassName) {
        stringBuilder
                .append("@Getter\n")
                .append("@AllArgsConstructor\n")
                .append("@ToString\n")
                .append("public enum ").append(className).append(" implements ").append(superClassName).append(" {\n\n");
    }

}
