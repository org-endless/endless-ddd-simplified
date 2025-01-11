package org.endless.ddd.simplified.generator.generators;

import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Field;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.endless.ddd.simplified.generator.utils.DDDUtils.entityNames;
import static org.endless.ddd.simplified.generator.utils.DDDUtils.id;
import static org.endless.ddd.simplified.generator.utils.StringTools.*;


/**
 * AssociationGenerator
 * <p>
 * create 2024/11/12 16:45
 * <p>
 * update 2024/11/12 16:45
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class AssociationGenerator {

    public void generate(Aggregate aggregate, Boolean enableRecord, Boolean enableMapper) throws Exception {
        String aggregateName = aggregate.getAggregateName();
        String aggregateDescription = aggregate.getDescription();

        List<Field> fields = aggregate.getFields();
        for (Field field : fields) {
            String fieldType = field.type();
            String fieldName = field.name();
            String fieldDescription = field.description();
            String generics = generics(fieldType);
            if (fieldType.startsWith("List<") && !entityNames(aggregate).contains(generics)) {
                String associationDescription = removeSuffix(fieldDescription, "ID列表");
                String classDescription = aggregate.getDescription() + "-" + associationDescription + "关系数据库记录实体";
                String associationIdName = removeSuffix(fieldName, "s");
                String associationClassName = StringUtils.capitalize(removeSuffix(fieldName, "Ids"));
                String className = removeSuffix(aggregateName, 1) + associationClassName + "AssociationRecord";
                String tableName = snakeCase(aggregate.getContextName()) + "_" + snakeCase(removeSuffix(className, "Record")).replace("_" + aggregate.getContextName(), "").replace(aggregate.getContextName() + "_", "");
                List<Field> associationFields = new ArrayList<>();
                associationFields.add(Field.builder().name("associationId").type("String").description(removeSuffix(classDescription, "关系数据库记录实体") + "ID").nullable(false).build());
                associationFields.add(Field.builder().name(associationIdName).type("String").description(associationDescription + "ID").nullable(false).build());
                associationFields.add(Field.builder().name(id(aggregateName, 1)).type("String").description(aggregateDescription + "ID").nullable(false).build());

                if (enableRecord) {
                    new DataRecordGenerator().generate(aggregate, associationFields, className, tableName, aggregateName, aggregateDescription, classDescription);
                }
                if (enableMapper) {
                    String mapperName = exchangeSuffix(className, "Mapper", 1);
                    String mapperDescription = exchangeSuffix(classDescription, "关系数据库记录实体", "关系");
                    new MapperGenerator().generate(aggregate, mapperName, className, mapperDescription);
                }
            }
        }
    }
}
