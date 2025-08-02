package org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption;

import org.endless.ddd.simplified.generator.common.model.domain.anticorruption.DDDSimplifiedGeneratorDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;

/**
 * ProjectDrivenAdapter
 * <p>
 * create 2025/07/29 21:13
 * <p>
 * update 2025/07/29 21:13
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorDrivenAdapter
 * @since 1.0.0
 */
public interface ProjectDrivenAdapter extends DDDSimplifiedGeneratorDrivenAdapter {

    void freemarker(ProjectAggregate aggregate, String fileName, String templateFileName);
}
