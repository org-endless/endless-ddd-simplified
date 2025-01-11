package org.endless.ddd.simplified.generator.generators;

import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.endless.ddd.simplified.generator.object.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Value;
import org.endless.ddd.simplified.generator.utils.StringTools;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.classDefineRecord;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.*;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.importHeaderRecord;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.packageHeader;
import static org.endless.ddd.simplified.generator.template.MethodTemplate.*;
import static org.endless.ddd.simplified.generator.template.ValidateTemplate.validate;
import static org.endless.ddd.simplified.generator.template.ValidateTemplate.validateMethods;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.*;

/**
 * DataRecordGenerator
 * <p>
 * create 2024/09/18 11:42
 * <p>
 * update 2024/09/18 11:42
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class DataRecordGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {
        String className = exchangeSuffix(aggregate.getAggregateName(), "Record", 1);
        String contextTableName = aggregate.getContextName();
        String tableName = aggregate.getContextName() + "_" + snakeCase(removeSuffix(className, "Record")).replace(contextTableName + "_", "").replace("_" + contextTableName, "");
        String classDescription = aggregate.getDescription() + "数据库记录实体";

        generate(aggregate, aggregate.getFields(), className, tableName, aggregate.getAggregateName(), aggregate.getDescription(), classDescription);
    }

    public void generateEntity(Aggregate aggregate, Entity entity) throws Exception {
        String className = exchangeSuffix(entity.getName(), "Record", 1);
        String contextTableName = aggregate.getContextName();
        String domainTableName = snakeCase(aggregate.getDomainName());
        String entityTableName = snakeCase(removeSuffix(className, "Record"));
        String tableName = contextTableName + "_"
                + domainTableName.replace("_" + aggregate.getContextName(), "").replace(contextTableName + "_", "") + "_"
                + entityTableName.replace("_" + domainTableName, "").replace(domainTableName + "_", "");
        String classDescription = entity.getDescription() + "数据库记录实体";

        generate(aggregate, entity.getFields(), className, tableName, entity.getName(), aggregate.getDescription(), classDescription);
    }

    protected void generate(Aggregate aggregate, List<Field> fields, String className, String tableName, String generics, String genericDescription, String classDescription) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        String domainPackage = domainPackage(aggregate);
        String subPackage = "infrastructure.data.record";
        String packageName = domainPackage + "." + subPackage;
        String superClassName = superClassName(aggregate.getServiceName(), className, 1);
        String superClassNameWithGenerics = superClassNameWithGenerics(superClassName, generics);
        Set<String> entityNames = entityNames(aggregate);
        Set<String> valueNames = valueNames(aggregate);
        List<Value> values = aggregate.getValues();
        List<Field> defaultFields = addDefaultFields(fields, className);
        List<Field> newFields;
        String aggregateName = aggregate.getAggregateName();
        newFields = addDefaultFields(recordFields(fields, values, aggregateName, className, generics, genericDescription), className);
        String rootPath = aggregate.getRootPath();

        packageHeader(stringBuilder, packageName);
        importHeaderRecord(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage, subPackage, !valueNames.isEmpty(), !enumNames(aggregate).isEmpty());
        comment(stringBuilder, className, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        classDefineRecord(stringBuilder, className, superClassNameWithGenerics, tableName);
        fields(stringBuilder, entityNames, valueNames, newFields, className);
        from(stringBuilder, entityNames, values, defaultFields, aggregateName, className, generics);
        to(stringBuilder, entityNames, values, defaultFields, className, generics);
        if (className.endsWith("AssociationRecord")) {
            remove(stringBuilder, entityNames, fields, className, classDescription);
        }
        addItem(stringBuilder, entityNames, newFields, className);
        validate(stringBuilder, newFields, className);
        validateMethods(stringBuilder, newFields, className);
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
        generateDDL(newFields, domainPackage, className, tableName, classDescription, rootPath);
    }

    protected void generateDDL(List<Field> fields, String domainPackage, String className, String tableName, String classDescription, String rootPath) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String subPackage = "infrastructure.data.persistence.sql";
        String packageName = domainPackage + "." + subPackage;
        stringBuilder
                .append("DROP TABLE IF EXISTS ").append(tableName).append(";\n")
                .append("CREATE TABLE ").append(tableName).append(" (\n");
        for (Field field : fields) {
            String fieldType = field.type();
            String fieldName = field.name();
            String fieldSqlType = getSqlType(fieldType, field.description());
            String nullAble = field.nullable() ? "NULL" : "NOT NULL";
            if (fieldName.equals("createAt") || fieldName.equals("modifyAt")) {
                nullAble = "NOT NULL";
            }
            String primaryKey = "";
            if (className.endsWith("AssociationRecord")) {
                if (fieldName.equals("associationId")) {
                    primaryKey = " PRIMARY KEY";
                }
            } else if (fieldName.equals(id(className, 1))) {
                primaryKey = " PRIMARY KEY";
            } else if (fieldName.equals("removeAt")) {
                primaryKey = " DEFAULT 0";
            }
            if (StringUtils.hasText(fieldSqlType)) {
                stringBuilder.append("    ").append(snakeCase(fieldName)).append(" ").append(fieldSqlType).append(" ").append(nullAble).append(primaryKey).append(" COMMENT '").append(field.description()).append("'").append(",\n");
            }
        }
        classDescription = classDescription.replace("数据库记录实体", "表");
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("\n) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '").append(classDescription).append("';");
        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }

    private String getSqlType(String fieldType, String fieldDescription) {
        String fieldSqlType = switch (fieldType) {
            case "String" -> "VARCHAR(255)";
            case "Integer" -> "INT";
            case "Long" -> "BIGINT";
            case "Double" -> "DOUBLE";
            case "Float" -> "FLOAT";
            case "Boolean" -> "BOOLEAN";
            case "BigDecimal" ->
                    "DECIMAL(" + StringTools.toDecimal(fieldDescription).get("precision") + ", " + StringTools.toDecimal(fieldDescription).get("scale") + " , 2)";
            default -> "";
        };
        if (fieldType.endsWith("Enum")) {
            fieldSqlType = "VARCHAR(255)";
        }
        return fieldSqlType;
    }
}
