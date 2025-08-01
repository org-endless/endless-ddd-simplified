package org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.impl;

import org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.ProjectCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateRespCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectFileCreateRespCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;
import org.endless.ddd.simplified.starter.common.config.log.annotation.Log;
import org.endless.ddd.simplified.starter.common.config.log.type.LogLevel;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandReqTransferNullException;
import org.endless.ddd.simplified.starter.common.utils.model.time.DateTime;
import org.endless.ddd.simplified.starter.common.utils.model.time.TimeStamp;

import java.util.List;
import java.util.Optional;

import static org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorFileDrivenAdapter.DEFAULT_DESIGN_DIRECTORY;

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

    private final ProjectDrivenAdapter projectDrivenAdapter;

    public ProjectCommandHandlerImpl(ProjectDrivenAdapter projectDrivenAdapter) {
        this.projectDrivenAdapter = projectDrivenAdapter;
    }

    @Override
    @Log(message = "项目创建", value = "#command", level = LogLevel.TRACE)
    public ProjectCreateRespCTransfer create(ProjectCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(ProjectCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("项目创建参数不能为空"));
        ProjectAggregate aggregate = ProjectAggregate.create(ProjectAggregate.builder()
                .projectArtifactId(command.getProjectArtifactId())
                .groupId(command.getGroupId())
                .name(command.getName())
                .description(command.getDescription())
                .version(command.getVersion())
                .author(command.getAuthor())
                .rootPath(command.getRootPath())
                .basePackage(command.getBasePackage())
                .enableSpringDoc(command.getEnableSpringDoc())
                .javaVersion(command.getJavaVersion())
                .loggingFramework(command.getLoggingFramework())
                .persistenceFramework(command.getPersistenceFramework())
                .serviceArtifactIds(command.getServiceArtifactIds())
                .createAt(DateTime.from(TimeStamp.now()))
                .updateAt(DateTime.from(TimeStamp.now())));

        // 项目存储YAML文件
        String projectYamlPath = aggregate.getRootPath() + "/" + DEFAULT_DESIGN_DIRECTORY + "/" + aggregate.getProjectArtifactId();
        String projectYamlName = aggregate.getProjectArtifactId() + ".yaml";
        String projectYamlContent = projectDrivenAdapter.yaml(aggregate);
        ProjectFileCreateRespCTransfer projectYaml = ProjectFileCreateRespCTransfer.builder()
                .rootPath(projectYamlPath)
                .packageName(aggregate.getBasePackage())
                .fileName(projectYamlName)
                .content(projectYamlContent)
                .build().validate();

        // 项目POM文件
        String projectPomPath = aggregate.getRootPath();
        String projectPomName = "pom.xml";
        String projectPomContent = projectDrivenAdapter.freemarker(aggregate, "project/ProjectPom.ftl");
        ProjectFileCreateRespCTransfer projectPom = ProjectFileCreateRespCTransfer.builder()
                .rootPath(projectPomPath)
                .packageName(aggregate.getBasePackage())
                .fileName(projectPomName)
                .content(projectPomContent)
                .build().validate();

        //     构建Common服务
        //     构建Core启动服务
        return ProjectCreateRespCTransfer.builder()
                .rootPath(aggregate.getRootPath())
                .packageName(aggregate.getBasePackage())
                .files(List.of(projectYaml, projectPom))
                .build().validate();
    }
}
