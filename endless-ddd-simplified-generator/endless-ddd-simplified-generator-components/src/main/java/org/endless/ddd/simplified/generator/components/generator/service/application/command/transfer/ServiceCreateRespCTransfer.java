package org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ProjectCreateRespCTransfer
 * <p>服务创建命令响应传输对象
 * <p>
 * create 2025/07/31 22:57
 * <p>
 * update 2025/07/31 22:59
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorCommandTransfer
 * @since 1.0.0
 */
@Getter
@ToString
@Builder
@JSONType(orders = {"rootPath", "packageName", "files"})
public class ServiceCreateRespCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 服务根路径
     */
    private final String rootPath;

    /**
     * 服务包名
     */
    private final String packageName;

    /**
     * 服务文件列表
     */
    private final List<ServiceFileCreateRespCTransfer> files;

    @Override
    public ServiceCreateRespCTransfer validate() {
        validateRootPath();
        validatePackageName();
        validateFiles();
        return this;
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new CommandTransferValidateException("服务根路径不能为空");
        }
    }

    private void validatePackageName() {
        if (!StringUtils.hasText(packageName)) {
            throw new CommandTransferValidateException("服务包名不能为空");
        }
    }

    private void validateFiles() {
        if (CollectionUtils.isEmpty(files)) {
            throw new CommandTransferValidateException("服务文件列表不能为空");
        }
    }
}
