package org.endless.ddd.simplified.generator.common.model.infrastructure.data.manager;

import org.endless.ddd.simplified.generator.common.model.application.query.anticorruption.DDDSimplifiedGeneratorQueryRepository;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorEntity;
import org.endless.ddd.simplified.generator.common.model.infrastructure.data.record.DDDSimplifiedGeneratorRecord;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.manager.EntityDataManager;

/**
 * DDDSimplifiedGeneratorEntityDataManager
 * <p>
 * create 2024/09/03 12:25
 * <p>
 * update 2024/11/03 19:03
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorQueryRepository
 * @see EntityDataManager
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorEntityDataManager<R extends DDDSimplifiedGeneratorRecord<E>, E extends DDDSimplifiedGeneratorEntity>
        extends DDDSimplifiedGeneratorQueryRepository<E>, EntityDataManager<R, E> {

}
