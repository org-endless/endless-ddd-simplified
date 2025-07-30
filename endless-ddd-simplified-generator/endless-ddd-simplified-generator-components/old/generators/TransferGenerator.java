package org.endless.ddd.simplified.generator.components.generators;

import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Field;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Transfer;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.type.AdapterType;
import org.endless.ddd.simplified.generator.object.type.CQRSType;

import java.util.List;

import static org.endless.ddd.simplified.generator.template.CommentTemplate.comment;
import static org.endless.ddd.simplified.generator.template.DefineTemplate.classDefineTransfer;
import static org.endless.ddd.simplified.generator.template.EndTemplate.end;
import static org.endless.ddd.simplified.generator.template.FieldTemplate.fields;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.importHeaderTransfer;
import static org.endless.ddd.simplified.generator.template.HeaderTemplate.packageHeader;
import static org.endless.ddd.simplified.generator.template.ValidateTemplate.validate;
import static org.endless.ddd.simplified.generator.template.ValidateTemplate.validateMethods;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.*;

/**
 * CommandTransferGenerator
 * <p>
 * create 2024/09/16 12:47
 * <p>
 * update 2024/09/16 12:47
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class TransferGenerator {


    public void generate(Aggregate aggregate, Transfer transfer) throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        CQRSType cqrsType = transfer.getCqrsType();
        AdapterType adapterType = transfer.getAdapterType();
        String subPackage, superClassName;
        String domainPackage = domainPackage(aggregate);
        String className = transfer.getName();
        String rootPath = aggregate.getRootPath();

        if (adapterType == AdapterType.DRIVING) {
            if (cqrsType == CQRSType.QUERY) {
                subPackage = "application.query.transfer";
                superClassName = aggregate.getServiceName() + "QueryTransfer";

            } else {
                subPackage = "application.command.transfer";
                superClassName = aggregate.getServiceName() + "CommandTransfer";
            }
        } else {
            subPackage = "infrastructure.adapter.transfer";
            superClassName = aggregate.getServiceName() + "DrivenTransfer";

        }
        String packageName = domainPackage + "." + subPackage;
        List<Field> fields = transfer.getFields();
        packageHeader(stringBuilder, packageName);
        importHeaderTransfer(stringBuilder, aggregate.getGroupId(), servicePackage(aggregate), domainPackage, subPackage, !valueNames(aggregate).isEmpty(), !enumNames(aggregate).isEmpty());
        comment(stringBuilder, className, superClassName, transfer.getDescription(), aggregate.getAuthor(), aggregate.getVersion());
        classDefineTransfer(stringBuilder, fields, className, superClassName);
        fields(stringBuilder, entityNames(aggregate), valueNames(aggregate), fields, className);
        validate(stringBuilder, fields, className);
        validateMethods(stringBuilder, fields, className);
        end(stringBuilder);

        deleteFileIfExists(rootPath, packageName, className);
        writeFile(rootPath, packageName, className, stringBuilder.toString());
    }
}
