package org.endless.ddd.simplified.generator.components.template;

import com.tansun.ddd.generator.object.entity.Field;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

import static com.tansun.ddd.generator.utils.DDDUtils.fieldMethod;
import static com.tansun.ddd.generator.utils.StringTools.*;

/**
 * ConstructorTemplate
 * <p>
 * create 2024/09/19 01:19
 * <p>
 * update 2024/09/19 01:19
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class ConstructorTemplate {

    /**
     * 全参构造器构造函数代码模版
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     * @param className     类名
     */
    public static void allArgsConstructor(StringBuilder stringBuilder, List<Field> fields, String className) {

        if (className.endsWith("Enum")) {
            stringBuilder.append("    ").append(className).append("(");
        } else if (className.endsWith("HandlerImpl") || className.endsWith("DataManager") || className.startsWith("Spring") || className.endsWith("Controller")) {
            stringBuilder.append("    public ").append(className).append("(");
        } else if (className.endsWith("Aggregate") || className.endsWith("Entity") || className.endsWith("Value")) {
            stringBuilder.append("    @Builder(buildMethodName = \"innerBuild\")\n");
            stringBuilder.append("    private ").append(className).append("(");
        } else {
            stringBuilder.append("    @Builder\n");
            stringBuilder.append("    private ").append(className).append("(");
        }

        StringBuilder params = new StringBuilder();
        StringBuilder body = new StringBuilder();

        for (Field field : fields) {
            String fieldType = field.getType();
            String fieldName = field.getName();
            if (!className.endsWith("Aggregate") && !className.endsWith("Entity")) {
                fieldType = fieldType.replace("Entity", getLastCamelCase(className, 1));
                if (fieldName.equals("createAt") || fieldName.equals("modifyAt")) {
                    continue;
                }
            }

            // 构造函数参数
            params.append(fieldType).append(" ").append(fieldName).append(", ");

            // 构造函数主体
            if (fieldType.startsWith("List<") && !className.endsWith("Enum") && !className.endsWith("HandlerImpl")) {
                body.append("        this.").append(fieldName).append(" = ").append(fieldName).append(" != null ? new ArrayList<>(").append(fieldName).append(") : new ArrayList<>();\n");
            } else {
                body.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            }
        }
        params.delete(params.length() - 2, params.length());
        stringBuilder
                .append(params).append(") {\n")
                .append(body);
        if (className.endsWith("Aggregate") || className.endsWith("Entity")) {
            stringBuilder.append("        validate(); \n");
        }
        stringBuilder.append("    }\n\n");
    }

    /**
     * 生成领域实体转换对象构造函数代码
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     * @param className     类名
     */
    public static void entityArgsConstructor(StringBuilder stringBuilder, Set<String> entityNames, List<Field> fields, String className, String generics) {

        String entityName = StringUtils.uncapitalize(generics);

        stringBuilder.append("    public ").append(className).append("(@NonNull ").append(generics).append(" ").append(entityName).append(") {\n");

        StringBuilder body = new StringBuilder();


        for (Field field : fields) {
            String fieldType = field.getType();
            String fieldName = field.getName();
            String filedValue = entityName + ".get" + fieldMethod(fieldName);
            String generic = generics(fieldType);

            if (fieldName.equals("createAt") || fieldName.equals("modifyAt")) {
                continue;
            }

            // 构造函数主体
            if (fieldType.startsWith("List<") && entityNames.contains(generic)) {

                String filedToRecord = ".stream().map(" + exchangeSuffix(generic, "Record", 1) + "::new).collect(Collectors.toList())";
                body.append("        this.").append(fieldName).append(" = ").append(filedValue).append(filedToRecord).append(";\n");
            } else {
                body.append("        this.").append(fieldName).append(" = ").append(filedValue).append(";\n");
            }
        }
        stringBuilder
                .append(body)
                .append("        validate();\n").append("    }\n\n");
    }
}
