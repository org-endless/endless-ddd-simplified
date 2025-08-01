package org.endless.ddd.simplified.generator.components.generator.project.infrastructure.adapter.project.spring;

import freemarker.template.Configuration;
import org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorContentDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * SpringProjectDrivenAdapter
 * <p>
 * create 2025/07/29 21:14
 * <p>
 * update 2025/07/29 21:14
 *
 * @author Deng Haozhi
 * @see ProjectDrivenAdapter
 * @since 1.0.0
 */
@Lazy
@Component
public class SpringProjectDrivenAdapter implements ProjectDrivenAdapter, DDDSimplifiedGeneratorContentDrivenAdapter {


    private final Configuration freemarkerConfig;

    public SpringProjectDrivenAdapter(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public String yaml(ProjectAggregate aggregate) {
        return DDDSimplifiedGeneratorContentDrivenAdapter.super.yaml(aggregate);
    }

    @Override
    public String freemarker(ProjectAggregate aggregate, String templateFileName) {
        return DDDSimplifiedGeneratorContentDrivenAdapter.super.freemarker(freemarkerConfig, aggregate, templateFileName);
    }
}
