package org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import org.endless.ddd.simplified.generator.common.model.application.command.transfer.DDDSimplifiedGeneratorCommandTransfer;

/**
 * ProjectModifyReqCTransfer
 * <p>项目修改命令请求传输对象
 * <p>
 * create 2025/08/02 20:50
 * <p>
 * update 2025/08/02 20:50
 *
 * @param projectArtifactId    项目构件ID
 * @param groupId              项目组织ID
 * @param name                 项目名称
 * @param description          项目描述
 * @param version              项目版本号
 * @param author               项目作者
 * @param rootPath             项目根路径
 * @param basePackage          项目基础包名
 * @param enableSpringDoc      项目是否启用Spring Doc
 * @param javaVersion          项目Java版本
 * @param loggingFramework     项目日志框架
 * @param persistenceFramework 项目持久化框架
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorCommandTransfer
 * @since 0.0.1
 */
@Builder
@JSONType(orders = {"projectArtifactId", "groupId", "name", "description", "version", "author", "rootPath", "basePackage", "enableSpringDoc", "javaVersion", "loggingFramework", "persistenceFramework"})
public record ProjectModifyReqCTransfer(
        String projectArtifactId, String groupId, String name, String description,
        String version, String author, String rootPath, String basePackage,
        Boolean enableSpringDoc, String javaVersion, String loggingFramework,
        String persistenceFramework) implements DDDSimplifiedGeneratorCommandTransfer {

    @Override
    public ProjectModifyReqCTransfer validate() {
        return this;
    }
}
