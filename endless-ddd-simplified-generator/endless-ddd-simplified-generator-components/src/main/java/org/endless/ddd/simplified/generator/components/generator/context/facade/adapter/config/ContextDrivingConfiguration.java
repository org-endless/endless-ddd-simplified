package org.endless.ddd.simplified.generator.components.generator.context.facade.adapter.config;

import org.endless.ddd.simplified.generator.components.generator.context.application.command.handler.ContextCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.context.application.command.handler.impl.ContextCommandHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.context.application.query.handler.ContextQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.context.application.query.handler.impl.ContextQueryHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.context.facade.adapter.ContextDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.context.facade.adapter.spring.SpringContextDrivingAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * ContextDrivingConfiguration
 * <p>
 * 上下文领域主动适配器配置类
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
public class ContextDrivingConfiguration {

    @Lazy
    @Primary
    @ConditionalOnMissingBean
    public @Bean ContextDrivingAdapter springContextDrivingAdapter(ContextCommandHandler commandHandler,
            ContextQueryHandler queryHandler) {
        return new SpringContextDrivingAdapter(commandHandler, queryHandler);
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean ContextCommandHandler contextCommandHandler() {
        return new ContextCommandHandlerImpl();
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean ContextQueryHandler contextQueryHandler() {
        return new ContextQueryHandlerImpl();
    }
}