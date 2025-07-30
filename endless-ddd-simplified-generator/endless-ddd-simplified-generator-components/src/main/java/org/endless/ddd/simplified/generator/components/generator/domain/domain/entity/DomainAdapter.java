package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorEntity;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.type.DomainAdapterTypeEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DomainAdapter
 * <p>
 * 领域适配器
 * <p>
 * create 2025/07/30 16:08
 * <p>
 * update 2025/07/30 18:19
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorEntity
 * @since 1.0.0
 */
@Getter
@ToString
@Builder(buildMethodName = "innerBuild")
public class DomainAdapter {

    /**
     * 领域适配器名称
     */
    private String name;

    /**
     * 领域适配器描述
     */
    private String description;

    /**
     * 领域适配器类型
     */
    private DomainAdapterTypeEnum type;

    /**
     * 领域适配器方法列表
     */
    private List<DomainMethod> domainMethods;

    /**
     * 领域适配器创建时间
     */
    private String createAt;

    /**
     * 领域适配器更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 创建领域适配器
     *
     * @param builder 构建器
     * @return 领域适配器
     */
    public static DomainAdapter create(DomainAdapterBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    /**
     * 删除领域适配器
     *
     * @param modifyUserId 修改用户ID
     * @return 领域适配器
     */
    public DomainAdapter remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域适配器处于不可删除状态");
        }
        return this;
    }

    /**
     * 检查是否可以删除
     *
     * @return 是否可以删除
     */
    private boolean canRemove() {
        return true;
    }

    /**
     * 验证领域适配器
     *
     * @return 领域适配器
     */
    public DomainAdapter validate() {
        validateName();
        validateDescription();
        validateType();
        validateDomainMethods();
        validateCreateAt();
        validateUpdateAt();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("领域适配器名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("领域适配器描述不能为空");
        }
    }

    private void validateType() {
        if (type == null) {
            throw new AggregateValidateException("领域适配器类型不能为空");
        }
    }

    private void validateDomainMethods() {
        if (!CollectionUtils.isEmpty(domainMethods)) {
            domainMethods.forEach(DomainMethod::validate);
        }
    }

    private void validateCreateAt() {
        if (!StringUtils.hasText(createAt)) {
            throw new AggregateValidateException("领域适配器创建时间不能为空");
        }
    }

    private void validateUpdateAt() {
        if (updateAt == null) {
            throw new AggregateValidateException("领域适配器更新时间不能为空");
        }
    }
}
