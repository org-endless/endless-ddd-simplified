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
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.exchangeSuffix;

/**
 * CommandHandlerGenerator
 * <p>Generates command handler interfaces and implementations for the specified Aggregate.
 * <p>
 * create 2024/09/16 12:51
 * <p>
 * update 2024/09/16 12:51
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class CommandHandlerGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {
        String interfaceName = exchangeSuffix(aggregate.getAggregateName(), "CommandHandler", 1);
        generateInterface(aggregate, interfaceName);
        generateImpl(aggregate, interfaceName);
    }

    private void generateInterface(Aggregate aggregate, String interfaceName) throws Exception {
        String subPackage = "application.command.handler";
        String superClassNameWithGeneric = superClassNameWithGenerics(superClassName(aggregate.getServiceName(), interfaceName, 2), aggregate.getAggregateName());
        String content = interfaceContent(aggregate, interfaceName, superClassNameWithGeneric, subPackage);

        writeToFile(aggregate.getRootPath(), packageName(aggregate, subPackage), interfaceName, content);
    }

    private void generateImpl(Aggregate aggregate, String interfaceName) throws Exception {
        String className = interfaceName + "Impl";
        String subPackage = "application.command.handler.impl";
        String repositoryName = exchangeSuffix(aggregate.getAggregateName(), "Repository", 1);
        List<Field> fields = createFields(repositoryName, aggregate.getDescription() + "聚合仓储接口");

        String content = implContent(aggregate, interfaceName, className, subPackage, fields);

        writeToFile(aggregate.getRootPath(), packageName(aggregate, subPackage), className, content);
    }

    private String interfaceContent(Aggregate aggregate, String interfaceName, String superClassNameWithGeneric, String subPackage) {
        StringBuilder stringBuilder = new StringBuilder();
        packageHeader(stringBuilder, packageName(aggregate, subPackage));
        importHeaderCommandHandler(stringBuilder, servicePackage(aggregate), domainPackage(aggregate), subPackage);
        comment(stringBuilder, interfaceName, superClassNameWithGeneric, aggregate.getDescription() + "领域命令处理器", aggregate.getAuthor(), aggregate.getVersion());
        interfaceDefine(stringBuilder, interfaceName, superClassNameWithGeneric);
        stringBuilder.append("\n");
        end(stringBuilder);
        return stringBuilder.toString();
    }


    private String implContent(Aggregate aggregate, String interfaceName, String className, String subPackage, List<Field> fields) {
        StringBuilder stringBuilder = new StringBuilder();
        packageHeader(stringBuilder, packageName(aggregate, subPackage));
        importHeaderCommandHandlerImpl(stringBuilder, aggregate.getGroupId(), domainPackage(aggregate), subPackage);
        comment(stringBuilder, className, interfaceName, aggregate.getDescription() + "领域命令处理器", aggregate.getAuthor(), aggregate.getVersion());
        classDefine(stringBuilder, className, interfaceName);
        fields(stringBuilder, entityNames(aggregate), valueNames(aggregate), fields, className);
        allArgsConstructor(stringBuilder, fields, className);
        end(stringBuilder);
        return stringBuilder.toString();
    }

    private List<Field> createFields(String repositoryName, String description) {
        List<Field> fields = new ArrayList<>();
        fields.add(Field.builder()
                .name(StringUtils.uncapitalize(repositoryName))
                .type(repositoryName)
                .description(description)
                .nullable(false)
                .build());
        return fields;
    }

    private void writeToFile(String rootPath, String packageName, String className, String content) throws Exception {
        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, content);
    }
}
