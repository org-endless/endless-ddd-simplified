package org.endless.ddd.simplified.generator.components.generator.service.application.command.handler.impl;

import org.endless.ddd.simplified.generator.components.generator.service.application.command.handler.ServiceCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer.ServiceCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer.ServiceCreateRespCTransfer;
import org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer.ServiceFileCreateRespCTransfer;
import org.endless.ddd.simplified.generator.components.generator.service.domain.anticorruption.ServiceDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.service.domain.entity.ServiceAggregate;
import org.endless.ddd.simplified.generator.components.generator.service.domain.type.ServiceTypeEnum;
import org.endless.ddd.simplified.starter.common.config.log.annotation.Log;
import org.endless.ddd.simplified.starter.common.config.log.type.LogLevel;
import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandReqTransferNullException;
import org.endless.ddd.simplified.starter.common.utils.model.time.DateTime;
import org.endless.ddd.simplified.starter.common.utils.model.time.TimeStamp;

import java.util.List;
import java.util.Optional;

/**
 * ServiceCommandHandlerImpl
 * <p>
 * 服务领域命令处理器实现类
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see ServiceCommandHandler
 * @since 0.0.1
 */
public class ServiceCommandHandlerImpl implements ServiceCommandHandler {

    private final ServiceDrivenAdapter serviceDrivenAdapter;

    public ServiceCommandHandlerImpl(ServiceDrivenAdapter serviceDrivenAdapter) {
        this.serviceDrivenAdapter = serviceDrivenAdapter;
    }

    @Override
    @Log(message = "服务创建命令", value = "#command", level = LogLevel.TRACE)
    public ServiceCreateRespCTransfer create(ServiceCreateReqCTransfer command) {
        Optional.ofNullable(command)
                .map(ServiceCreateReqCTransfer::validate)
                .orElseThrow(() -> new CommandReqTransferNullException("服务创建命令建参数不能为空"));

        ServiceAggregate aggregate = ServiceAggregate.create(ServiceAggregate.builder()
                .serviceArtifactId(command.serviceArtifactId())
                .projectArtifactId(command.serviceArtifactId())
                .name(command.name())
                .description(command.description())
                .version(command.version())
                .author(command.author())
                .rootPath(command.rootPath())
                .basePackage(command.basePackage())
                .type(ServiceTypeEnum.fromCode(command.type()))
                .port(command.port())
                .contextNames(command.contextNames())
                .createAt(DateTime.from(TimeStamp.now()))
                .updateAt(DateTime.from(TimeStamp.now())));

        // 服务存储YAML文件
        String serviceYamlFilePath = aggregate.getRootPath();
        String serviceYamlFileName = aggregate.getServiceArtifactId() + ".yaml";
        String serviceYamlContent = serviceDrivenAdapter.yaml(aggregate);
        ServiceFileCreateRespCTransfer yamlFile = ServiceFileCreateRespCTransfer.builder()
                .rootPath(serviceYamlFilePath)
                .packageName(aggregate.getBasePackage())
                .fileName(serviceYamlFileName)
                .content(serviceYamlContent)
                .build().validate();

        //     开始构建项目结构
        //     构建POM文件
        //     构建Common服务
        //     构建Core启动服务
        return ServiceCreateRespCTransfer.builder()
                .rootPath(aggregate.getRootPath())
                .packageName(aggregate.getBasePackage())
                .files(List.of(yamlFile))
                .build().validate();
    }
}
