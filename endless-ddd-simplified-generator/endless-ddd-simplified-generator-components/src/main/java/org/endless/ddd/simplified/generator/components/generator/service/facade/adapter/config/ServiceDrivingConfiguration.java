package org.endless.ddd.simplified.generator.components.generator.service.facade.adapter.config;

import org.endless.ddd.simplified.generator.components.generator.service.application.command.handler.ServiceCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.service.application.command.handler.impl.ServiceCommandHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.service.application.query.handler.ServiceQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.service.application.query.handler.impl.ServiceQueryHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.service.facade.adapter.ServiceDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.service.facade.adapter.spring.SpringServiceDrivingAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * ServiceDrivingConfiguration
 * <p>
 * 服务领域主动适配器配置类
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
public class ServiceDrivingConfiguration {

    @Lazy
    @Primary
    @ConditionalOnMissingBean
    public @Bean ServiceDrivingAdapter springServiceDrivingAdapter(ServiceCommandHandler commandHandler,
            ServiceQueryHandler queryHandler) {
        return new SpringServiceDrivingAdapter(commandHandler, queryHandler);
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean ServiceCommandHandler serviceCommandHandler() {
        return new ServiceCommandHandlerImpl();
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean ServiceQueryHandler serviceQueryHandler() {
        return new ServiceQueryHandlerImpl();
    }
}