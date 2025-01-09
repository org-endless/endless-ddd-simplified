package org.endless.ddd.simplified.generator.template;

import org.endless.ddd.simplified.generator.object.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Value;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.*;

/**
 * MethodTemplate
 * <p>
 * create 2024/09/29 17:13
 * <p>
 * update 2024/09/29 17:13
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class MethodTemplate {

    /**
     * 生成创建函数代码模版
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     * @param className     类名
     */
    public static void create(StringBuilder stringBuilder, List<Field> fields, String className) {

        stringBuilder
                .append("    ").append(access(className, false, false)).append(" static ").append(className).append(" create(").append(className).append("Builder builder) {\n")
                .append("        return builder\n");

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType();
            if (field.getNullable() && !fieldType.startsWith("List<")) {
                continue;
            }

            // 函数主体
            if (id(className, 1).equals(fieldName)) {
                stringBuilder.append("                .").append(fieldName).append("(IdGenerator.of())\n");
            } else if (fieldName.equals("isRemoved")) {
                stringBuilder.append("                .").append(fieldName).append("(false)\n");
            } else if (fieldName.equals("modifyUserId")) {
                stringBuilder.append("                .").append(fieldName).append("(builder.createUserId)\n");
            } else if (fieldType.startsWith("List<")) {
                stringBuilder.append("                .").append(fieldName).append("(builder.").append(fieldName).append(" == null ? new ArrayList<>() : new ArrayList<>(builder.").append(fieldName).append("))\n");
            } else {
                stringBuilder.append("                .").append(fieldName).append("(builder.").append(fieldName).append(")\n");
            }
        }
        stringBuilder
                .append("            .innerBuild()\n")
                .append("            .validate();\n")
                .append("    }\n\n");
    }

    /**
     * 生成删除函数代码模版
     *
     * @param stringBuilder 字符串构建器
     * @param fields        属性列表
     * @param className     类名
     */
    public static void remove(StringBuilder stringBuilder, Set<String> entityNames, List<Field> fields, String className, String classDescription) {


        stringBuilder.append("    ").append(access(className, false, true)).append(" ").append(className).append(" remove(String modifyUserId) {\n");


        // 判断是否已经被删除
        stringBuilder.append("        if (this.isRemoved) {\n");
        if (className.endsWith("Aggregate")) {
            stringBuilder.append("            throw new AggregateRemoveException(\"已经被删除的聚合根<").append(classDescription).append(">不能再次删除, ID: \" + ").append(id(className, 1)).append(");\n");
        } else if (className.endsWith("Entity")) {
            stringBuilder.append("            throw new EntityRemoveException(\"已经被删除的实体<").append(classDescription).append(">不能再次删除, ID: \" + ").append(id(className, 1)).append(");\n");
        } else if (className.endsWith("Record")) {
            stringBuilder.append("            throw new DataRecordRemoveException(\"已经被删除的数据库记录<").append(classDescription).append(">不能再次删除, ID: \" + ").append("associationId").append(");\n");
        }
        stringBuilder.append("        }\n");

        // canRemove方法判断是否可以删除
        stringBuilder.append("        if (!canRemove()) {\n");
        if (className.endsWith("Aggregate")) {
            stringBuilder.append("            throw new AggregateRemoveException(\"聚合根<").append(classDescription).append(">处于不可删除状态, ID: \" + ").append(id(className, 1)).append(");\n");
        } else if (className.endsWith("Entity")) {
            stringBuilder.append("            throw new EntityRemoveException(\"实体<").append(classDescription).append(">处于不可删除状态, ID: \" + ").append(id(className, 1)).append(");\n");
        } else if (className.endsWith("Record")) {
            stringBuilder.append("            throw new DataRecordRemoveException(\"数据库记录<").append(classDescription).append(">处于不可删除状态, ID: \" + ").append("associationId").append(");\n");
        }
        stringBuilder.append("        }\n");

        // 删除方法主体
        for (Field field : fields) {

            String fieldType = field.getType();
            String fieldName = field.getName();
            String generics = generics(fieldType);
            if (entityNames.contains(fieldType)) {
                stringBuilder.append("        this.").append(fieldName).append(".remove();\n");
            }
            if (fieldType.startsWith("List<")) {
                String singularField = removePrefix(fieldName, "s");
                if (entityNames.contains(generics)) {
                    stringBuilder.append("        this.").append(fieldName).append(".forEach(").append(singularField).append(" -> ").append(singularField).append(".remove(modifyUserId));\n");
                } else {
                    stringBuilder.append("        this.").append(fieldName).append(".clear();\n");
                }
            }
        }
        stringBuilder
                .append("        this.isRemoved = true;\n")
                .append("        this.modifyUserId = modifyUserId;\n")
                .append("        return this;\n")
                .append("    }\n\n");

        // canRemove方法
        stringBuilder
                .append("    private boolean canRemove() {\n")
                .append("        return true;\n")
                .append("    }\n\n");
    }

    /**
     * 生成增加子实体函数代码模版
     *
     * @param stringBuilder 字符串构建器
     * @param fields        属性列表
     * @param className     类名
     */
    public static void addItem(StringBuilder stringBuilder, Set<String> entityNames, List<Field> fields, String className) {

        for (Field field : fields) {

            String fieldType = field.getType();
            String fieldTypeEntity = field.getType();
            String fieldName = field.getName();
            String generics = generics(fieldType);

            if (className.endsWith("Record") && !fieldType.startsWith("List<String>")) {
                fieldType = exchangeGenerics(fieldType, "Entity", "Record");
                generics = exchangeSuffix(generics, "Entity", "Record");
            }

            if (fieldType.startsWith("List<")) {

                // 列表增加子实体方法
                appendAddMethod(stringBuilder, className, generics, fieldName);
                // 列表增加子实体列表方法
                appendAddAllMethod(stringBuilder, className, fieldType, fieldName);
            } else if (entityNames.contains(fieldTypeEntity)) {
                appendAddMethod(stringBuilder, className, generics, fieldName);

            }
        }
    }

    private static void appendAddMethod(StringBuilder stringBuilder, String className, String generics, String fieldName) {
        String singularField = removeSuffix(fieldName, "s");
        String method = "add" + StringUtils.capitalize(singularField);
        String modifyUserIdParma = ", String modifyUserId";
        if (className.endsWith("Record")) {
            modifyUserIdParma = "";
        }
        stringBuilder.append("    ").append(access(className, false, true)).append(" ").append(className).append(" ").append(method).append("(").append(generics).append(" ").append(singularField).append(modifyUserIdParma).append(") {\n");

        if ("String".equals(generics)) {
            stringBuilder.append("        if (!StringUtils.hasText(").append(singularField).append(")) {\n");
        } else {
            stringBuilder.append("        if (").append(singularField).append(" == null) {\n");
        }

        addException(stringBuilder, className, generics);

        stringBuilder.append("        }\n");
        if (removeSuffix(generics, 1).equals(StringUtils.capitalize(fieldName))) {
            stringBuilder.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");

        } else {
            stringBuilder.append("        this.").append(fieldName).append(".add(").append(singularField).append(");\n");

        }
        if (!className.endsWith("Record")) {
            stringBuilder.append("        this.modifyUserId = modifyUserId;\n");
        }
        stringBuilder
                .append("        return this;\n")
                .append("    }\n\n");
    }

    private static void appendAddAllMethod(StringBuilder stringBuilder, String className, String fieldType, String fieldName) {
        String method = "add" + StringUtils.capitalize(fieldName);
        String modifyUserIdParma = ", String modifyUserId";
        if (className.endsWith("Record")) {
            modifyUserIdParma = "";
        }
        stringBuilder
                .append("    ").append(access(className, false, true)).append(" ").append(className).append(" ").append(method).append("(").append(fieldType).append(" ").append(fieldName).append(modifyUserIdParma).append(") {\n")
                .append("        if (CollectionUtils.isEmpty(").append(fieldName).append(")) {\n");

        addListException(stringBuilder, className, fieldType);
        stringBuilder.append("        }\n");

        if ("String".equals(fieldType)) {
            stringBuilder.append("        for(String ").append(fieldName).append(" : ").append(fieldName).append(") {\n")
                    .append("            if (!StringUtils.hasText(").append(fieldName).append(")) {\n");
            addStringListException(stringBuilder, className, fieldName);
            stringBuilder.append("            }\n")
                    .append("        }\n");
        }
        stringBuilder
                .append("        this.").append(fieldName).append(".addAll(").append(fieldName).append(");\n");
        if (!className.endsWith("Record")) {
            stringBuilder.append("        this.modifyUserId = modifyUserId;\n");
        }
        stringBuilder.append("        return this;\n")
                .append("    }\n\n");
    }

    private static void addException(StringBuilder stringBuilder, String className, String type) {
        if (className.endsWith("Aggregate")) {
            stringBuilder.append("            throw new AggregateAddItemException(\"聚合根要添加的子实体 ").append(type).append(" 不能为 null\");\n");
        } else if (className.endsWith("Record")) {
            stringBuilder.append("            throw new DataRecordAddItemException(\"数据库实体要添加的子实体 ").append(type).append(" 不能为 null\");\n");
        }
    }

    private static void addListException(StringBuilder stringBuilder, String className, String fieldName) {
        if (className.endsWith("Aggregate")) {
            stringBuilder.append("                throw new AggregateAddItemException(\"聚合根要添加的子实体列表 ").append(fieldName).append(" 不能为空\");\n");
        } else if (className.endsWith("Record")) {
            stringBuilder.append("                throw new DataRecordAddItemException(\"数据库实体要添加的子实体列表 ").append(fieldName).append(" 不能为空\");\n");
        }
    }

    private static void addStringListException(StringBuilder stringBuilder, String className, String fieldName) {
        if (className.endsWith("Aggregate")) {
            stringBuilder.append("                throw new AggregateAddItemException(\"聚合根要添加的子实体列表 ").append(fieldName).append(" 中包含空字符串或空白字符串\");\n");
        } else if (className.endsWith("Record")) {
            stringBuilder.append("                throw new DataRecordAddItemException(\"数据库实体要添加的子实体列表 ").append(fieldName).append(" 中包含空字符串或空白字符串\");\n");
        }
    }

    public static void removeItem(StringBuilder stringBuilder, Set<String> entityNames, List<Field> fields, String className) {
        for (final Field field : fields) {

            String fieldType = field.getType();
            String fieldName = field.getName();
            String generics = generics(fieldType);
            // 删除子实体方法
            if (entityNames.contains(fieldType) && field.getNullable()) {
                stringBuilder
                        .append("    ").append(access(className, false, true)).append(" ").append(className).append(" remove").append(fieldType).append("(String modifyUserId) {\n")
                        .append("        this.").append(fieldName).append(".remove(modifyUserId);\n")
                        .append("        this.modifyUserId = modifyUserId;\n")
                        .append("        return this;\n")
                        .append("    }\n\n");
            }


            String singularField = removeSuffix(fieldName, "s");
            String singularMethod = "remove" + StringUtils.capitalize(singularField);
            if (fieldType.startsWith("List<") && entityNames.contains(generics)) {
                String idGetter = getter(id(generics, 1));
                // 列表删除子实体方法
                stringBuilder
                        .append("    ").append(access(className, false, true)).append(" ").append(className).append(" ").append(singularMethod).append("(").append(generics).append(" ").append(singularField).append(", String modifyUserId) {\n")
                        .append("        if (").append(singularField).append(" == null) {\n")
                        .append("            throw new AggregateRemoveItemException(\"聚合根要删除的子实体 ").append(generics).append(" 不能为 null\");\n")
                        .append("        }\n")
                        .append("        this.").append(fieldName).append(".stream()\n")
                        .append("                .filter(exist -> exist.").append(idGetter).append("().equals(").append(singularField).append(".").append(idGetter).append("()))\n")
                        .append("                .findFirst()\n")
                        .append("                .orElseThrow(() -> new AggregateRemoveItemException(\"未找到要删除的子实体 ").append(generics).append(" ID: \" + ").append(singularField).append(".").append(idGetter).append("()))\n")
                        .append("                .remove(modifyUserId);\n")
                        .append("        this.modifyUserId = modifyUserId;\n")
                        .append("        return this;\n")
                        .append("    }\n\n");
                String method = "remove" + StringUtils.capitalize(fieldName);
                stringBuilder
                        .append("    ").append(access(className, false, true)).append(" ").append(className).append(" ").append(method).append("(").append(fieldType).append(" ").append(fieldName).append(", String modifyUserId) {\n")
                        .append("        if (CollectionUtils.isEmpty(").append(fieldName).append(")) {\n")
                        .append("            throw new AggregateRemoveItemException(\"聚合根要删除的子实体列表 ").append(fieldType).append(" 不能为空\");\n")
                        .append("        }\n")
                        .append("        Set<String> existIds = this.").append(fieldName).append(".stream()\n")
                        .append("                .map(").append(generics).append("::").append(idGetter).append(")\n")
                        .append("                .collect(Collectors.toSet());\n")
                        .append("        ").append(fieldName).append(".forEach(remove -> {\n")
                        .append("            if (!existIds.contains(remove.").append(idGetter).append("())) {\n")
                        .append("                throw new AggregateRemoveItemException(\"要删除的子实体列表 ").append(fieldType).append(" 中包含不存在的子实体 ID: \" + remove.").append(idGetter).append("());\n")
                        .append("            }\n")
                        .append("            this.").append(fieldName).append(".stream()\n")
                        .append("                    .filter(exist -> exist.").append(idGetter).append("().equals(remove.").append(idGetter).append("()))\n")
                        .append("                    .findFirst()\n")
                        .append("                    .ifPresent(").append(singularField).append(" -> ").append(singularField).append(".remove(modifyUserId));\n")
                        .append("        });\n")
                        .append("        this.modifyUserId = modifyUserId;\n")
                        .append("        return this;\n")
                        .append("    }\n\n");
            } else if ("List<String>".equals(fieldType) || "List<Long>".equals(fieldType)) {
                stringBuilder
                        .append("    ").append(access(className, false, true)).append(" ").append(className).append(" ").append(singularMethod).append("(").append(generics).append(" ").append(singularField).append(") {\n")
                        .append("        if (").append(singularField).append(" == null) {\n")
                        .append("            throw new AggregateRemoveItemException(\"聚合根要删除的子实体 ").append(generics).append(" 不能为 null\");\n")
                        .append("        }\n")
                        .append("        this.").append(fieldName).append(".remove(").append(singularField).append(");\n")
                        .append("        this.modifyUserId = modifyUserId;\n")
                        .append("        return this;\n")
                        .append("    }\n\n");
                // 列表删除子实体列表方法
                String method = "remove" + StringUtils.capitalize(fieldName);
                stringBuilder
                        .append("    ").append(access(className, false, true)).append(" ").append(className).append(" ").append(method).append("(").append(fieldType).append(" ").append(fieldName).append(") {\n")
                        .append("        if (CollectionUtils.isEmpty(").append(fieldName).append(")) {\n")
                        .append("            throw new AggregateRemoveItemException(\"聚合根要删除的子实体列表 ").append(fieldType).append(" 不能为空\");\n")
                        .append("        }\n")
                        .append("        this.").append(fieldName).append(".removeAll(").append(fieldName).append(");\n")
                        .append("        this.modifyUserId = modifyUserId;\n")
                        .append("        return this;\n")
                        .append("    }\n\n");
            }
        }
    }

    public static void from(StringBuilder stringBuilder, Set<String> entityNames, List<Value> values, List<Field> fields, String aggregateName, String className, String generics) {
        Map<String, Value> valueMap = Optional.ofNullable(values)
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.toMap(Value::getName, value -> value));
        Set<String> expandedTypes = new HashSet<>(); // 用于记录已展开的类型
        String param, objectParam, associationParam = "";
        String aggregateId = id(aggregateName, 1);
        if (className.endsWith("AssociationRecord")) {
            associationParam = StringUtils.uncapitalize(removeSuffix(removePrefix(className, removeSuffix(aggregateName, "Aggregate")), "AssociationRecord")) + "Id";
            param = "aggregate, String " + associationParam;
            objectParam = "aggregate";
        } else if (generics.endsWith("Aggregate")) {
            param = "aggregate";
            objectParam = "aggregate";
        } else {
            param = "entity, String " + aggregateId;
            objectParam = "entity";
        }
        stringBuilder
                .append("    ").append(access(generics, false, false)).append(" static ").append(className).append(" from").append("(").append(generics).append(" ").append(param).append(") {\n");
        if (generics.endsWith("Aggregate") && !className.endsWith("AssociationRecord")) {
            stringBuilder.append("        String ").append(aggregateId).append(" = ").append(param).append(".get").append(StringUtils.capitalize(aggregateId)).append("();\n");
        }
        stringBuilder.append("        return ").append(className).append(".builder()\n");
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType();
            String fieldGenerics = generics(fieldType);
            String filedGenericsRecord = exchangeSuffix(fieldGenerics, "Entity", "Record");
            String getField = "get" + StringUtils.capitalize(fieldName);

            if (fieldName.equals("createAt") || fieldName.equals("modifyAt") || fieldName.equals("removeAt")) {
                continue;
            } else if (fieldName.equals(id(className, 1))) {
                if (generics.endsWith("Aggregate")) {
                    stringBuilder.append("                .").append(fieldName).append("(").append(aggregateId).append(")\n");
                } else {
                    stringBuilder.append("                .").append(fieldName).append("(").append(objectParam).append(".get").append(StringUtils.capitalize(fieldName)).append("())\n");
                    stringBuilder.append("                .").append(aggregateId).append("(").append(aggregateId).append(")\n");
                }
            } else if (className.endsWith("AssociationRecord")) {
                if (fieldName.equals("associationId")) {
                    stringBuilder.append("                .").append(fieldName).append("(IdGenerator.of())\n");
                } else if (fieldName.equals(associationParam)) {
                    stringBuilder.append("                .").append(associationParam).append("(").append(associationParam).append(")\n");
                } else {
                    stringBuilder.append("                .").append(fieldName).append("(").append(objectParam).append(".get").append(StringUtils.capitalize(fieldName)).append("())\n");
                }
            } else if (fieldType.startsWith("List<")) {
                if (entityNames.contains(fieldGenerics)) {
                    stringBuilder
                            .append("                .").append(fieldName).append("(").append(objectParam).append(".").append(getField).append("() == null ? new ArrayList<>() : ").append(objectParam).append(".").append(getField).append("().stream()\n")
                            .append("                        .map(record -> ").append(filedGenericsRecord).append(".from(record, ").append(aggregateId).append(")).collect(Collectors.toList()))\n");
                } else {
                    stringBuilder
                            .append("                .").append(fieldName).append("(").append(objectParam).append(".").append(getField).append("() == null ? new ArrayList<>() : new ArrayList<>(").append(objectParam).append(".").append(getField).append("()))\n");
                }
            } else if (valueMap.containsKey(fieldType) && !expandedTypes.contains(fieldType)) {
                Value value = valueMap.get(fieldType);

                for (Field valueField : value.getFields()) {
                    String valueFieldName = valueField.getName();
                    String getValueField = "get" + StringUtils.capitalize(valueFieldName);
                    if (field.getNullable()) {
                        stringBuilder.append("                .").append(fieldName).append(StringUtils.capitalize(valueFieldName)).append("(").append(objectParam).append(".").append(getField).append("() == null ? null : ").append(objectParam).append(".").append(getField).append("().").append(getValueField).append("())\n");
                    } else {
                        stringBuilder.append("                .").append(fieldName).append(StringUtils.capitalize(valueFieldName)).append("(").append(objectParam).append(".").append(getField).append("().").append(getValueField).append("())\n");
                    }

                }
                expandedTypes.add(fieldType);
            } else if (entityNames.contains(fieldGenerics)) {
                stringBuilder.append("                .").append(fieldName).append("(").append(filedGenericsRecord).append(".from(").append(objectParam).append(".").append(getField).append("(), ").append(objectParam).append(".get").append(StringUtils.capitalize(aggregateId)).append("()))\n");
            } else {
                stringBuilder.append("                .").append(fieldName).append("(").append(objectParam).append(".get").append(StringUtils.capitalize(fieldName)).append("())\n");
            }
        }
        stringBuilder
                .append("                .build()\n")
                .append("                .validate();\n")
                .append("    }\n\n");
    }

    public static void to(StringBuilder stringBuilder, Set<String> entityNames, List<Value> values, List<Field> fields, String className, String generics) {

        Map<String, Value> valueMap = Optional.ofNullable(values)
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.toMap(Value::getName, value -> value));
        Set<String> expandedTypes = new HashSet<>(); // 用于记录已展开的类型

        if (className.endsWith("AssociationRecord")) {
            String associationParam = StringUtils.uncapitalize(removeSuffix(removePrefix(className, removeSuffix(generics, "Aggregate")), "AssociationRecord")) + "Id";
            stringBuilder
                    .append("    ").append(access(generics, false, true)).append(" ").append("String to").append("() {\n")
                    .append("        validate();\n")
                    .append("        return ").append(associationParam).append(";\n")
                    .append("    }\n\n");
            return;
        } else {
            stringBuilder
                    .append("    ").append(access(generics, false, true)).append(" ").append(generics).append(" to").append("() {\n")
                    .append("        validate();\n")
                    .append("        return ").append(generics).append(".builder()\n");
        }
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType();
            String fieldGenericsEntity = generics(fieldType);
            String fieldGenerics = fieldGenericsEntity.replace("Entity", getLastCamelCase(className, 1));

            if (fieldName.equals("createAt") || fieldName.equals("modifyAt") || fieldName.equals("removeAt")) {
                continue;
            } else if (fieldType.startsWith("List<")) {
                if (entityNames.contains(fieldGenericsEntity)) {
                    stringBuilder
                            .append("                .").append(fieldName).append("(").append(fieldName).append("== null? new ArrayList<>() : ").append(fieldName).append(".stream()\n")
                            .append("                        .map(").append(fieldGenerics).append("::to).collect(Collectors.toList())").append(")\n");
                } else {
                    stringBuilder.append("                .").append(fieldName).append("(").append(fieldName).append("== null? new ArrayList<>() : new ArrayList<>(").append(fieldName).append("))\n");
                }

            } else if (valueMap.containsKey(fieldType) && !expandedTypes.contains(fieldType)) {
                Value value = valueMap.get(fieldType);
                String valueName = value.getName();
                stringBuilder.append("                .").append(fieldName).append("(").append(valueName).append(".builder()\n");
                for (Field valueFiled : value.getFields()) {
                    String valueFiledName = valueFiled.getName();
                    stringBuilder.append("                        .").append(valueFiledName).append("(").append(fieldName).append(StringUtils.capitalize(valueFiledName)).append(")\n");
                }
                stringBuilder.append("                        .innerBuild())\n");
                expandedTypes.add(fieldType);
            } else if (entityNames.contains(fieldType)) {
                stringBuilder.append("                .").append(fieldName).append("(").append(fieldName).append(".to())\n");
            } else {
                stringBuilder.append("                .").append(fieldName).append("(").append(fieldName).append(")\n");
            }
        }
        stringBuilder
                .append("                .innerBuild();\n")
                .append("    }\n\n");
    }

    /**
     * 枚举FromCode方法生成模板
     *
     * @param stringBuilder 字符串构建器
     * @param fields        字段列表
     */
    public static void fromCode(StringBuilder stringBuilder, List<Field> fields, String className, String classDescription) {
        String codeType = fields.getFirst().getType();
        String codeName = fields.getFirst().getName();
        stringBuilder
                .append("    public static ").append(className).append(" fromCode(").append(codeType).append(" code) {\n")
                .append("        for (").append(className).append(" type : values()) {\n")
                .append("            if (type.").append(codeName).append(".equals(code)) {\n")
                .append("                return type;\n")
                .append("            }\n")
                .append("        }\n")
                .append("        throw new EnumException(\"未知的").append(classDescription).append(": \" + code);\n")
                .append("    }\n\n");
    }

    public static void beanDefine(StringBuilder stringBuilder, Set<String> entityNames, String domainName) {
        String adapter = domainName + "DrivingAdapter";
        String springAdapter = "Spring" + domainName + "DrivingAdapter";
        String commandHandler = domainName + "CommandHandler";
        String queryHandler = domainName + "QueryHandler";
        String repository = domainName + "Repository";
        String queryRepository = domainName + "QueryRepository";
        StringBuilder queryRepositories = new StringBuilder();
        StringBuilder queryRepositoryDefine = new StringBuilder();

        stringBuilder
                .append("    @Lazy\n")
                .append("    @Primary\n")
                .append("    @ConditionalOnMissingBean\n")
                .append("    public @Bean ").append(adapter).append(" ").append(StringUtils.uncapitalize(springAdapter)).append("(").append(commandHandler).append(" commandHandler, ").append(queryHandler).append(" queryHandler) {\n")
                .append("        return new ").append(springAdapter).append("(commandHandler, queryHandler);\n")
                .append("    }\n\n")

                .append("    @Lazy\n")
                .append("    @ConditionalOnMissingBean\n")
                .append("    protected @Bean ").append(commandHandler).append(" ").append(StringUtils.uncapitalize(commandHandler)).append("(").append(repository).append(" repository) {\n")
                .append("        return new ").append(commandHandler).append("Impl(repository);\n")
                .append("    }\n\n");


        queryRepositories.append(StringUtils.uncapitalize(queryRepository));
        queryRepositoryDefine.append(queryRepository).append(" ").append(StringUtils.uncapitalize(queryRepository));
        for (String entityName : entityNames) {
            entityName = removeSuffix(entityName, "Entity");
            queryRepositories.append(", ").append(StringUtils.uncapitalize(entityName)).append("QueryRepository");
            queryRepositoryDefine.append(", ").append(entityName).append("QueryRepository ").append(StringUtils.uncapitalize(entityName)).append("QueryRepository");
        }
        stringBuilder
                .append("    @Lazy\n")
                .append("    @ConditionalOnMissingBean\n")
                .append("    protected @Bean ").append(queryHandler).append(" ").append(StringUtils.uncapitalize(queryHandler)).append("(").append(queryRepositoryDefine).append(") {\n")
                .append("        return new ").append(queryHandler).append("Impl(").append(queryRepositories).append(");\n")
                .append("    }\n\n");
    }
}
