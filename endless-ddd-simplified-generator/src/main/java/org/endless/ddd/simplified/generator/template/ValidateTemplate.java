package org.endless.ddd.simplified.generator.template;

import org.endless.ddd.simplified.generator.object.entity.Field;
import org.endless.ddd.simplified.generator.utils.DDDUtils;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * ValidateTemplate
 * <p>
 * create 2024/09/19 01:18
 * <p>
 * update 2024/09/19 01:18
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class ValidateTemplate {

    /**
     * 生成校验方法
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     */
    public static void validate(StringBuilder stringBuilder, List<Field> fields, String className) {

        stringBuilder
                .append("    @Override\n")
                .append("    public ").append(className).append(" validate() {\n");
        for (Field field : fields) {
            if (!field.nullable() || DDDUtils.isValidateDecimalField(field.type(), field.name(), field.description())) {
                stringBuilder.append("        validate").append(StringUtils.capitalize(field.name())).append("();\n");
            }
        }
        stringBuilder
                .append("        return this;\n")
                .append("    }\n\n");
    }

    /**
     * 按属性生成校验方法
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     * @param className     类名
     */
    public static void validateMethods(StringBuilder stringBuilder, List<Field> fields, String className) {

        for (Field field : fields) {
            String fieldName = field.name();
            String fieldType = field.type();
            String fieldDescription = field.description();

            // 检查条件，如果符合，则生成校验方法
            if (!field.nullable() || DDDUtils.isValidateDecimalField(fieldType, fieldName, fieldDescription)) {

                stringBuilder.append("    private void validate").append(StringUtils.capitalize(fieldName)).append("() {\n");

                // 针对不同类型进行校验逻辑生成
                if (DDDUtils.isValidateDecimalField(fieldType, fieldName, fieldDescription) && field.nullable()) {
                    appendBigDecimalNullableValidation(stringBuilder, className, fieldName, fieldDescription);
                } else if (DDDUtils.isValidateDecimalField(fieldType, fieldName, fieldDescription)) {
                    appendBigDecimalValidation(stringBuilder, className, fieldName, fieldDescription);
                } else if ("String".equals(fieldType)) {
                    appendStringValidation(stringBuilder, className, fieldName, fieldDescription);
                } else if (isNumericType(fieldType)) {
                    appendNumericValidation(stringBuilder, className, fieldName, fieldDescription);
                } else if (fieldType.startsWith("List<")) {
                    appendListValidation(stringBuilder, className, fieldName, fieldDescription);
                } else {
                    appendDefaultValidation(stringBuilder, className, fieldName, fieldDescription);
                }

                stringBuilder.append("    }\n\n");
            }
        }
    }

    private static void appendStringValidation(StringBuilder stringBuilder, String className, String fieldName, String fieldDescription) {
        stringBuilder.append("        if (!StringUtils.hasText(").append(fieldName).append(")) {\n");
        validateException(stringBuilder, className, "不能为空\"", fieldDescription);
        stringBuilder.append("        }\n");
    }

    private static void appendNumericValidation(StringBuilder stringBuilder, String className, String fieldName, String fieldDescription) {
        stringBuilder.append("        if (").append(fieldName).append(" == null || ").append(fieldName).append(" < 0) {\n");
        validateException(stringBuilder, className, "不能为 null 或小于 0，当前值为: \" + " + fieldName, fieldDescription);
        stringBuilder.append("        }\n");
    }

    private static void appendListValidation(StringBuilder stringBuilder, String className, String fieldName, String fieldDescription) {
        stringBuilder.append("        if (CollectionUtils.isEmpty(").append(fieldName).append(")) {\n");
        validateException(stringBuilder, className, "不能为空\"", fieldDescription);
        stringBuilder.append("        }\n");
    }

    private static void appendDefaultValidation(StringBuilder stringBuilder, String className, String fieldName, String fieldDescription) {
        stringBuilder.append("        if (").append(fieldName).append(" == null) {\n");
        validateException(stringBuilder, className, "不能为 null \"", fieldDescription);
        stringBuilder.append("        }\n");
    }

    private static void appendBigDecimalValidation(StringBuilder stringBuilder, String className, String fieldName, String fieldDescription) {
        if (fieldName.endsWith("Amount") || fieldName.equals("amount")) {
            stringBuilder.append("        Decimal.validateAmount(").append(fieldName).append(");\n");
        }
        if (fieldName.endsWith("Rate") || fieldName.equals("rate")) {
            stringBuilder.append("        Decimal.validateRate(").append(fieldName).append(");\n");
        }
        if (fieldName.endsWith("Percentage") || fieldName.equals("percentage")) {
            stringBuilder.append("        Decimal.validatePercentage(").append(fieldName).append(");\n");
        }
    }

    private static void appendBigDecimalNullableValidation(StringBuilder stringBuilder, String className, String fieldName, String fieldDescription) {
        stringBuilder.append("        if (").append(fieldName).append(" != null) {\n");
        if (fieldName.endsWith("Amount") || fieldName.equals("amount")) {
            stringBuilder.append("            Decimal.validateAmount(").append(fieldName).append(");\n");
        }
        if (fieldName.endsWith("Rate") || fieldName.equals("rate")) {
            stringBuilder.append("            Decimal.validateRate(").append(fieldName).append(");\n");
        }
        if (fieldName.endsWith("Percentage") || fieldName.equals("percentage")) {
            stringBuilder.append("            Decimal.validatePercentage(").append(fieldName).append(");\n");
        }
        stringBuilder.append("        }\n");
    }

    private static void validateException(StringBuilder stringBuilder, String className, String message, String fieldDescription) {
        if (className.endsWith("Aggregate")) {
            stringBuilder.append("            throw new AggregateValidateException(\"").append(fieldDescription).append(message).append(");\n");
        } else if (className.endsWith("Entity")) {
            stringBuilder.append("            throw new EntityValidateException(\"").append(fieldDescription).append(message).append(");\n");
        } else if (className.endsWith("Value")) {
            stringBuilder.append("            throw new ValueValidateException(\"").append(fieldDescription).append(message).append(");\n");
        } else if (className.endsWith("CTransfer")) {
            stringBuilder.append("            throw new CommandTransferValidateException(\"").append(fieldDescription).append(message).append(");\n");
        } else if (className.endsWith("QTransfer")) {
            stringBuilder.append("            throw new QueryTransferValidateException(\"").append(fieldDescription).append(message).append(");\n");
        } else if (className.endsWith("DTransfer")) {
            stringBuilder.append("            throw new DrivenTransferValidateException(\"").append(fieldDescription).append(message).append(");\n");
        } else if (className.endsWith("Record")) {
            stringBuilder.append("            throw new DataRecordValidateException(\"").append(fieldDescription).append(message).append(");\n");
        }
    }

    private static boolean isNumericType(String fieldType) {
        return "Long".equals(fieldType) || "Integer".equals(fieldType) || "Double".equals(fieldType);
    }
}
