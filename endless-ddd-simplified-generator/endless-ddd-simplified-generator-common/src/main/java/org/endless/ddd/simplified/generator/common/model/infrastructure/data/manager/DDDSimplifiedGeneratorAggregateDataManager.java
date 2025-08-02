package org.endless.ddd.simplified.generator.common.model.infrastructure.data.manager;

import org.endless.ddd.simplified.generator.common.model.application.query.anticorruption.DDDSimplifiedGeneratorQueryRepository;
import org.endless.ddd.simplified.generator.common.model.domain.anticorruption.DDDSimplifiedGeneratorRepository;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.generator.common.model.infrastructure.data.record.DDDSimplifiedGeneratorRecord;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.manager.AggregateDataManager;

/**
 * DDDSimplifiedGeneratorAggregateDataManager
 * <p>
 * create 2024/09/03 12:25
 * <p>
 * update 2024/09/12 13:19
 * update 2024/11/03 19:03
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorRepository
 * @see DDDSimplifiedGeneratorQueryRepository
 * @see AggregateDataManager
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorAggregateDataManager<R extends DDDSimplifiedGeneratorRecord<A>, A extends DDDSimplifiedGeneratorAggregate>
        extends DDDSimplifiedGeneratorRepository<A>, DDDSimplifiedGeneratorQueryRepository<A>, AggregateDataManager<R, A> {

}
