package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

/**
 * DomainEvent
 * <p>
 * 领域事件
 * <p>
 * create 2025/07/30 16:45
 * <p>
 * update 2025/07/30 16:45
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@ToString
@Builder(buildMethodName = "innerBuild")
public class DomainEvent {

    /**
     * 领域事件名称
     */
    private String name;

    /**
     * 领域事件描述
     */
    private String description;

    public static DomainEvent create(DomainEventBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public DomainEvent remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域事件处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    public DomainEvent validate() {
        validateName();
        validateDescription();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("领域事件名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("领域事件描述不能为空");
        }
    }
}
