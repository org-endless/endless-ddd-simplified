package org.endless.ddd.simplified.generator.components.generators;

import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.classDefine;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.addDefaultFields;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.fields;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.importHeaderEntity;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.packageHeader;
import static org.endless.ddd.simplified.generator.template.MethodTemplate.*;
import static org.endless.ddd.simplified.generator.template.ValidateTemplate.validate;
import static org.endless.ddd.simplified.generator.template.ValidateTemplate.validateMethods;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;

/**
 * AggregateGenerator
 * <p>
 * create 2024/09/16 20:49
 * <p>
 * update 2024/09/16 20:49
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class EntityGenerator {

    /**
     * 生成聚合类
     *
     * @param aggregate 配置信息
     * @throws IOException IO异常
     */
    public void generateAggregate(Aggregate aggregate) throws Exception {
        if (aggregate != null && aggregate.getFields() != null && !aggregate.getFields().isEmpty() && aggregate.getAggregateName() != null) {
            String classDescription = aggregate.getDescription() + "聚合根";
            generate(aggregate, aggregate.getFields(), aggregate.getAggregateName(), classDescription);
        } else {
            throw new IllegalArgumentException("聚合格式定义错误");
        }
    }

    /**
     * 生成实体类
     *
     * @param aggregate 配置信息
     * @param entity    实体信息
     * @throws IOException IO异常
     */
    public void generateEntity(Aggregate aggregate, Entity entity) throws Exception {
        if (entity != null && entity.getFields() != null && !entity.getFields().isEmpty() && entity.getName() != null) {
            String classDescription = entity.getDescription() + "实体";
            generate(aggregate, entity.getFields(), entity.getName(), classDescription);
        } else {
            throw new IllegalArgumentException("实体格式定义错误");
        }
    }

    /**
     * 代码生成器
     *
     * @param aggregate 聚合
     * @param fields    属性列表
     * @param className 类名
     * @throws IOException IO异常
     */
    private void generate(Aggregate aggregate, List<Field> fields, String className, String classDescription) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String domainPackage = domainPackage(aggregate);
        String subPackage = "domain.entity";
        String packageName = domainPackage + "." + subPackage;
        String superClassName = superClassName(aggregate.getServiceName(), className, 1);
        List<Field> newFields = addDefaultFields(new ArrayList<>(fields), className);
        Set<String> entityNames = entityNames(aggregate);
        Set<String> valueNames = valueNames(aggregate);
        String rootPath = aggregate.getRootPath();

        packageHeader(stringBuilder, packageName);
        importHeaderEntity(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage, subPackage, !valueNames.isEmpty(), !enumNames(aggregate).isEmpty());
        comment(stringBuilder, className, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        classDefine(stringBuilder, className, superClassName);
        fields(stringBuilder, entityNames, valueNames, newFields, className);
        create(stringBuilder, newFields, className);
        remove(stringBuilder, entityNames, newFields, className, classDescription);
        addItem(stringBuilder, entityNames, newFields, className);
        removeItem(stringBuilder, entityNames, newFields, className);
        validate(stringBuilder, newFields, className);
        validateMethods(stringBuilder, newFields, className);
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }
}
