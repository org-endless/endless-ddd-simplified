package org.endless.ddd.simplified.generator.components.generator.project.application.query.anticorruption;


import org.endless.ddd.simplified.generator.common.model.application.query.anticorruption.DDDSimplifiedGeneratorQueryRepository;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectRepository;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;

/**
 * ProjectQueryRepository
 * <p>项目聚合查询仓储接口
 * <p>
 * create 2025/08/02 19:36
 * <p>
 * update 2025/08/02 19:44
 *
 * @author Deng Haozhi
 * @see ProjectRepository
 * @see ProjectRepository
 * @since 0.0.1
 */
public interface ProjectQueryRepository extends ProjectRepository, DDDSimplifiedGeneratorQueryRepository<ProjectAggregate> {

}
