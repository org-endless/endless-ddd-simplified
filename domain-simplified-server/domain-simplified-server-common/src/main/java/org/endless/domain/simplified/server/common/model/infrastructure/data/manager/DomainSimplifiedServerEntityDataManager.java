package org.endless.domain.simplified.server.common.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.model.infrastructure.data.manager.EntityDataManager;
import org.endless.domain.simplified.server.common.model.application.query.anticorruption.DomainSimplifiedServerQueryRepository;
import org.endless.domain.simplified.server.common.model.domain.entity.DomainSimplifiedServerEntity;
import org.endless.domain.simplified.server.common.model.infrastructure.data.record.DomainSimplifiedServerRecord;

/**
 * DomainSimplifiedServerEntityDataManager
 * <p>
 * create 2024/09/03 12:25
 * <p>
 * update 2024/11/03 19:03
 *
 * @author Deng Haozhi
 * @see DomainSimplifiedServerQueryRepository
 * @see EntityDataManager
 * @since 1.0.0
 */
public interface DomainSimplifiedServerEntityDataManager<R extends DomainSimplifiedServerRecord<E>, E extends DomainSimplifiedServerEntity>
        extends DomainSimplifiedServerQueryRepository<E>, EntityDataManager<R, E> {

}
