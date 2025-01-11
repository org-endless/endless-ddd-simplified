package org.endless.ddd.simplified.generator.object.entity;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.utils.StringTools;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Aggregate
 * <p> 聚合根信息
 * <p>
 * create 2024/09/16 17:36
 * <p>
 * update 2024/10/18 15:16
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
@JSONType(orders = {"author", "version", "groupId", "serviceName", "rootPath", "serviceSubPackage", "contextName", "domainName", "aggregateName", "description", "fields", "entities", "values", "enums", "methods"})
public class Aggregate {

    private final String author;

    private final String version;

    private final String groupId;

    private final String serviceName;

    private final String rootPath;

    private final String serviceSubPackage;

    private final String contextName;

    private final String domainName;

    private final String aggregateName;

    private final String description;

    private final List<Field> fields;

    private final List<Entity> entities;

    private final List<Value> values;

    private final List<Enum> enums;

    private final List<Method> methods;

    private final List<Transfer> transfers;

    public Aggregate validate() {
        validateAuthor();
        validateVersion();
        validateGroupId();
        validateServiceName();
        validateRootPath();
        validateServiceSubPackage();
        validateContextName();
        validateDomainName();
        validateAggregateName();
        validateDescription();
        validateFields();
        validateEntities();
        validateValues();
        validateEnums();
        validateMethods();
        validateTransfers();
        return this;
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new IllegalArgumentException("作者不能为空，当前值为: " + author);
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new IllegalArgumentException("版本号不能为空，当前值为: " + version);
        }
    }

    private void validateGroupId() {
        if (!StringUtils.hasText(groupId)) {
            throw new IllegalArgumentException("groupId不能为空，当前值为: " + groupId);
        }
    }

    private void validateServiceName() {
        if (!StringUtils.hasText(serviceName)) {
            throw new IllegalArgumentException("服务名称不能为空，当前值为: " + serviceName);
        }
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new IllegalArgumentException("根路径不能为空，当前值为: " + rootPath);
        }
    }

    private void validateServiceSubPackage() {
        if (!StringUtils.hasText(serviceSubPackage)) {
            throw new IllegalArgumentException("服务业务子包不能为空，当前值为: " + serviceSubPackage);
        }
    }

    private void validateContextName() {
        if (!StringUtils.hasText(contextName)) {
            throw new IllegalArgumentException("上下文名称不能为空，当前值为: " + contextName);
        }
    }

    private void validateDomainName() {
        if (!StringUtils.hasText(domainName)) {
            throw new IllegalArgumentException("领域名称不能为空，当前值为: " + domainName);
        }
    }

    private void validateAggregateName() {
        if (!StringUtils.hasText(aggregateName)) {
            throw new IllegalArgumentException("聚合名称不能为空，当前值为: " + aggregateName);
        }
        if (!aggregateName.endsWith("Aggregate")) {
            throw new IllegalArgumentException("聚合名称必须以Aggregate结尾，当前值为: " + aggregateName);
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("描述不能为空，当前值为: " + description);
        }
    }

    private void validateFields() {
        if (fields != null && !fields.isEmpty()) {
            fields.stream()
                    .peek(Field::validate)  // 执行每个字段的 validate 方法
                    .filter(field -> {
                        String fieldType = field.type();
                        String generics = StringTools.generics(fieldType);
                        return fieldType.startsWith("List<")
                                && !generics.endsWith("Entity")
                                && !generics.equals("String")
                                && !generics.equals("Long");
                    })
                    .findAny()
                    .ifPresent(field -> {
                        throw new IllegalArgumentException(field.type() + " 类型字段 " + field.name() + " 泛型必须为 Entity、String、Long！");
                    });
        } else {
            throw new IllegalArgumentException("字段不能为空！");
        }
    }

    private void validateEntities() {
        if (entities != null && !entities.isEmpty()) {
            entities.forEach(Entity::validate);
        }
    }

    private void validateValues() {
        if (values != null && !values.isEmpty()) {
            values.forEach(Value::validate);
        }
    }

    private void validateEnums() {
        if (enums != null && !enums.isEmpty()) {
            enums.forEach(Enum::validate);
        }
    }

    private void validateMethods() {
        if (methods != null && !methods.isEmpty()) {
            methods.forEach(Method::validate);
        }
    }

    private void validateTransfers() {
        if (transfers != null && !transfers.isEmpty()) {
            transfers.forEach(Transfer::validate);
        }
    }
}
