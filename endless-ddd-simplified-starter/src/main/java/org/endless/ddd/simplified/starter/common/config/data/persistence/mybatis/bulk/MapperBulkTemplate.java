package org.endless.ddd.simplified.starter.common.config.data.persistence.mybatis.bulk;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper.MapperBulkException;
import org.endless.ddd.simplified.starter.common.model.domain.entity.Entity;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.persistence.mapper.DataMapper;
import org.endless.ddd.simplified.starter.common.model.infrastructure.data.record.DataRecord;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * MapperBulkTemplate
 * <p>
 * create 2024/11/10 14:48
 * <p>
 * update 2025/07/01 18:32
 *
 * @author Deng Haozhi
 * @see MapperBulkOperations
 * @since 2.0.0
 */
public class MapperBulkTemplate<R extends DataRecord<? extends Entity>> implements MapperBulkOperations<R> {

    private final SqlSessionTemplate sqlSessionTemplate;

    public MapperBulkTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public void execute(List<R> records, Class<? extends DataMapper<R>> mapperClass, BiConsumer<DataMapper<R>, R> consumer) {
        Optional.ofNullable(records)
                .filter(l -> !CollectionUtils.isEmpty(l))
                .orElseThrow(() -> new MapperBulkException("批量执行的数据记录不能为空"));
        try (SqlSession session = sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH, false)) {
            DataMapper<R> mapper = session.getMapper(mapperClass);
            records.forEach(record -> consumer.accept(mapper, record));
            session.commit();
        } catch (Exception e) {
            throw new MapperBulkException(e);
        }
    }
}
