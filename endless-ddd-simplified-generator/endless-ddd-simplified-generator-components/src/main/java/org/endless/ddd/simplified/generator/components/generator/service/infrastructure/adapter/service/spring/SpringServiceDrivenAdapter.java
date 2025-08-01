package org.endless.ddd.simplified.generator.components.generator.service.infrastructure.adapter.service.spring;

import org.endless.ddd.simplified.generator.common.model.infrastructure.adapter.DDDSimplifiedGeneratorContentDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.service.domain.anticorruption.ServiceDrivenAdapter;
import org.endless.ddd.simplified.generator.components.generator.service.domain.entity.ServiceAggregate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * SpringServiceDrivenAdapter
 * <p>
 * create 2025/08/01 15:26
 * <p>
 * update 2025/08/01 15:26
 *
 * @author Deng Haozhi
 * @see ServiceDrivenAdapter
 * @since 1.0.0
 */
@Lazy
@Component
public class SpringServiceDrivenAdapter implements ServiceDrivenAdapter, DDDSimplifiedGeneratorContentDrivenAdapter {

    @Override
    public String yaml(ServiceAggregate aggregate) {
        return DDDSimplifiedGeneratorContentDrivenAdapter.super.yaml(aggregate);
    }
}
