package org.endless.ddd.simplified.starter.common.model.application.query.anticorruption;

import org.endless.ddd.simplified.starter.common.model.domain.entity.Entity;

/**
 * QueryRepository
 * <p>查询仓储适配器模版
 * <p>CQRS查询仓储适配器模版
 * <p>DDD应用层，处理查询逻辑，不受到领域模型和状态的限制
 * <p>
 * create 2024/11/03 18:35
 * <p>
 * update 2024/11/03 18:35
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public interface QueryRepository<E extends Entity> {

}
