package org.endless.ddd.simplified.generator.components.generators;

import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
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
import static org.endless.ddd.simplified.generator.template.MethodTemplate.beanDefine;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.exchangeSuffix;

/**
 * DrivingAdapterGenerator
 * <p>
 * create 2024/09/20 17:35
 * <p>
 * update 2024/09/20 17:35
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class DrivingAdapterGenerator {


    public void generateDomain(Aggregate aggregate) throws Exception {

        generateInterface(aggregate);
        generateSpringImpl(aggregate);
        generateConfiguration(aggregate);
    }

    private void generateInterface(Aggregate aggregate) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String interfaceName = aggregate.getDomainName() + "DrivingAdapter";
        String superClassName = superClassName(aggregate.getServiceName(), interfaceName, 2);
        String domainPackage = domainPackage(aggregate);
        String subPackage = "facade.adapter";
        String packageName = domainPackage + "." + subPackage;
        String rootPath = aggregate.getRootPath();
        String classDescription = aggregate.getDescription() + "领域主动适配器";

        packageHeader(stringBuilder, packageName);
        importHeaderDrivingAdapter(stringBuilder, servicePackage(aggregate), subPackage);
        comment(stringBuilder, interfaceName, superClassName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        interfaceDefine(stringBuilder, interfaceName, superClassName);
        stringBuilder.append("\n");
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, interfaceName);
        writeFile(rootPath, packageName, interfaceName, stringBuilder.toString());
    }

    private void generateSpringImpl(Aggregate aggregate) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();

        String interfaceName = aggregate.getDomainName() + "DrivingAdapter";
        String className = "Spring" + interfaceName;
        String domainPackage = domainPackage(aggregate);
        String subPackage = "facade.adapter.spring";
        String packageName = domainPackage + "." + subPackage;
        String rootPath = aggregate.getRootPath();
        String description = aggregate.getDescription();
        String classDescription = description + "领域主动适配器Spring实现类";
        String commandName = exchangeSuffix(aggregate.getAggregateName(), "CommandHandler", 1);
        String queryName = exchangeSuffix(aggregate.getAggregateName(), "QueryHandler", 1);
        List<Field> fields = new ArrayList<>();
        fields.add(Field.builder().name(StringUtils.uncapitalize(commandName)).type(commandName).description(description + "领域命令处理器").nullable(false).build());
        fields.add(Field.builder().name(StringUtils.uncapitalize(queryName)).type(queryName).description(description + "领域查询处理器").nullable(false).build());


        packageHeader(stringBuilder, packageName);
        importHeaderSpringDrivingAdapter(stringBuilder, aggregate.getGroupId(), domainPackage, subPackage);
        comment(stringBuilder, className, interfaceName, classDescription, aggregate.getAuthor(), aggregate.getVersion());
        classDefine(stringBuilder, className, interfaceName);
        fields(stringBuilder, entityNames(aggregate), valueNames(aggregate), fields, className);
        allArgsConstructor(stringBuilder, fields, className);
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }

    private void generateConfiguration(Aggregate aggregate) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        String className = aggregate.getDomainName() + "DrivingConfiguration";
        String domainPackage = domainPackage(aggregate);
        String subPackage = "facade.adapter.config";
        String packageName = domainPackage + "." + subPackage;
        String classDescription = aggregate.getDescription() + "领域主动适配器配置类";
        String rootPath = aggregate.getRootPath();


        packageHeader(stringBuilder, packageName);
        importHeaderDrivingConfiguration(stringBuilder, aggregate.getGroupId(), domainPackage, subPackage);
        comment(stringBuilder, className, "", classDescription, aggregate.getAuthor(), aggregate.getVersion());
        classDefine(stringBuilder, className, "");
        beanDefine(stringBuilder, entityNames(aggregate), aggregate.getDomainName());
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }
}
