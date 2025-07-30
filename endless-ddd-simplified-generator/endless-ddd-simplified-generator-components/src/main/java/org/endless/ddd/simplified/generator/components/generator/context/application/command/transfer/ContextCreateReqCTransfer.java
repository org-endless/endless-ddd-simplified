package org.endless.ddd.simplified.generator.components.generator.context.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.StringUtils;

/**
 * ContextCreateReqCTransfer
 * <p>
 * 上下文创建命令请求传输对象
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
@JSONType(orders = { "name", "description", "serviceId", "boundary", "contextMapping" })
public class ContextCreateReqCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 上下文名称
     */
    private final String name;

    /**
     * 上下文描述
     */
    private final String description;

    /**
     * 服务ID
     */
    private final String serviceId;

    /**
     * 上下文边界
     */
    private final String boundary;

    /**
     * 上下文映射
     */
    private final String contextMapping;

    @Override
    public ContextCreateReqCTransfer validate() {
        validateName();
        validateServiceId();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new CommandTransferValidateException("上下文名称不能为空");
        }
    }

    private void validateServiceId() {
        if (!StringUtils.hasText(serviceId)) {
            throw new CommandTransferValidateException("服务ID不能为空");
        }
    }
}