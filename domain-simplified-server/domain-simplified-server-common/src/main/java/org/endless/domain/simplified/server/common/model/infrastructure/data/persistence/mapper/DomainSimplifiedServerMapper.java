package org.endless.domain.simplified.server.common.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.model.infrastructure.data.persistence.mapper.DataMapper;
import org.endless.domain.simplified.server.common.model.domain.entity.DomainSimplifiedServerEntity;
import org.endless.domain.simplified.server.common.model.infrastructure.data.record.DomainSimplifiedServerRecord;

/**
 * DomainSimplifiedServerMapper
 * <p>
 * create 2024/09/03 09:35
 * <p>
 * update 2024/09/03 12:09
 *
 * @see DataMapper
 * @since 1.0.0
 */
public interface DomainSimplifiedServerMapper<R extends DomainSimplifiedServerRecord<? extends DomainSimplifiedServerEntity>>
        extends DataMapper<R> {

}
