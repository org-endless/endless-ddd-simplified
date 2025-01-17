package org.endless.ddd.simplified.starter.common.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.model.application.query.anticorruption.QueryRepository;
import org.endless.ddd.simplified.starter.common.model.domain.anticorruption.Repository;
import org.endless.ddd.simplified.starter.common.model.domain.entity.Aggregate;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.record.DataRecord;

/**
 * DataManager
 * <p>DDD聚合数据仓储管理类型模版
 * <p>工厂模式处理数据仓储逻辑
 * <p>
 * create 2024/09/03 12:25
 * <p>
 * update 2024/11/03 18:58
 *
 * @author Deng Haozhi
 * @see Repository
 * @see QueryRepository
 * @see DataManager
 * @since 1.0.0
 */
public interface AggregateDataManager<R extends DataRecord<A>, A extends Aggregate>
        extends Repository<A>, QueryRepository<A>, DataManager<R> {

}
