package org.endless.ddd.simplified.generator.components.generator.project.facade.adapter;

import org.endless.ddd.simplified.generator.common.model.facade.adapter.DDDSimplifiedGeneratorDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateReqCTransfer;
import org.endless.ddd.simplified.generator.components.generator.project.application.command.transfer.ProjectCreateRespCTransfer;

/**
 * ProjectDrivingAdapter
 * <p>项目领域主动适配器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorDrivingAdapter
 * @since 0.0.1
 */
public interface ProjectDrivingAdapter extends DDDSimplifiedGeneratorDrivingAdapter {

    ProjectCreateRespCTransfer create(ProjectCreateReqCTransfer command);
}
