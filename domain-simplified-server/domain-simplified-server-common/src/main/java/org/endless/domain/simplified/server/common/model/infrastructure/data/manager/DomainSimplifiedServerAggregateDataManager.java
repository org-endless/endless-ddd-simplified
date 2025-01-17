package org.endless.domain.simplified.server.common.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.model.infrastructure.data.manager.AggregateDataManager;
import org.endless.domain.simplified.server.common.model.application.query.anticorruption.DomainSimplifiedServerQueryRepository;
import org.endless.domain.simplified.server.common.model.domain.anticorruption.DomainSimplifiedServerRepository;
import org.endless.domain.simplified.server.common.model.domain.entity.DomainSimplifiedServerAggregate;
import org.endless.domain.simplified.server.common.model.infrastructure.data.record.DomainSimplifiedServerRecord;

/**
 * DomainSimplifiedServerAggregateDataManager
 * <p>
 * create 2024/09/03 12:25
 * <p>
 * update 2024/09/12 13:19
 * update 2024/11/03 19:03
 *
 * @author Deng Haozhi
 * @see DomainSimplifiedServerRepository
 * @see DomainSimplifiedServerQueryRepository
 * @see AggregateDataManager
 * @since 1.0.0
 */
public interface DomainSimplifiedServerAggregateDataManager<R extends DomainSimplifiedServerRecord<A>, A extends DomainSimplifiedServerAggregate>
        extends DomainSimplifiedServerRepository<A>, DomainSimplifiedServerQueryRepository<A>, AggregateDataManager<R, A> {

}
