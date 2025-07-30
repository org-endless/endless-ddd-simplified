package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DomainEntity
 * <p>
 * 领域实体
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
public class DomainEntity {

    /**
     * 实体名称
     */
    private String name;

    /**
     * 实体描述
     */
    private String description;

    /**
     * 实体字段列表
     */
    private List<DomainField> fields;

    /**
     * 实体创建时间
     */
    private String createAt;

    /**
     * 实体更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 创建领域实体
     *
     * @param builder 构建器
     * @return 领域实体
     */
    public static DomainEntity create(DomainEntityBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    /**
     * 验证领域实体
     *
     * @return 领域实体
     */
    public DomainEntity validate() {
        validateName();
        validateDescription();
        validateFields();
        validateCreateAt();
        validateUpdateAt();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("实体名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("实体描述不能为空");
        }
    }

    private void validateFields() {
        if (CollectionUtils.isEmpty(fields)) {
            throw new AggregateValidateException("实体字段列表不能为空");
        }
        fields.forEach(DomainField::validate);
    }

    private void validateCreateAt() {
        if (!StringUtils.hasText(createAt)) {
            throw new AggregateValidateException("实体创建时间不能为空");
        }
    }

    private void validateUpdateAt() {
        if (updateAt == null) {
            throw new AggregateValidateException("实体更新时间不能为空");
        }
    }
}
