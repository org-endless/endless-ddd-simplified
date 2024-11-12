package org.endless.ddd.simplified.generator.generators;

import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.endless.ddd.simplified.generator.object.entity.Field;
import org.endless.ddd.simplified.generator.utils.DDDUtils;
import org.endless.ddd.simplified.generator.utils.StringTools;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.ConstructorTemplate.allArgsConstructor;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.classDefine;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.fields;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.importHeaderDataManager;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.packageHeader;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.exchangeSuffix;
import static org.endless.ddd.simplified.generator.utils.StringTools.removeSuffix;

/**
 * DataManagerGenerator
 * <p>
 * create 2024/09/16 12:51
 * <p>
 * update 2024/09/16 12:51
 *
 * @since 2.0.0
 */
public class DataManagerGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {

        String aggregateName = aggregate.getAggregateName();
        String className = exchangeSuffix(aggregateName, "DataManager", 1);
        String recordName = exchangeSuffix(aggregateName, "Record", 1);
        String superRepository = exchangeSuffix(aggregateName, "Repository", 1);
        String superQueryRepository = exchangeSuffix(aggregateName, "QueryRepository", 1);
        String superDataManager = superClassName(aggregate.getServiceName() + "Aggregate", className, 2);
        String superClassName = superRepository + ", " + superQueryRepository + ", " + superDataManager;
        String superClassNameWithGenerics = superRepository + ", " + superQueryRepository + ", " + superClassNameWithGenerics(superDataManager, recordName, aggregateName);

        generate(aggregate, className, superClassName, superClassNameWithGenerics, aggregate.getDescription() + "聚合");
    }

    public void generateEntity(Aggregate aggregate, Entity entity) throws Exception {

        String entityName = entity.getName();
        String className = exchangeSuffix(entityName, "DataManager", 1);
        String recordName = exchangeSuffix(entityName, "Record", 1);
        String superQueryRepository = exchangeSuffix(entityName, "QueryRepository", 1);
        String superDataManager = superClassName(aggregate.getServiceName() + "Entity", className, 2);
        String superClassName = superQueryRepository + ", " + superDataManager;
        String superClassNameWithGenerics = superQueryRepository + ", " + superClassNameWithGenerics(superDataManager, recordName, entityName);
        generate(aggregate, className, superClassName, superClassNameWithGenerics, entity.getDescription() + "实体");
    }

    private void generate(Aggregate aggregate, String className, String superClassName, String superClassNameWithGenerics, String description) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String domainPackage = domainPackage(aggregate);
        String subPackage = "infrastructure.data.manager";
        String packageName = domainPackage + "." + subPackage;
        String rootPath = aggregate.getRootPath();
        String classDescription = description + "数据管理器";

        String mapperName = exchangeSuffix(className, "Mapper", 2);
        String aggregateName = exchangeSuffix(className, "Aggregate", 2);
        List<Field> fields = new ArrayList<>();
        fields.add(Field.builder().name(StringUtils.uncapitalize(mapperName)).type(mapperName).description(description + " Mybatis-Plus 数据访问对象").nullable(false).build());
        if (aggregateName.equals(aggregate.getAggregateName()) && aggregate.getEntities() != null && !aggregate.getEntities().isEmpty()) {
            for (Entity entity : aggregate.getEntities()) {
                String entityMapper = exchangeSuffix(entity.getName(), "Mapper", 1);
                fields.add(Field.builder().name(StringUtils.uncapitalize(entityMapper)).type(entityMapper).description(entity.getDescription() + "实体 Mybatis-Plus 数据访问对象").nullable(false).build());
            }
        }
        for (Field aggregateField : aggregate.getFields()) {
            String aggregateFieldType = aggregateField.getType();
            if (("List<String>".equals(aggregateFieldType) || "List<Long>".equals(aggregateFieldType)) && description.endsWith("聚合")) {
                String associationClassName = StringUtils.capitalize(removeSuffix(aggregateField.getName(), "Ids"));
                String associationMapper = removeSuffix(aggregateName, "Aggregate") + associationClassName + "AssociationMapper";
                String associationDescription = aggregate.getDescription() + "-" + removeSuffix(aggregateField.getDescription(), "ID列表");
                fields.add(Field.builder().name(StringUtils.uncapitalize(associationMapper)).type(associationMapper).description(associationDescription + "关系 Mybatis-Plus 数据访问对象").nullable(false).build());
            }
        }
        packageHeader(stringBuilder, packageName);
        importHeaderDataManager(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage, subPackage);
        comment(stringBuilder, className, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        classDefine(stringBuilder, className, superClassNameWithGenerics);
        fields(stringBuilder, entityNames(aggregate), valueNames(aggregate), fields, className);
        allArgsConstructor(stringBuilder, fields, className);
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }
}
