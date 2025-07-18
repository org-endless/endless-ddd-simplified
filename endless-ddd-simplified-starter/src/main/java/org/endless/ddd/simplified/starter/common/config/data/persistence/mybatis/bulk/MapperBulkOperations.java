package org.endless.ddd.simplified.starter.common.config.data.persistence.mybatis.bulk;

import org.endless.ddd.simplified.starter.common.model.domain.entity.Entity;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.persistence.mapper.DataMapper;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.record.DataRecord;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * MapperBulkOperations
 * <p>
 * create 2024/11/10 14:45
 * <p>
 * update 2024/11/10 14:45
 * update 2025/07/15 14:01
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public interface MapperBulkOperations<R extends DataRecord<? extends Entity>> {

    void execute(List<R> records, Class<? extends DataMapper<R>> mapperClass, BiConsumer<DataMapper<R>, R> consumer);
}
