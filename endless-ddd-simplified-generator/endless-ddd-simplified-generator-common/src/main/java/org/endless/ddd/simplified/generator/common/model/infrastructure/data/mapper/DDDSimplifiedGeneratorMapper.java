package org.endless.ddd.simplified.generator.common.model.infrastructure.data.mapper;

import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorEntity;
import org.endless.ddd.simplified.generator.common.model.infrastructure.data.record.DDDSimplifiedGeneratorRecord;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.persistence.mapper.DataMapper;

/**
 * DDDSimplifiedGeneratorMapper
 * <p>
 * create 2024/09/03 09:35
 * <p>
 * update 2024/09/03 12:09
 *
 * @see DataMapper
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorMapper<R extends DDDSimplifiedGeneratorRecord<? extends DDDSimplifiedGeneratorEntity>>
        extends DataMapper<R> {

}
