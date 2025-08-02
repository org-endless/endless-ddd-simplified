package org.endless.ddd.simplified.generator.common.model.application.command.handler;

import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.starter.common.model.application.command.handler.CommandHandler;

/**
 * DDDSimplifiedGeneratorCommandHandler
 * <p>
 * create 2024/08/30 17:19
 * <p>
 * update 2024/08/30 17:20
 *
 * @author Deng Haozhi
 * @see CommandHandler
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorCommandHandler<A extends DDDSimplifiedGeneratorAggregate>
        extends CommandHandler<A> {

    String DDD_SIMPLIFIED_GENERATOR_USER_ID = "DDD_SIMPLIFIED_GENERATOR_USER_ID";
}
