package org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.impl;

import org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.ProjectCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectModifyReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectRepository;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectJavaVersionEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectLoggingFrameworkEnum;
import org.endless.ddd.simplified.generator.components.generator.project.domain.type.ProjectPersistenceFrameworkEnum;
import org.endless.ddd.simplified.starter.common.config.log.annotation.Log;
import org.endless.ddd.simplified.starter.common.config.log.type.LogLevel;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandReqTransferNullException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ProjectCommandHandlerImpl
 * <p>项目领域命令处理器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see ProjectCommandHandler
 * @since 0.0.1
 */
public class ProjectCommandHandlerImpl implements ProjectCommandHandler {

    /**
     * 项目仓储
     */
    private final ProjectRepository projectRepository;

    /**
     * 项目被动适配器
     */
    private final ProjectDrivenAdapter projectDrivenAdapter;

    public ProjectCommandHandlerImpl(ProjectRepository projectRepository, ProjectDrivenAdapter projectDrivenAdapter) {
        this.projectRepository = projectRepository;
        this.projectDrivenAdapter = projectDrivenAdapter;
    }

    @Override
    @Transactional
    @Log(message = "项目创建命令", value = "#command", level = LogLevel.TRACE)
    public void create(ProjectCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(ProjectCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("项目创建命令参数不能为空"));

        ProjectAggregate aggregate = ProjectAggregate.create(ProjectAggregate.builder()
                .projectArtifactId(command.projectArtifactId())
                .groupId(command.groupId())
                .name(command.name())
                .description(command.description())
                .version(command.version())
                .author(command.author())
                .rootPath(command.rootPath())
                .basePackage(command.basePackage())
                .enableSpringDoc(command.enableSpringDoc())
                .javaVersion(ProjectJavaVersionEnum.fromCode(command.javaVersion()))
                .loggingFramework(ProjectLoggingFrameworkEnum.fromCode(command.loggingFramework()))
                .persistenceFramework(ProjectPersistenceFrameworkEnum.fromCode(command.persistenceFramework()))
                .createUserId(DDD_SIMPLIFIED_GENERATOR_USER_ID));
        projectRepository.save(aggregate);
        // 项目POM文件
        projectDrivenAdapter.freemarker(aggregate, "pom.xml", "project/ProjectPom.ftl");
    }

    @Override
    public void modify(ProjectModifyReqCTransfer command) {

    }
}
