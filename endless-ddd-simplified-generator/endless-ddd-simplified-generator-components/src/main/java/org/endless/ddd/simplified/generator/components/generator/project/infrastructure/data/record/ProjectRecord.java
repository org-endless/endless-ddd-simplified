package org.endless.ddd.simplified.generator.components.generator.project.infrastructure.data.record;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.endless.ddd.simplified.generator.common.model.infrastructure.data.record.DDDSimplifiedGeneratorRecord;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectJavaVersionEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectLoggingFrameworkEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectPersistenceFrameworkEnum;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.record.DataRecordValidateException;
import org.springframework.util.StringUtils;

/**
 * ProjectRecord
 * <p>项目数据库记录实体
 * <p>
 * create 2025/08/02 20:02
 * <p>
 * update 2025/08/02 20:02
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorRecord
 * @since 0.0.1
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "generator_project")
public class ProjectRecord implements DDDSimplifiedGeneratorRecord<ProjectAggregate> {

    /**
     * 项目ID
     */
    @TableId
    private String projectId;

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
    private String createUserId;

    /**
     * 修改者ID
     */
    private String modifyUserId;

    /**
     * 是否已删除
     */
    private Boolean isRemoved;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createAt;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long modifyAt;

    /**
     * 删除时间
     */
    private Long removeAt;

    public static ProjectRecord from(ProjectAggregate aggregate) {
        String projectId = aggregate.getProjectId();
        return ProjectRecord.builder()
                .projectId(projectId)
                .projectArtifactId(aggregate.getProjectArtifactId())
                .groupId(aggregate.getGroupId())
                .name(aggregate.getName())
                .description(aggregate.getDescription())
                .version(aggregate.getVersion())
                .author(aggregate.getAuthor())
                .rootPath(aggregate.getRootPath())
                .basePackage(aggregate.getBasePackage())
                .enableSpringDoc(aggregate.getEnableSpringDoc())
                .javaVersion(aggregate.getJavaVersion())
                .loggingFramework(aggregate.getLoggingFramework())
                .persistenceFramework(aggregate.getPersistenceFramework())
                .createUserId(aggregate.getCreateUserId())
                .modifyUserId(aggregate.getModifyUserId())
                .isRemoved(aggregate.getIsRemoved())
                .build()
                .validate();
    }

    public ProjectAggregate to() {
        validate();
        return ProjectAggregate.builder()
                .projectId(projectId)
                .projectArtifactId(projectArtifactId)
                .groupId(groupId)
                .name(name)
                .description(description)
                .version(version)
                .author(author)
                .rootPath(rootPath)
                .basePackage(basePackage)
                .enableSpringDoc(enableSpringDoc)
                .javaVersion(javaVersion)
                .loggingFramework(loggingFramework)
                .persistenceFramework(persistenceFramework)
                .createUserId(createUserId)
                .modifyUserId(modifyUserId)
                .isRemoved(isRemoved)
                .innerBuild();
    }

    @Override
    public ProjectRecord validate() {
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
            throw new DataRecordValidateException("项目ID不能为空");
        }
    }

    private void validateProjectArtifactId() {
        if (!StringUtils.hasText(projectArtifactId)) {
            throw new DataRecordValidateException("项目构件ID不能为空");
        }
    }

    private void validateGroupId() {
        if (!StringUtils.hasText(groupId)) {
            throw new DataRecordValidateException("项目组织ID不能为空");
        }
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new DataRecordValidateException("项目名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new DataRecordValidateException("项目描述不能为空");
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new DataRecordValidateException("项目版本号不能为空");
        }
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new DataRecordValidateException("项目作者不能为空");
        }
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new DataRecordValidateException("项目根路径不能为空");
        }
    }

    private void validateBasePackage() {
        if (!StringUtils.hasText(basePackage)) {
            throw new DataRecordValidateException("项目基础包名不能为空");
        }
    }

    private void validateEnableSpringDoc() {
        if (enableSpringDoc == null) {
            throw new DataRecordValidateException("是否启用Spring Doc不能为 null ");
        }
    }

    private void validateJavaVersion() {
        if (javaVersion == null) {
            throw new DataRecordValidateException("项目Java版本不能为 null ");
        }
    }

    private void validateLoggingFramework() {
        if (loggingFramework == null) {
            throw new DataRecordValidateException("项目日志框架不能为 null ");
        }
    }

    private void validatePersistenceFramework() {
        if (persistenceFramework == null) {
            throw new DataRecordValidateException("项目持久化框架不能为 null ");
        }
    }

    private void validateCreateUserId() {
        if (!StringUtils.hasText(createUserId)) {
            throw new DataRecordValidateException("创建者ID不能为空");
        }
    }

    private void validateModifyUserId() {
        if (!StringUtils.hasText(modifyUserId)) {
            throw new DataRecordValidateException("修改者ID不能为空");
        }
    }

    private void validateIsRemoved() {
        if (isRemoved == null) {
            throw new DataRecordValidateException("是否已删除不能为 null ");
        }
    }
}
