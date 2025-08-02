package org.endless.ddd.simplified.generator.common.model.domain.anticorruption;

import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.starter.common.model.domain.anticorruption.Repository;

/**
 * DDDSimplifiedGeneratorRepository
 * <p>
 * create 2025/08/02 19:31
 * <p>
 * update 2025/08/02 19:32
 *
 * @author Deng Haozhi
 * @see Repository
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorRepository
        <A extends DDDSimplifiedGeneratorAggregate> extends Repository<A> {

}
