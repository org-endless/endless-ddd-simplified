package org.endless.ddd.simplified.generator.components.generators;

import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.ConstructorTemplate.allArgsConstructor;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.classDefine;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.interfaceDefine;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.fields;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.*;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.exchangeSuffix;

/**
 * QueryHandlerGenerator
 * <p>
 * create 2024/09/16 12:41
 * <p>
 * update 2024/09/16 12:41
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class QueryHandlerGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {

        String interfaceName = exchangeSuffix(aggregate.getAggregateName(), "QueryHandler", 1);
        generateInterface(aggregate, interfaceName);
        generateImpl(aggregate, interfaceName, aggregate.getAggregateName());
    }

    private void generateInterface(Aggregate aggregate, String interfaceName) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String superClassName = superClassName(aggregate.getServiceName(), interfaceName, 2);
        String domainPackage = domainPackage(aggregate);
        String subPackage = "application.query.handler";
        String packageName = domainPackage + "." + subPackage;
        String rootPath = aggregate.getRootPath();
        String classDescription = aggregate.getDescription() + "领域查询处理器";

        packageHeader(stringBuilder, packageName);
        importHeaderQueryHandler(stringBuilder, servicePackage(aggregate), domainPackage, subPackage);
        comment(stringBuilder, interfaceName, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        interfaceDefine(stringBuilder, interfaceName, superClassName);
        stringBuilder.append("\n");
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, interfaceName);
        writeFile(rootPath, packageName, interfaceName, stringBuilder.toString());
    }

    private void generateImpl(Aggregate aggregate, String interfaceName, String generic) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String className = interfaceName + "Impl";
        String domainPackage = domainPackage(aggregate);
        String subPackage = "application.query.handler.impl";
        String packageName = domainPackage + "." + subPackage;
        String rootPath = aggregate.getRootPath();
        String queryRepository = exchangeSuffix(generic, "QueryRepository", 1);
        List<Field> fields = new ArrayList<>();
        fields.add(Field.builder().name(StringUtils.uncapitalize(queryRepository)).type(queryRepository).description(aggregate.getDescription() + "聚合查询仓储接口").nullable(false).build());
        if (aggregate.getEntities() != null && !aggregate.getEntities().isEmpty()) {
            for (Entity entity : aggregate.getEntities()) {
                String entityRepository = exchangeSuffix(entity.getName(), "QueryRepository", 1);
                fields.add(Field.builder().name(StringUtils.uncapitalize(entityRepository)).type(entityRepository).description(entity.getDescription() + "实体查询仓储接口").nullable(false).build());
            }
        }
        String classDescription = aggregate.getDescription() + "领域查询处理器";


        packageHeader(stringBuilder, packageName);
        importHeaderQueryHandlerImpl(stringBuilder, aggregate.getGroupId(), domainPackage, subPackage);
        comment(stringBuilder, className, interfaceName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        classDefine(stringBuilder, className, interfaceName);
        fields(stringBuilder, entityNames(aggregate), valueNames(aggregate), fields, className);
        allArgsConstructor(stringBuilder, fields, className);
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }
}
