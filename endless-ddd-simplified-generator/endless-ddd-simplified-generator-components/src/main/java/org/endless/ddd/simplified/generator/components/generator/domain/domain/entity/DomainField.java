package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.type.DomainFieldTypeEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

/**
 * DomainField
 * <p>
 * 领域字段
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @since 0.0.1
 */
@Getter
@ToString
@Builder(buildMethodName = "innerBuild")
public class DomainField {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段类型
     */
    private DomainFieldTypeEnum type;

    /**
     * 字段类型对象名称
     */
    private String typeObjectName;

    /**
     * 字段描述
     */
    private String description;

    /**
     * 是否必填
     */
    private Boolean isRequired;

    public static DomainField create(DomainFieldBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public DomainField remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域字段处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    public DomainField validate() {
        validateName();
        validateType();
        validateTypeObjectName();
        validateDescription();
        validateIsRequired();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("字段名称不能为空");
        }
    }

    private void validateType() {
        if (type == null) {
            throw new AggregateValidateException("字段类型不能为空");
        }
    }

    private void validateTypeObjectName() {
        if (type == DomainFieldTypeEnum.ENTITY || type == DomainFieldTypeEnum.VALUE || type == DomainFieldTypeEnum.ENUM
                || type == DomainFieldTypeEnum.LIST_ENTITY || type == DomainFieldTypeEnum.LIST_VALUE || type == DomainFieldTypeEnum.LIST_ENUM) {
            if (!StringUtils.hasText(typeObjectName)) {
                throw new AggregateValidateException("字段类型对象名称不能为空");
            }
        } else {
            throw new AggregateValidateException("字段类型对象名称只能用于实体、值对象、枚举、列表类型");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("字段描述不能为空");
        }
    }

    private void validateIsRequired() {
        if (isRequired == null) {
            throw new AggregateValidateException("是否必填不能为空");
        }
    }
}
