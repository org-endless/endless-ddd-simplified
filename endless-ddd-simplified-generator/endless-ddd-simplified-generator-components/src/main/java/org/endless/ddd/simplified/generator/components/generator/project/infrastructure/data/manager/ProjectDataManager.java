package org.endless.ddd.simplified.generator.components.generator.project.infrastructure.data.manager;

import org.endless.ddd.simplified.generator.common.model.infrastructure.data.manager.DDDSimplifiedGeneratorAggregateDataManager;
import org.endless.ddd.simplified.generator.components.generator.project.application.query.anticorruption.ProjectQueryRepository;
import org.endless.ddd.simplified.generator.components.generator.project.domain.anticorruption.ProjectRepository;
import org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate;
import org.endless.ddd.simplified.generator.components.generator.project.infrastructure.data.persistence.mapper.ProjectMapper;
import org.endless.ddd.simplified.generator.components.generator.project.infrastructure.data.record.ProjectRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * ProjectDataManager
 * <p>项目聚合数据管理器
 * <p>
 * create 2025/08/02 20:02
 * <p>
 * update 2025/08/02 20:02
 *
 * @author Deng Haozhi
 * @see ProjectRepository
 * @see ProjectQueryRepository
 * @see DDDSimplifiedGeneratorAggregateDataManager
 * @since 0.0.1
 */
@Lazy
@Component
public class ProjectDataManager implements ProjectRepository, ProjectQueryRepository, DDDSimplifiedGeneratorAggregateDataManager<ProjectRecord, ProjectAggregate> {

    /**
     * 项目聚合 Mybatis-Plus 数据访问对象
     */
    private final ProjectMapper projectMapper;

    public ProjectDataManager(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectAggregate save(ProjectAggregate aggregate) {
        return null;
    }

    @Override
    public void remove(ProjectAggregate aggregate) {

    }

    @Override
    public ProjectAggregate modify(ProjectAggregate aggregate) {
        return null;
    }

    @Override
    public Optional<ProjectAggregate> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProjectAggregate> findByIdForUpdate(String id) {
        return Optional.empty();
    }
}
