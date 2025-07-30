package org.endless.ddd.simplified.generator.components.generator.context.domain.anticorruption;

import org.endless.ddd.simplified.generator.common.model.domain.anticorruption.DDDSimplifiedGeneratorDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.context.domain.entity.ContextAggregate;

/**
 * ContextDrivenAdapter
 * <p>
 * 上下文领域被动适配器
 * <p>
 * create 2025/07/29 21:13
 * <p>
 * update 2025/07/29 21:13
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorDrivenAdapter
 * @since 1.0.0
 */
public interface ContextDrivenAdapter extends DDDSimplifiedGeneratorDrivenAdapter {

    void save(ContextAggregate aggregate);

}
