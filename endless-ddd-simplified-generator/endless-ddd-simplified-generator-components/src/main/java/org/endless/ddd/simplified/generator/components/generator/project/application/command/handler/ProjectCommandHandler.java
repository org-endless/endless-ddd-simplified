package org.endless.ddd.simplified.generator.components.generator.project.application.command.handler;

import org.endless.ddd.simplified.generator.common.model.application.command.handler.DDDSimplifiedGeneratorCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectModifyReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;

/**
 * ProjectCommandHandler
 * <p>项目领域命令处理器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:21
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorCommandHandler
 * @since 0.0.1
 */
public interface ProjectCommandHandler extends DDDSimplifiedGeneratorCommandHandler<ProjectAggregate> {

    void create(ProjectCreateReqCTransfer command);

    void modify(ProjectModifyReqCTransfer command);

}
