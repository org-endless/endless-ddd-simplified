package org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectJavaVersionEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectLoggingFrameworkEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectPersistenceFrameworkEnum;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ProjectCreateReqCTransfer
 * <p>
 * 项目创建命令请求传输对象
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
@JSONType(orders = { "projectArtifactId", "groupId", "name", "description", "version", "author", "rootPath",
        "basePackage", "enableSpringDoc", "javaVersion", "loggingFramework", "persistenceFramework",
        "serviceArtifactIds" })
public class ProjectCreateReqCTransfer implements DDDSimplifiedGeneratorCommandTransfer {

    /**
     * 项目构件ID
     */
    private final String projectArtifactId;

    /**
     * 组织ID
     */
    private final String groupId;

    /**
     * 项目名称
     */
    private final String name;

    /**
     * 项目描述
     */
    private final String description;

    /**
     * 项目版本号
     */
    private final String version;

    /**
     * 项目作者
     */
    private final String author;

    /**
     * 项目根路径
     */
    private final String rootPath;

    /**
     * 项目基础包名
     */
    private final String basePackage;

    /**
     * 项目是否启用Spring Doc
     */
    private final String enableSpringDoc;

    /**
     * 项目Java版本
     */
    private final ProjectJavaVersionEnum javaVersion;

    /**
     * 项目日志框架
     */
    private final ProjectLoggingFrameworkEnum loggingFramework;

    /**
     * 项目持久化框架
     */
    private final ProjectPersistenceFrameworkEnum persistenceFramework;

    /**
     * 服务构件ID列表
     */
    private final List<String> serviceArtifactIds;

    @Override
    public ProjectCreateReqCTransfer validate() {
        validateProjectArtifactId();
        validateGroupId();
        validateName();
        validateDescription();
        validateVersion();
        validateAuthor();
        validateRootPath();
        validateBasePackage();
        validateEnableSpringDoc();
        validateJavaVersion();
        validateLoggingFramework();
        validatePersistenceFramework();
        validateServiceArtifactIds();
        return this;
    }

    private void validateProjectArtifactId() {
        if (!StringUtils.hasText(projectArtifactId)) {
            throw new CommandTransferValidateException("项目构件ID不能为空");
        }
    }

    private void validateGroupId() {
        if (!StringUtils.hasText(groupId)) {
            throw new CommandTransferValidateException("组织ID不能为空");
        }
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new CommandTransferValidateException("项目名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new CommandTransferValidateException("项目描述不能为空");
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new CommandTransferValidateException("项目版本号不能为空");
        }
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new CommandTransferValidateException("项目作者不能为空");
        }
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new CommandTransferValidateException("项目根路径不能为空");
        }
    }

    private void validateBasePackage() {
        if (!StringUtils.hasText(basePackage)) {
            throw new CommandTransferValidateException("项目基础包名不能为空");
        }
    }

    private void validateEnableSpringDoc() {
        if (!StringUtils.hasText(enableSpringDoc)) {
            throw new CommandTransferValidateException("项目是否启用Spring Doc不能为空");
        }
    }

    private void validateJavaVersion() {
        if (javaVersion == null) {
            throw new CommandTransferValidateException("项目Java版本不能为空");
        }
    }

    private void validateLoggingFramework() {
        if (loggingFramework == null) {
            throw new CommandTransferValidateException("项目日志框架不能为空");
        }
    }

    private void validatePersistenceFramework() {
        if (persistenceFramework == null) {
            throw new CommandTransferValidateException("项目持久化框架不能为空");
        }
    }

    private void validateServiceArtifactIds() {
        if (CollectionUtils.isEmpty(serviceArtifactIds)) {
            throw new CommandTransferValidateException("服务构件ID列表不能为空");
        }
    }
}
