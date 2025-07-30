package org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.spring;

import org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler.DomainCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.transfer.DomainCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.domain.application.query.handler.DomainQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.DomainDrivingAdapter;

/**
 * SpringDomainDrivingAdapter
 * <p>
 * 聚合领域主动适配器Spring实现类
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DomainDrivingAdapter
 * @since 0.0.1
 */
public class SpringDomainDrivingAdapter implements DomainDrivingAdapter {

    /**
     * 聚合领域命令处理器
     */
    private final DomainCommandHandler aggregateCommandHandler;

    /**
     * 聚合领域查询处理器
     */
    private final DomainQueryHandler aggregateQueryHandler;

    public SpringDomainDrivingAdapter(DomainCommandHandler aggregateCommandHandler,
                                      DomainQueryHandler aggregateQueryHandler) {
        this.aggregateCommandHandler = aggregateCommandHandler;
        this.aggregateQueryHandler = aggregateQueryHandler;
    }

    @Override
    public void create(DomainCreateReqCTransfer command) {
        aggregateCommandHandler.create(command);
    }
}
