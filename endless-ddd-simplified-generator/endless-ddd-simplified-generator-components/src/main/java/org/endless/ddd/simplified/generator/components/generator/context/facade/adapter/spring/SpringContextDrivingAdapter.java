package org.endless.ddd.simplified.generator.components.generator.context.facade.adapter.spring;

import org.endless.ddd.simplified.generator.components.generator.context.application.command.handler.ContextCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.context.application.command.transfer.ContextCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.context.application.query.handler.ContextQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.context.facade.adapter.ContextDrivingAdapter;

/**
 * SpringContextDrivingAdapter
 * <p>
 * 上下文领域主动适配器Spring实现类
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see ContextDrivingAdapter
 * @since 0.0.1
 */
public class SpringContextDrivingAdapter implements ContextDrivingAdapter {

    /**
     * 上下文领域命令处理器
     */
    private final ContextCommandHandler contextCommandHandler;

    /**
     * 上下文领域查询处理器
     */
    private final ContextQueryHandler contextQueryHandler;

    public SpringContextDrivingAdapter(ContextCommandHandler contextCommandHandler,
            ContextQueryHandler contextQueryHandler) {
        this.contextCommandHandler = contextCommandHandler;
        this.contextQueryHandler = contextQueryHandler;
    }

    @Override
    public void create(ContextCreateReqCTransfer command) {
        contextCommandHandler.create(command);
    }
}