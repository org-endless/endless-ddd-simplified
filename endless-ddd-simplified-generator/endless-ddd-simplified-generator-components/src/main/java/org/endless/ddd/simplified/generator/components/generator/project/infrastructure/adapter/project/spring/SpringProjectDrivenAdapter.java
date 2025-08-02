package org.endless.ddd.simplified.generator.components.generator.project.infrastructure.adapter.project.spring;

import freemarker.template.Configuration;
import org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorContentDrivenAdapter;
import org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorFileDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
public class SpringProjectDrivenAdapter implements ProjectDrivenAdapter, DDDSimplifiedGeneratorContentDrivenAdapter, DDDSimplifiedGeneratorFileDrivenAdapter {

    private final Configuration freemarkerConfig;

    public SpringProjectDrivenAdapter(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void freemarker(ProjectAggregate aggregate, String fileName, String templateFileName) {
        Map<String, Object> model = new HashMap<>();
        model.put("projectAggregate", aggregate);
        String content = DDDSimplifiedGeneratorContentDrivenAdapter.super.freemarker(freemarkerConfig, model, templateFileName);
        if (StringUtils.hasText(content)) {
            write(aggregate.getRootPath(), content, fileName);
        }
    }
}
