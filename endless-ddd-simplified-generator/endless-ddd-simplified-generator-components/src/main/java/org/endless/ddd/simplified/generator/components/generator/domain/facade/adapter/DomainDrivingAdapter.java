package org.endless.ddd.simplified.generator.components.generator.domain.facade.adapter;

import org.endless.ddd.simplified.generator.common.model.facade.adapter.DDDSimplifiedGeneratorDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.domain.application.command.transfer.DomainCreateReqCTransfer;

/**
 * DomainDrivingAdapter
 * <p>
 * 聚合领域主动适配器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorDrivingAdapter
 * @since 0.0.1
 */
public interface DomainDrivingAdapter extends DDDSimplifiedGeneratorDrivingAdapter {

    void create(DomainCreateReqCTransfer command);
}
