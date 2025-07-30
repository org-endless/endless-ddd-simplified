package org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.config;

import org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler.DomainCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.handler.impl.DomainCommandHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.domain.application.query.handler.DomainQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.domain.application.query.handler.impl.DomainQueryHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.DomainDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter.spring.SpringDomainDrivingAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * DomainDrivingConfiguration
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
public class DomainDrivingConfiguration {

    @Lazy
    @Primary
    @ConditionalOnMissingBean
    public @Bean DomainDrivingAdapter springDomainDrivingAdapter(DomainCommandHandler commandHandler, DomainQueryHandler queryHandler) {
        return new SpringDomainDrivingAdapter(commandHandler, queryHandler);
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean DomainCommandHandler aggregateCommandHandler() {
        return new DomainCommandHandlerImpl();
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean DomainQueryHandler aggregateQueryHandler() {
        return new DomainQueryHandlerImpl();
    }
}
