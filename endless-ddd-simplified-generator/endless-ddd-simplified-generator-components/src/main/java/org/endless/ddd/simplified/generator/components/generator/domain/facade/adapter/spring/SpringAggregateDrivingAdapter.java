package org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.spring;

import org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler.AggregateCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.transfer.AggregateCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.domain.application.query.handler.AggregateQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.AggregateDrivingAdapter;

/**
 * SpringAggregateDrivingAdapter
 * <p>
 * 聚合领域主动适配器Spring实现类
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see AggregateDrivingAdapter
 * @since 0.0.1
 */
public class SpringAggregateDrivingAdapter implements AggregateDrivingAdapter {

    /**
     * 聚合领域命令处理器
     */
    private final AggregateCommandHandler aggregateCommandHandler;

    /**
     * 聚合领域查询处理器
     */
    private final AggregateQueryHandler aggregateQueryHandler;

    public SpringAggregateDrivingAdapter(AggregateCommandHandler aggregateCommandHandler,
            AggregateQueryHandler aggregateQueryHandler) {
        this.aggregateCommandHandler = aggregateCommandHandler;
        this.aggregateQueryHandler = aggregateQueryHandler;
    }

    @Override
    public void create(AggregateCreateReqCTransfer command) {
        aggregateCommandHandler.create(command);
    }
}
