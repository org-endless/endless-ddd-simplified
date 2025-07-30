package org.endless.ddd.simplified.generator.components.generator.domain.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.StringUtils;

/**
 * DomainCreateReqCTransfer
 * <p>
 * 聚合创建命令请求传输对象
 * <p>
 * create 2025/07/29 21:06
 * <p>
 * update 2025/07/29 21:06
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorCommandTransfer
 * @since 0.0.1
 */
@Getter
@ToString
@Builder
@JSONType(orders = {"name", "description", "contextId", "aggregateName", "aggregateDescription"})
public class DomainCreateReqCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 聚合名称
     */
    private final String name;

    /**
     * 聚合描述
     */
    private final String description;

    /**
     * 上下文ID
     */
    private final String contextId;

    /**
     * 聚合根名称
     */
    private final String aggregateName;

    /**
     * 聚合根描述
     */
    private final String aggregateDescription;

    @Override
    public DomainCreateReqCTransfer validate() {
        validateName();
        validateContextId();
        validateAggregateName();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new CommandTransferValidateException("聚合名称不能为空");
        }
    }

    private void validateContextId() {
        if (!StringUtils.hasText(contextId)) {
            throw new CommandTransferValidateException("上下文ID不能为空");
        }
    }

    private void validateAggregateName() {
        if (!StringUtils.hasText(aggregateName)) {
            throw new CommandTransferValidateException("聚合根名称不能为空");
        }
    }
}
