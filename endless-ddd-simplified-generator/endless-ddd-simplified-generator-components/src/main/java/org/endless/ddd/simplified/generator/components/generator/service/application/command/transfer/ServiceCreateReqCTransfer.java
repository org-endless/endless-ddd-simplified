package org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.StringUtils;

/**
 * ServiceCreateReqCTransfer
 * <p>
 * 服务创建命令请求传输对象
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
@JSONType(orders = { "name", "description", "port", "rootPath", "serviceSubPackage", "projectId" })
public class ServiceCreateReqCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 服务名称
     */
    private final String name;

    /**
     * 服务描述
     */
    private final String description;

    /**
     * 服务端口
     */
    private final Integer port;

    /**
     * 服务根路径
     */
    private final String rootPath;

    /**
     * 服务子包名
     */
    private final String serviceSubPackage;

    /**
     * 项目ID
     */
    private final String projectId;

    @Override
    public ServiceCreateReqCTransfer validate() {
        validateName();
        validateProjectId();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new CommandTransferValidateException("服务名称不能为空");
        }
    }

    private void validateProjectId() {
        if (!StringUtils.hasText(projectId)) {
            throw new CommandTransferValidateException("项目ID不能为空");
        }
    }
}