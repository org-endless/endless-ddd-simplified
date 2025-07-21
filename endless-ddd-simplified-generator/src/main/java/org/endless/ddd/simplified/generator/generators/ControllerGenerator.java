package org.endless.ddd.simplified.generator.generators;

import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Field;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.ConstructorTemplate.allArgsConstructor;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.controllerDefine;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.fields;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.importHeaderController;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.packageHeader;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;
import static org.endless.ddd.simplified.generator.utils.StringTools.camelCaseToSlash;

/**
 * ControllerGenerator
 * <p>Generates controller code for the provided Aggregate.
 * <p>
 * create 2024/09/20 17:50
 * <p>
 * update 2024/09/20 17:50
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class ControllerGenerator {

    public void generateAggregate(Aggregate aggregate) throws Exception {
        generate(aggregate);
    }

    private void generate(Aggregate aggregate) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String packageName = getPackageName(aggregate);
        String className = getClassName(aggregate);
        String superClassName = superClassName(aggregate.getServiceName(), className, 2);

        content(stringBuilder, aggregate, packageName, className, superClassName);

        writeGeneratedFile(aggregate.getRootPath(), packageName, className, stringBuilder.toString());
    }

    private String getPackageName(Aggregate aggregate) {
        return domainPackage(aggregate) + ".sidecar.rest";
    }

    private String getClassName(Aggregate aggregate) {
        return aggregate.getDomainName() + "RestController";
    }

    private void content(StringBuilder stringBuilder, Aggregate aggregate, String packageName, String className, String superClassName) {
        String drivingAdapter = aggregate.getDomainName() + "DrivingAdapter";
        String drivingAdapterDescription = aggregate.getDescription() + "领域主动适配器";
        List<Field> fields = createFields(drivingAdapter, drivingAdapterDescription);
        String uri = uri(aggregate.getContextName(), aggregate.getDomainName());

        packageHeader(stringBuilder, packageName);
        importHeaderController(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage(aggregate), "sidecar.rest");
        comment(stringBuilder, className, superClassName, aggregate.getDescription() + "领域Rest控制器", aggregate.getAuthor(), aggregate.getVersion());
        controllerDefine(stringBuilder, uri, className, superClassName);
        fields(stringBuilder, entityNames(aggregate), valueNames(aggregate), fields, className);
        allArgsConstructor(stringBuilder, fields, className);
        end(stringBuilder);
    }

    private List<Field> createFields(String drivingAdapter, String drivingAdapterDescription) {
        List<Field> fields = new ArrayList<>();
        fields.add(Field.builder()
                .name(StringUtils.uncapitalize(drivingAdapter))
                .type(drivingAdapter)
                .description(drivingAdapterDescription)
                .nullable(false)
                .build());
        return fields;
    }

    private void writeGeneratedFile(String rootPath, String packageName, String className, String content) throws Exception {
        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, content);
    }

    private String uri(String contextName, String domainName) {
        String contextUriName = contextName.replace(".", "/");
        String domainUriName = camelCaseToSlash(domainName);
        if (domainUriName.contains(contextUriName)) {
            if (domainUriName.equals(contextUriName)) {
                return contextUriName;
            } else {
                return contextUriName + "/" + domainUriName.replace(contextUriName + "/", "").replace("/" + contextUriName, "");
            }
        } else {
            return contextUriName + "/" + domainUriName;
        }
    }
}
