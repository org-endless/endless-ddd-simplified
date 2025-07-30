package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

/**
 * DomainService
 * <p>
 * 领域服务
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
public class DomainService {

    /**
     * 领域服务名称
     */
    private String name;

    /**
     * 领域服务描述
     */
    private String description;

    /**
     * 创建领域服务
     *
     * @param builder 构建器
     * @return 领域服务
     */
    public static DomainService create(DomainServiceBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    /**
     * 删除领域服务
     *
     * @param modifyUserId 修改用户ID
     * @return 领域服务
     */
    public DomainService remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域服务处于不可删除状态");
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
     * 验证领域服务
     *
     * @return 领域服务
     */
    public DomainService validate() {
        validateName();
        validateDescription();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("领域服务名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("领域服务描述不能为空");
        }
    }
}
