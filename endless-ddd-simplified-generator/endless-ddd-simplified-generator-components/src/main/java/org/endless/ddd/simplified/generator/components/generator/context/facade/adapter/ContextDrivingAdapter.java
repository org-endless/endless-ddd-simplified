package org.endless.ddd.simplified.generator.components.generator.context.facade.adapter;

import org.endless.ddd.simplified.generator.common.model.facade.adapter.DDDSimplifiedGeneratorDrivingAdapter;
import org.endless.ddd.simplified.generator.components.generator.context.application.command.transfer.ContextCreateReqCTransfer;

/**
 * ContextDrivingAdapter
 * <p>
 * 上下文领域主动适配器
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorDrivingAdapter
 * @since 0.0.1
 */
public interface ContextDrivingAdapter extends DDDSimplifiedGeneratorDrivingAdapter {

    void create(ContextCreateReqCTransfer command);
}