package org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.config;

import org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler.AggregateCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler.impl.AggregateCommandHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.domain.application.query.handler.AggregateQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.query.handler.impl.AggregateQueryHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.AggregateDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.spring.SpringAggregateDrivingAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * AggregateDrivingConfiguration
 * <p>
 * 聚合领域主动适配器配置类
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @since 0.0.1
 */
@Lazy
@Configuration
public class AggregateDrivingConfiguration {

    @Lazy
    @Primary
    @ConditionalOnMissingBean
    public @Bean AggregateDrivingAdapter springAggregateDrivingAdapter(AggregateCommandHandler commandHandler,
            AggregateQueryHandler queryHandler) {
        return new SpringAggregateDrivingAdapter(commandHandler, queryHandler);
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean AggregateCommandHandler aggregateCommandHandler() {
        return new AggregateCommandHandlerImpl();
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean AggregateQueryHandler aggregateQueryHandler() {
        return new AggregateQueryHandlerImpl();
    }
}
