package org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.StringUtils;

import java.util.List;

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
@JSONType(orders = {"serviceArtifactId", "projectArtifactId", "name", "description", "version", "author",
        "rootPath", "basePackage", "type", "port", "contextNames"})
public class ServiceCreateReqCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 服务构件ID
     */
    private final String serviceArtifactId;

    /**
     * 项目构件ID
     */
    private final String projectArtifactId;

    /**
     * 服务名称
     */
    private final String name;

    /**
     * 服务描述
     */
    private final String description;

    /**
     * 服务版本
     */
    private final String version;

    /**
     * 服务作者
     */
    private final String author;

    /**
     * 服务根路径
     */
    private final String rootPath;

    /**
     * 服务基础包名
     */
    private final String basePackage;

    /**
     * 服务类型
     */
    private final String type;

    /**
     * 服务端口
     */
    private final Integer port;

    /**
     * 限界上下文名称列表
     */
    private final List<String> contextNames;

    /**
     * 服务创建时间
     */
    private final String createAt;

    /**
     * 服务更新时间
     */
    private final String updateAt;

    @Override
    public ServiceCreateReqCTransfer validate() {
        validateServiceArtifactId();
        validateProjectArtifactId();
        validateName();
        validateDescription();
        validateVersion();
        validateAuthor();
        validateRootPath();
        validateBasePackage();
        validateType();
        validatePort();
        return this;
    }

    /**
     * 校验服务构件ID
     */
    private void validateServiceArtifactId() {
        if (!StringUtils.hasText(serviceArtifactId)) {
            throw new CommandTransferValidateException("服务构件ID不能为空");
        }
        // 校验构件ID格式：只能包含字母、数字、连字符和下划线
        if (!serviceArtifactId.matches("^[a-zA-Z0-9_-]+$")) {
            throw new CommandTransferValidateException("服务构件ID只能包含字母、数字、连字符和下划线");
        }
        // 校验构件ID长度
        if (serviceArtifactId.length() < 3 || serviceArtifactId.length() > 50) {
            throw new CommandTransferValidateException("服务构件ID长度必须在3-50个字符之间");
        }
    }

    /**
     * 校验项目构件ID
     */
    private void validateProjectArtifactId() {
        if (!StringUtils.hasText(projectArtifactId)) {
            throw new CommandTransferValidateException("项目构件ID不能为空");
        }
        // 校验构件ID格式：只能包含字母、数字、连字符和下划线
        if (!projectArtifactId.matches("^[a-zA-Z0-9_-]+$")) {
            throw new CommandTransferValidateException("项目构件ID只能包含字母、数字、连字符和下划线");
        }
        // 校验构件ID长度
        if (projectArtifactId.length() < 3 || projectArtifactId.length() > 50) {
            throw new CommandTransferValidateException("项目构件ID长度必须在3-50个字符之间");
        }
    }

    /**
     * 校验服务名称
     */
    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new CommandTransferValidateException("服务名称不能为空");
        }
        // 校验服务名称长度
        if (name.length() < 2 || name.length() > 100) {
            throw new CommandTransferValidateException("服务名称长度必须在2-100个字符之间");
        }
        // 校验服务名称格式：不能包含特殊字符
        if (!name.matches("^[\\u4e00-\\u9fa5a-zA-Z0-9\\s_-]+$")) {
            throw new CommandTransferValidateException("服务名称包含非法字符");
        }
    }

    /**
     * 校验服务描述
     */
    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new CommandTransferValidateException("服务描述不能为空");
        }
        // 校验服务描述长度
        if (description.length() < 5 || description.length() > 500) {
            throw new CommandTransferValidateException("服务描述长度必须在5-500个字符之间");
        }
    }

    /**
     * 校验服务版本
     */
    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new CommandTransferValidateException("服务版本不能为空");
        }
        // 校验版本号格式：x.y.z 或 x.y.z-SNAPSHOT
        if (!version.matches("^\\d+\\.\\d+\\.\\d+(-SNAPSHOT)?$")) {
            throw new CommandTransferValidateException("服务版本格式不正确，应为 x.y.z 或 x.y.z-SNAPSHOT");
        }
    }

    /**
     * 校验服务作者
     */
    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new CommandTransferValidateException("服务作者不能为空");
        }
        // 校验作者名称长度
        if (author.length() < 2 || author.length() > 50) {
            throw new CommandTransferValidateException("服务作者名称长度必须在2-50个字符之间");
        }
    }

    /**
     * 校验服务根路径
     */
    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new CommandTransferValidateException("服务根路径不能为空");
        }
        // 校验路径格式：不能包含非法字符
        if (!rootPath.matches("^[a-zA-Z]:\\\\.*$|^/.*$")) {
            throw new CommandTransferValidateException("服务根路径格式不正确");
        }
    }

    /**
     * 校验服务基础包名
     */
    private void validateBasePackage() {
        if (!StringUtils.hasText(basePackage)) {
            throw new CommandTransferValidateException("服务基础包名不能为空");
        }
        // 校验包名格式：只能包含字母、数字、点
        if (!basePackage.matches("^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)*$")) {
            throw new CommandTransferValidateException("服务基础包名格式不正确，应为小写字母和数字组成，用点分隔");
        }
        // 校验包名长度
        if (basePackage.length() < 5 || basePackage.length() > 100) {
            throw new CommandTransferValidateException("服务基础包名长度必须在5-100个字符之间");
        }
    }

    /**
     * 校验服务类型
     */
    private void validateType() {
        if (!StringUtils.hasText(type)) {
            throw new CommandTransferValidateException("服务类型不能为空");
        }
    }

    /**
     * 校验服务端口
     */
    private void validatePort() {
        if (port == null) {
            throw new CommandTransferValidateException("服务端口不能为空");
        }
        // 校验端口范围：1024-65535
        if (port < 1024 || port > 65535) {
            throw new CommandTransferValidateException("服务端口必须在1024-65535之间");
        }
    }
}
