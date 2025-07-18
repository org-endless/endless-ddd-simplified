package org.endless.ddd.simplified.generator.object.entity;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.object.type.EnumValue;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Enum
 * <p>
 * create 2024/09/18 00:33
 * <p>
 * update 2024/09/18 00:33
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
@JSONType(orders = {"name", "valueType", "description", "fields", "enumValues"})
public class Enum {

    private final String name;

    private final String valueType;

    private final String description;

    private final List<Field> fields;

    private final List<EnumValue> enumValues;

    public Enum validate() {
        validateName();
        validateValueType();
        validateDescription();
        validateFields();
        validateEnumValues();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("枚举名称不能为空，当前值为: " + name);
        }
    }

    private void validateValueType() {
        if (!StringUtils.hasText(valueType)) {
            throw new IllegalArgumentException("枚举值类型不能为空，当前值为: " + valueType);
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("枚举描述不能为空，当前值为: " + description);
        }
    }

    private void validateFields() {
        if (!CollectionUtils.isEmpty(fields)) {
            fields.forEach(Field::validate);
        } else {
            throw new IllegalArgumentException("实体字段不能为空");
        }
    }

    private void validateEnumValues() {
        if (!CollectionUtils.isEmpty(enumValues)) {
            enumValues.forEach(EnumValue::validate);
        } else {
            throw new IllegalArgumentException("枚举值不能为空");
        }
    }
}
