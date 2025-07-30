package org.endless.ddd.simplified.generator.components.generator.context.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ContextAggregate
 * <p>
 * 限界上下文领域聚合根
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
public class ContextAggregate implements DDDSimplifiedGeneratorAggregate {

    /**
     * 限界上下文名称
     */
    private String name;

    /**
     * 服务构件ID
     */
    private String serviceArtifactId;

    /**
     * 限界上下文描述
     */
    private String description;

    /**
     * 限界上下文版本
     */
    private String version;

    /**
     * 限界上下文作者
     */
    private String author;

    /**
     * 领域名称列表
     */
    private List<String> domainNames;

    /**
     * 限界上下文创建时间
     */
    private LocalDateTime createAt;

    /**
     * 限界上下文更新时间
     */
    private LocalDateTime updateAt;

    public static ContextAggregate create(ContextAggregateBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public ContextAggregate remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("聚合根<限界上下文领域聚合根>处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    @Override
    public ContextAggregate validate() {
        validateName();
        validateServiceArtifactId();
        validateDescription();
        validateVersion();
        validateAuthor();
        validateCreateAt();
        validateUpdateAt();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("限界上下文名称不能为空");
        }
    }

    private void validateServiceArtifactId() {
        if (!StringUtils.hasText(serviceArtifactId)) {
            throw new AggregateValidateException("服务构件ID不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("限界上下文描述不能为空");
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new AggregateValidateException("限界上下文版本不能为空");
        }
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new AggregateValidateException("限界上下文作者不能为空");
        }
    }

    private void validateCreateAt() {
        if (createAt == null) {
            throw new AggregateValidateException("限界上下文创建时间不能为空");
        }
    }

    private void validateUpdateAt() {
        if (updateAt == null) {
            throw new AggregateValidateException("限界上下文更新时间不能为空");
        }
    }
}
