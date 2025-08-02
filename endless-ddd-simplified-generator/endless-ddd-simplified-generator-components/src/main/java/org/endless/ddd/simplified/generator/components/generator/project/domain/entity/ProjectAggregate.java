package org.endless.ddd.simplified.generator.components.generator.project.domain.entity;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectJavaVersionEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectLoggingFrameworkEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectPersistenceFrameworkEnum;
import org.endless.ddd.simplified.starter.common.config.utils.id.IdGenerator;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

/**
 * ProjectAggregate
 * <p>项目聚合根
 * <p>
 * create 2025/08/02 20:02
 * <p>
 * update 2025/08/02 20:02
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorAggregate
 * @since 0.0.1
 */
@Getter
@ToString
@Builder(buildMethodName = "innerBuild")
@JSONType(orders = {"projectId", "projectArtifactId", "groupId", "name", "description", "version", "author", "rootPath", "basePackage", "enableSpringDoc", "javaVersion", "loggingFramework", "persistenceFramework", "createUserId", "modifyUserId", "isRemoved"})
public class ProjectAggregate implements DDDSimplifiedGeneratorAggregate {

    /**
     * 项目ID
     */
    private final String projectId;

    /**
     * 项目构件ID
     */
    private String projectArtifactId;

    /**
     * 项目组织ID
     */
    private String groupId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目版本号
     */
    private String version;

    /**
     * 项目作者
     */
    private String author;

    /**
     * 项目根路径
     */
    private String rootPath;

    /**
     * 项目基础包名
     */
    private String basePackage;

    /**
     * 是否启用Spring Doc
     */
    private Boolean enableSpringDoc;

    /**
     * 项目Java版本
     */
    private ProjectJavaVersionEnum javaVersion;

    /**
     * 项目日志框架
     */
    private ProjectLoggingFrameworkEnum loggingFramework;

    /**
     * 项目持久化框架
     */
    private ProjectPersistenceFrameworkEnum persistenceFramework;

    /**
     * 创建者ID
     */
    private final String createUserId;

    /**
     * 修改者ID
     */
    private String modifyUserId;

    /**
     * 是否已删除
     */
    private Boolean isRemoved;

    public static ProjectAggregate create(ProjectAggregateBuilder builder) {
        return builder
                .projectId(IdGenerator.of())
                .modifyUserId(builder.createUserId)
                .isRemoved(false)
                .innerBuild()
                .validate();
    }

    public ProjectAggregate remove(String modifyUserId) {
        if (this.isRemoved) {
            throw new AggregateRemoveException("已经被删除的聚合根<项目聚合根>不能再次删除, ID: " + projectId);
        }
        if (!canRemove()) {
            throw new AggregateRemoveException("聚合根<项目聚合根>处于不可删除状态, ID: " + projectId);
        }
        this.isRemoved = true;
        this.modifyUserId = modifyUserId;
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    @Override
    public ProjectAggregate validate() {
        validateProjectId();
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
        validateCreateUserId();
        validateModifyUserId();
        validateIsRemoved();
        return this;
    }

    private void validateProjectId() {
        if (!StringUtils.hasText(projectId)) {
            throw new AggregateValidateException("项目ID不能为空");
        }
    }

    private void validateProjectArtifactId() {
        if (!StringUtils.hasText(projectArtifactId)) {
            throw new AggregateValidateException("项目构件ID不能为空");
        }
    }

    private void validateGroupId() {
        if (!StringUtils.hasText(groupId)) {
            throw new AggregateValidateException("项目组织ID不能为空");
        }
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("项目名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("项目描述不能为空");
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new AggregateValidateException("项目版本号不能为空");
        }
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new AggregateValidateException("项目作者不能为空");
        }
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new AggregateValidateException("项目根路径不能为空");
        }
    }

    private void validateBasePackage() {
        if (!StringUtils.hasText(basePackage)) {
            throw new AggregateValidateException("项目基础包名不能为空");
        }
    }

    private void validateEnableSpringDoc() {
        if (enableSpringDoc == null) {
            throw new AggregateValidateException("是否启用Spring Doc不能为 null ");
        }
    }

    private void validateJavaVersion() {
        if (javaVersion == null) {
            throw new AggregateValidateException("项目Java版本不能为 null ");
        }
    }

    private void validateLoggingFramework() {
        if (loggingFramework == null) {
            throw new AggregateValidateException("项目日志框架不能为 null ");
        }
    }

    private void validatePersistenceFramework() {
        if (persistenceFramework == null) {
            throw new AggregateValidateException("项目持久化框架不能为 null ");
        }
    }

    private void validateCreateUserId() {
        if (!StringUtils.hasText(createUserId)) {
            throw new AggregateValidateException("创建者ID不能为空");
        }
    }

    private void validateModifyUserId() {
        if (!StringUtils.hasText(modifyUserId)) {
            throw new AggregateValidateException("修改者ID不能为空");
        }
    }

    private void validateIsRemoved() {
        if (isRemoved == null) {
            throw new AggregateValidateException("是否已删除不能为 null ");
        }
    }
}
