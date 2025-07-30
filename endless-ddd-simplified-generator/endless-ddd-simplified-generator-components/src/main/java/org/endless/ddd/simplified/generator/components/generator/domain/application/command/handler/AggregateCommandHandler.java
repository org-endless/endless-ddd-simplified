package org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler;

import org.endless.ddd.simplified.generator.common.model.application.command.handler.DDDSimplifiedGeneratorCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.transfer.AggregateCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.AggregateDomainAggregate;

/**
 * AggregateCommandHandler
 * <p>
 * 聚合领域命令处理器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:21
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorCommandHandler
 * @since 0.0.1
 */
public interface AggregateCommandHandler extends DDDSimplifiedGeneratorCommandHandler<AggregateDomainAggregate> {

    void create(AggregateCreateReqCTransfer command);

}
