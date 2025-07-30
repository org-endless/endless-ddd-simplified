package org.endless.ddd.simplified.generator.components.generator.project.facade.adapter.spring;

import org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.ProjectCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.application.query.handler.ProjectQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.project.facade.adapter.ProjectDrivingAdapter;

/**
 * SpringProjectDrivingAdapter
 * <p>项目领域主动适配器Spring实现类
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see ProjectDrivingAdapter
 * @since 0.0.1
 */
public class SpringProjectDrivingAdapter implements ProjectDrivingAdapter {

    /**
     * 项目领域命令处理器
     */
    private final ProjectCommandHandler projectCommandHandler;

    /**
     * 项目领域查询处理器
     */
    private final ProjectQueryHandler projectQueryHandler;

    public SpringProjectDrivingAdapter(ProjectCommandHandler projectCommandHandler, ProjectQueryHandler projectQueryHandler) {
        this.projectCommandHandler = projectCommandHandler;
        this.projectQueryHandler = projectQueryHandler;
    }

    @Override
    public void create(ProjectCreateReqCTransfer command) {
        projectCommandHandler.create(command);
    }
}
