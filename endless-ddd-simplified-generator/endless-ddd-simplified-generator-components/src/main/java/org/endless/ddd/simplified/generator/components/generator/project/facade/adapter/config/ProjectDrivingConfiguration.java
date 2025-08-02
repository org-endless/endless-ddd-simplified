package org.endless.ddd.simplified.generator.components.generator.project.facade.adapter.config;

import org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.ProjectCommandHandler;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.handler.impl.ProjectCommandHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.project.application.query.handler.ProjectQueryHandler;
import org.endless.ddd.simplified.generator.components.generator.project.application.query.handler.impl.ProjectQueryHandlerImpl;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectRepository;
import org.endless.ddd.simplified.generator.components.generator.project.facade.adapter.ProjectDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.facade.adapter.spring.SpringProjectDrivingAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * ProjectDrivingConfiguration
 * <p>项目领域主动适配器配置类
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
public class ProjectDrivingConfiguration {

    @Lazy
    @Primary
    @ConditionalOnMissingBean
    public @Bean ProjectDrivingAdapter springProjectDrivingAdapter(ProjectCommandHandler commandHandler, ProjectQueryHandler queryHandler) {
        return new SpringProjectDrivingAdapter(commandHandler, queryHandler);
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean ProjectCommandHandler projectCommandHandler(ProjectRepository projectRepository, ProjectDrivenAdapter projectDrivenAdapter) {
        return new ProjectCommandHandlerImpl(projectRepository, projectDrivenAdapter);
    }

    @Lazy
    @ConditionalOnMissingBean
    protected @Bean ProjectQueryHandler projectQueryHandler() {
        return new ProjectQueryHandlerImpl();
    }
}
