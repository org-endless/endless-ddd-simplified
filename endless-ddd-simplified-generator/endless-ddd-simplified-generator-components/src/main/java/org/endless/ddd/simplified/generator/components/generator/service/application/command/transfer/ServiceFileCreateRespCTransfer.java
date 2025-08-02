package org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.StringUtils;

/**
 * ProjectFileCreateRespCTransfer
 * <p>服务文件创建命令响应传输对象
 * <p>
 * create 2025/07/31 22:57
 * <p>
 * update 2025/08/01 14:25
 *
 * @param rootPath    文件根路径
 * @param packageName 文件包名
 * @param fileName    文件名称
 * @param content     文件内容
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorCommandTransfer
 * @since 1.0.0
 */
@Builder
@JSONType(orders = {"rootPath", "packageName", "fileName", "content"})
public record ServiceFileCreateRespCTransfer(
        String rootPath, String packageName, String fileName, String content)
        implements DDDSimplifiedGeneratorCommandTransfer {

    @Override
    public ServiceFileCreateRespCTransfer validate() {
        validateRootPath();
        validatePackageName();
        validateFileName();
        validateContent();
        return this;
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new CommandTransferValidateException("文件根路径不能为空");
        }
    }

    private void validatePackageName() {
        if (!StringUtils.hasText(packageName)) {
            throw new CommandTransferValidateException("文件包名不能为空");
        }
    }

    private void validateFileName() {
        if (!StringUtils.hasText(fileName)) {
            throw new CommandTransferValidateException("文件名称不能为空");
        }
    }

    private void validateContent() {
        if (!StringUtils.hasText(content)) {
            throw new CommandTransferValidateException("文件内容不能为空");
        }
    }
}
