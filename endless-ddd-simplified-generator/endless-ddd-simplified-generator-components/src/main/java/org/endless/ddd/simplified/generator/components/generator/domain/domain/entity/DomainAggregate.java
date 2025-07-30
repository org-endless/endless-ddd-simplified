package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DomainAggregate
 * <p>
 * 领域聚合根
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorAggregate
 * @since 0.0.1
 */
@Getter
@ToString
@Builder(buildMethodName = "innerBuild")
public class DomainAggregate implements DDDSimplifiedGeneratorAggregate {

    /**
     * 领域名称
     */
    private String name;

    /**
     * 限界上下文名称
     */
    private String contextName;

    /**
     * 领域描述
     */
    private String description;

    /**
     * 领域版本
     */
    private String version;

    /**
     * 领域作者
     */
    private String author;

    /**
     * 聚合根字段列表
     */
    private List<DomainField> fields;

    /**
     * 领域实体列表
     */
    private List<DomainEntity> entities;

    /**
     * 领域枚举列表
     */
    private List<DomainEnum> enums;

    /**
     * 领域值对象列表
     */
    private List<DomainValue> values;

    /**
     * 领域适配器列表
     */
    private List<DomainAdapter> adapters;

    /**
     * 领域创建时间
     */
    private String createAt;

    /**
     * 领域更新时间
     */
    private LocalDateTime updateAt;

    public static DomainAggregate create(DomainAggregateBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public DomainAggregate remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("聚合根<领域聚合根>处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    @Override
    public DomainAggregate validate() {
        validateName();
        validateContextName();
        validateDescription();
        validateVersion();
        validateAuthor();
        validateFields();
        validateEntities();
        validateEnums();
        validateValues();
        validateAdapters();
        validateCreateAt();
        validateUpdateAt();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("领域名称不能为空");
        }
    }

    private void validateContextName() {
        if (!StringUtils.hasText(contextName)) {
            throw new AggregateValidateException("限界上下文名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("领域描述不能为空");
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new AggregateValidateException("领域版本不能为空");
        }
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new AggregateValidateException("领域作者不能为空");
        }
    }

    private void validateFields() {
        if (CollectionUtils.isEmpty(fields)) {
            throw new AggregateValidateException("字段列表不能为空");
        }
        fields.forEach(DomainField::validate);
    }

    private void validateEntities() {
        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(DomainEntity::validate);
        }
    }

    private void validateEnums() {
        if (!CollectionUtils.isEmpty(enums)) {
            enums.forEach(DomainEnum::validate);
        }
    }

    private void validateValues() {
        if (!CollectionUtils.isEmpty(values)) {
            enums.forEach(DomainEnum::validate);
        }
    }

    private void validateAdapters() {
        if (CollectionUtils.isEmpty(adapters)) {
            throw new AggregateValidateException("领域适配器列表不能为空");
        }
        adapters.forEach(DomainAdapter::validate);
    }

    private void validateCreateAt() {
        if (!StringUtils.hasText(createAt)) {
            throw new AggregateValidateException("领域创建时间不能为空");
        }
    }

    private void validateUpdateAt() {
        if (updateAt == null) {
            throw new AggregateValidateException("领域更新时间不能为空");
        }
    }
}
