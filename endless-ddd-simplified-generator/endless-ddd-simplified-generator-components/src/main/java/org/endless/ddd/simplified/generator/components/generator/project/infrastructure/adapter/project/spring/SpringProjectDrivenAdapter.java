package org.endless.ddd.simplified.generator.components.generator.project.infrastructure.adapter.project.spring;

import org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorContentDrivenAdapter;
import org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorFileDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;

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
public class SpringProjectDrivenAdapter implements ProjectDrivenAdapter, DDDSimplifiedGeneratorFileDrivenAdapter, DDDSimplifiedGeneratorContentDrivenAdapter {

    @Override
    public void save(ProjectAggregate aggregate) {
        write(aggregate.getRootPath(), null, aggregate.getName() + ".yaml", yaml(aggregate));
    }
}
