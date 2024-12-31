package org.endless.ddd.simplified.generator.template;

import org.endless.ddd.simplified.generator.object.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Value;
import org.endless.ddd.simplified.generator.object.type.EnumValue;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.endless.ddd.simplified.generator.utils.DDDUtils.id;
import static org.endless.ddd.simplified.generator.utils.StringTools.generics;
import static org.endless.ddd.simplified.generator.utils.StringTools.getLastCamelCase;

/**
 * FieldTemplate
 * <p>
 * create 2024/10/11 10:17
 * <p>
 * update 2024/10/11 10:17
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class FieldTemplate {

    /**
     * 为属性列表增加DDD默认属性
     *
     * @param oldFields 旧字段列表
     * @param className 类名
     */
    public static List<Field> addDefaultFields(List<Field> oldFields, String className) {

        List<Field> fields = new ArrayList<>(oldFields);

        if (className.endsWith("Aggregate") || className.endsWith("Entity") || className.endsWith("Record")) {
            fields.add(Field.builder().name("createUserId").type("String").description("创建者ID").nullable(false).build());
            fields.add(Field.builder().name("modifyUserId").type("String").description("修改者ID").nullable(false).build());
        }
        fields.add(Field.builder().name("isRemoved").type("Boolean").description("是否已删除").nullable(false).build());
        if (className.endsWith("AssociationRecord") || className.endsWith("Record")) {
            fields.add(Field.builder().name("createAt").type("Long").description("创建时间").nullable(true).build());
            fields.add(Field.builder().name("modifyAt").type("Long").description("修改时间").nullable(true).build());
            fields.add(Field.builder().name("removeAt").type("Long").description("删除时间").nullable(true).build());
        }

        return fields;
    }


    public static List<Field> recordFields(List<Field> oldFields, List<Value> values, String aggregateName, String className, String generics, String genericDescription) {
        // 将 values 转换为 Map，提高查找效率
        Map<String, Value> valueMap = Optional.ofNullable(values)
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.toMap(Value::getName, value -> value));
        List<Field> fields = new ArrayList<>();
        Set<String> expandedTypes = new HashSet<>(); // 用于记录已展开的类型

        for (Field field : oldFields) {
            String fieldName = field.getName();
            String fieldType = field.getType();
            if (generics.endsWith("Entity") && fieldName.equals(id(className, 1))) {
                fields.add(field);
                fields.add(Field.builder().name(id(aggregateName, 1)).type("String").description(genericDescription + "ID").nullable(false).build());
                continue;
            }
            // 如果 valueNames 包含 fieldType，且 valueMap 中存在对应的 value，并且该类型尚未展开
            if (valueMap.containsKey(fieldType) && !expandedTypes.contains(fieldType)) {
                Value value = valueMap.get(fieldType);

                // 展开 value 中的字段并添加到 fields 列表
                for (Field valueField : value.getFields()) {
                    fields.add(Field.builder()
                            .name(fieldName + StringUtils.capitalize(valueField.getName()))
                            .type(valueField.getType())
                            .description(valueField.getDescription())
                            .nullable(valueField.getNullable())
                            .build());
                }
                // 将已经展开的类型记录下来，避免后续重复展开
                expandedTypes.add(fieldType);
                continue;
            }
            fields.add(field);
        }
        return fields;
    }

    /**
     * 生成类属性定义代码模版
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     * @param className     类名
     */
    public static void fields(StringBuilder stringBuilder, Set<String> entityNames, Set<String> valueNames, List<Field> fields, String className) {

        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("字段列表不能为空");
        }
        Set<String> definedFieldNames = new HashSet<>();

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType();
            String generics = generics(fieldType);
            stringBuilder.append("    /**\n").append("     * ").append(field.getDescription()).append("\n     */\n");
            if (!className.endsWith("Aggregate") && !className.endsWith("Entity")) {
                if (entityNames.contains(generics)) {
                    fieldType = fieldType.replace("Entity", getLastCamelCase(className, 1));
                }

                if (className.endsWith("Record")) {
                    if (fieldName.equals(id(className, 1)) || fieldName.equals("associationId")) {
                        stringBuilder.append("    @TableId\n");
                    }
                    if (fieldType.startsWith("List<")) {
                        stringBuilder
                                .append("    @TableField(exist = false)\n")
                                .append("    @Builder.Default\n")
                                .append("    private final ").append(fieldType).append(" ").append(fieldName).append(" = new ArrayList<>();\n\n");
                        continue;
                    } else if (entityNames.contains(generics)) {
                        stringBuilder.append("    @TableField(exist = false)\n");
                    }
                    if (fieldName.equals("createAt")) {
                        stringBuilder.append("    @TableField(fill = FieldFill.INSERT)\n");
                    }
                    if (fieldName.equals("modifyAt")) {
                        stringBuilder.append("    @TableField(fill = FieldFill.INSERT_UPDATE)\n");
                    }
                    stringBuilder.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
                    definedFieldNames.add(field.getName());
                    continue;
                }
                stringBuilder.append("    private final ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
            } else {
                if (fieldName.equals(id(className, 1)) || fieldName.equals("createUserId") || fieldType.startsWith("List<")) {
                    stringBuilder.append("    private final ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
                } else {
                    stringBuilder.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
                }
            }
            definedFieldNames.add(field.getName());
        }

        if (className.endsWith("AssociationRecord") && !definedFieldNames.contains("associationId")) {
            throw new IllegalArgumentException("缺少主键字段 associationId ");
        }
        if (className.endsWith("Aggregate") || className.endsWith("Entity") || (className.endsWith("Record") && !className.endsWith("AssociationRecord"))) {
            String id = id(className, 1);
            if (!definedFieldNames.contains(id)) {
                throw new IllegalArgumentException("缺少主键字段 " + id);
            }
        }
    }

    /**
     * 生成枚举属性定义代码模板
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     */
    public static void enumFields(StringBuilder stringBuilder, List<Field> fields) {

        for (Field field : fields) {
            stringBuilder.append("    private final ").append(field.getType()).append(" ").append(field.getName()).append(";\n\n");
        }
    }

    /**
     * 生成枚举值定义代码模板
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     * @param className     类名
     */
    public static void enumValues(StringBuilder stringBuilder, List<EnumValue> values, List<Field> fields, String className) {

        String codeType = fields.get(0).getType();
        if (!codeType.equals("String") && !codeType.equals("Integer") && !codeType.equals("Long")) {
            throw new IllegalArgumentException("无法识别的枚举类型");
        }

        for (EnumValue value : values) {

            String fieldName = value.getCode();
            String description = "\"" + value.getDescription() + "\"";
            if (codeType.equals("String")) {
                fieldName = "\"" + fieldName + "\"";
            }
            stringBuilder.append("    ").append(value.getCode()).append("(").append(fieldName).append(", ").append(description).append("),\n");
        }
        // 移除最后一个多余的逗号
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        stringBuilder.append(";\n\n");

    }
}
