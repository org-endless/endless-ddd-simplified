package org.endless.ddd.simplified.generator.common.model.domain.event;

import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.starter.common.model.domain.event.Event;

/**
 * DDDSimplifiedGeneratorEvent
 * <p>
 * create 2024/09/06 15:14
 * <p>
 * update 2024/09/06 15:15
 *
 * @author Deng Haozhi
 * @see Event
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorEvent<A extends DDDSimplifiedGeneratorAggregate>
        extends Event<A> {

}
