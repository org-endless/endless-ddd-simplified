package org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.StringUtils;

/**
 * ProjectCreateReqCTransfer
 * <p>项目创建命令请求传输对象
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
@JSONType(orders = {"name"})
public class ProjectCreateReqCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 项目名称
     */
    private final String name;

    @Override
    public ProjectCreateReqCTransfer validate() {
        validateName();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new CommandTransferValidateException("项目名称不能为空");
        }
    }
}
