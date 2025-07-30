package org.endless.ddd.simplified.generator.components.generators;

import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.interfaceDefine;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.importHeaderMapper;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.packageHeader;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.exchangeSuffix;

/**
 * MapperGenerator
 * <p>
 * create 2024/09/16 12:50
 * <p>
 * update 2024/09/16 12:50
 *
 * @since 1.0.0
 */
public class MapperGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {
        String interfaceName = exchangeSuffix(aggregate.getAggregateName(), "Mapper", 1);
        String generic = exchangeSuffix(aggregate.getAggregateName(), "Record", 1);

        generate(aggregate, interfaceName, generic, aggregate.getDescription() + "聚合");
    }

    public void generateEntity(Aggregate aggregate, Entity entity) throws Exception {
        String interfaceName = exchangeSuffix(entity.getName(), "Mapper", 1);
        String generic = exchangeSuffix(entity.getName(), "Record", 1);

        generate(aggregate, interfaceName, generic, entity.getDescription() + "实体");
    }

    protected void generate(Aggregate aggregate, String interfaceName, String generic, String description) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String domainPackage = domainPackage(aggregate);
        String subPackage = "infrastructure.data.persistence.mapper";
        String packageName = domainPackage + "." + subPackage;
        String superClassName = superClassName(aggregate.getServiceName(), interfaceName, 1);
        String superClassNameWithGeneric = superClassNameWithGenerics(superClassName, generic);
        String rootPath = aggregate.getRootPath();
        String classDescription = description + " Mybatis-Plus 数据访问对象";

        packageHeader(stringBuilder, packageName);
        importHeaderMapper(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage, subPackage);
        comment(stringBuilder, interfaceName, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        interfaceDefine(stringBuilder, interfaceName, superClassNameWithGeneric);
        stringBuilder.append("\n");
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, interfaceName);
        writeFile(rootPath, packageName, interfaceName, stringBuilder.toString());
    }
}
