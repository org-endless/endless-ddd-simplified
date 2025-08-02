package org.endless.ddd.simplified.generator.components.generator.project.infrastructure.data.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.endless.ddd.simplified.generator.common.model.infrastructure.data.mapper.DDDSimplifiedGeneratorMapper;
import org.endless.ddd.simplified.generator.components.generator.project.infrastructure.data.record.ProjectRecord;
import org.springframework.context.annotation.Lazy;

/**
 * ProjectMapper
 * <p>项目聚合 Mybatis-Plus 数据访问对象
 * <p>
 * create 2025/08/02 20:02
 * <p>
 * update 2025/08/02 20:02
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorMapper
 * @since 0.0.1
 */
@Lazy
@Mapper
public interface ProjectMapper extends DDDSimplifiedGeneratorMapper<ProjectRecord> {

}
