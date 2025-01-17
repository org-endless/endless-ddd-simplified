package org.endless.ddd.simplified.generator.generators;

import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.interfaceDefine;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.*;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.exchangeSuffix;

/**
 * RepositoryGenerator
 * <p>
 * create 2024/10/18 08:41
 * <p>
 * update 2024/10/18 08:41
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class RepositoryGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {
        String classDescription = aggregate.getDescription() + "聚合仓储接口";
        generate(aggregate, aggregate.getAggregateName(), false, classDescription);
    }

    public void generateAggregateQuery(Aggregate aggregate) throws Exception {
        String classDescription = aggregate.getDescription() + "聚合查询仓储接口";
        generate(aggregate, aggregate.getAggregateName(), true, classDescription);
    }

    public void generateEntityQuery(Aggregate aggregate, Entity entity) throws Exception {
        String classDescription = entity.getDescription() + "实体查询仓储接口";
        generate(aggregate, entity.getName(), true, classDescription);
    }

    private void generate(Aggregate aggregate, String generics, Boolean isQuery, String classDescription) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        String className, domainPackage, subPackage, packageName, superClassName, superClassNameWithGenerics;
        String rootPath = aggregate.getRootPath();

        if (!isQuery) {
            className = exchangeSuffix(generics, "Repository", 1);
            domainPackage = domainPackage(aggregate);
            subPackage = "domain.anticorruption";
            packageName = domainPackage + "." + subPackage;

            superClassName = superClassName(aggregate.getServiceName(), className, 1);
            superClassNameWithGenerics = superClassNameWithGenerics(superClassName, generics);
        } else {
            className = exchangeSuffix(generics, "QueryRepository", 1);
            domainPackage = domainPackage(aggregate);
            subPackage = "application.query.anticorruption";
            packageName = domainPackage + "." + subPackage;
            String superClassNameQueryRepository = superClassName(aggregate.getServiceName(), className, 2);
            String superClassNameRepository = exchangeSuffix(className, "QueryRepository", "Repository");
            if (generics.endsWith("Aggregate")) {
                superClassName = superClassNameRepository + ", " + superClassNameQueryRepository;
                superClassNameWithGenerics = superClassNameRepository + ", " + superClassNameWithGenerics(superClassNameQueryRepository, generics);

            } else {
                superClassName = superClassNameQueryRepository;
                superClassNameWithGenerics = superClassNameWithGenerics(superClassNameQueryRepository, generics);
            }
        }

        packageHeader(stringBuilder, packageName);
        if (!isQuery) {
            importHeaderRepository(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage, subPackage);
        } else {
            importHeaderQueryRepository(stringBuilder, servicePackage(aggregate), domainPackage, subPackage);
        }
        comment(stringBuilder, className, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        interfaceDefine(stringBuilder, className, superClassNameWithGenerics);

        stringBuilder.append("\n");
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }
}
