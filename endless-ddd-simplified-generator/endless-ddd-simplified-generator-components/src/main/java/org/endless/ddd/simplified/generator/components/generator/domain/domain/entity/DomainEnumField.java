package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

/**
 * DomainEnumField
 * <p>
 * 领域枚举字段
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
public class DomainEnumField {

    /**
     * 枚举字段编码
     */
    private String code;

    /**
     * 枚举字段描述
     */
    private String description;

    public static DomainEnumField create(DomainEnumFieldBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public DomainEnumField remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域枚举字段处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    public DomainEnumField validate() {
        validateCode();
        validateDescription();
        return this;
    }

    private void validateCode() {
        if (!StringUtils.hasText(code)) {
            throw new AggregateValidateException("枚举字段编码不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("枚举字段描述不能为空");
        }
    }
}
