package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DomainEnum
 * <p>
 * 领域枚举
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
public class DomainEnum {

    /**
     * 枚举名称
     */
    private String name;

    /**
     * 枚举描述
     */
    private String description;

    /**
     * 枚举字段列表
     */
    private List<DomainEnumField> fields;

    /**
     * 枚举创建时间
     */
    private String createAt;

    /**
     * 枚举更新时间
     */
    private LocalDateTime updateAt;

    public static DomainEnum create(DomainEnumBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public DomainEnum remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域枚举处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    public DomainEnum validate() {
        validateName();
        validateDescription();
        validateFields();
        validateCreateAt();
        validateUpdateAt();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("枚举名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("枚举描述不能为空");
        }
    }

    private void validateFields() {
        if (CollectionUtils.isEmpty(fields)) {
            throw new AggregateValidateException("枚举字段列表不能为空");
        }
        fields.forEach(DomainEnumField::validate);
    }

    private void validateCreateAt() {
        if (!StringUtils.hasText(createAt)) {
            throw new AggregateValidateException("枚举创建时间不能为空");
        }
    }

    private void validateUpdateAt() {
        if (updateAt == null) {
            throw new AggregateValidateException("枚举更新时间不能为空");
        }
    }
}
