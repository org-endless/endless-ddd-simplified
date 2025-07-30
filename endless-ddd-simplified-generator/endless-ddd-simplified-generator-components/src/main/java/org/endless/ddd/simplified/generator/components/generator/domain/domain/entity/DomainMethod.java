package org.endless.ddd.simplified.generator.components.generator.domain.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.type.DomainFieldTypeEnum;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.type.DomainOperationTypeEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * DomainMethod
 * <p>
 * 领域方法
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
public class DomainMethod {

    /**
     * 领域方法名称
     */
    private String name;

    /**
     * 领域方法描述
     */
    private String description;

    /**
     * 领域方法操作类型
     */
    private DomainOperationTypeEnum operationType;

    /**
     * 领域方法参数列表
     */
    private List<DomainField> parameters;

    /**
     * 领域方法返回类型
     */
    private DomainFieldTypeEnum returnType;

    /**
     * 领域方法返回类型对象名称
     */
    private String typeObjectName;

    /**
     * 领域方法传输对象列表
     */
    private List<DomainTransfer> transfers;

    /**
     * 创建领域方法
     *
     * @param builder 构建器
     * @return 领域方法
     */
    public static DomainMethod create(DomainMethodBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    /**
     * 删除领域方法
     *
     * @param modifyUserId 修改用户ID
     * @return 领域方法
     */
    public DomainMethod remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("领域方法处于不可删除状态");
        }
        return this;
    }

    /**
     * 检查是否可以删除
     *
     * @return 是否可以删除
     */
    private boolean canRemove() {
        // 这里可以根据业务逻辑判断是否可以删除
        // 例如：检查方法状态、依赖关系等
        return true; // 暂时保持简单，后续可以根据业务需求扩展
    }

    /**
     * 验证领域方法
     *
     * @return 领域方法
     */
    public DomainMethod validate() {
        validateName();
        validateDescription();
        validateOperationType();
        validateParameters();
        validateReturnType();
        validateTypeObjectName();
        validateTransfers();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("领域方法名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("领域方法描述不能为空");
        }
    }

    private void validateOperationType() {
        if (operationType == null) {
            throw new AggregateValidateException("领域方法操作类型不能为空");
        }
    }

    private void validateParameters() {
        if (parameters == null || parameters.isEmpty()) {
            throw new AggregateValidateException("领域方法参数列表不能为空");
        }
    }

    private void validateReturnType() {
        if (returnType == null) {
            throw new AggregateValidateException("领域方法返回类型不能为空");
        }
    }

    private void validateTypeObjectName() {
        if (!StringUtils.hasText(typeObjectName)) {
            throw new AggregateValidateException("领域方法返回类型对象名称不能为空");
        }
    }

    private void validateTransfers() {
        if (transfers == null || transfers.isEmpty()) {
            throw new AggregateValidateException("领域方法传输对象列表不能为空");
        }
    }
}
