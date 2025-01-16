package org.endless.ddd.simplified.starter.common.model.application.query.transfer;

/**
 * PageReqQTransfer
 * <p>
 * create 2025/01/16 12:38
 * <p>
 * update 2025/01/16 12:38
 *
 * @author Deng Haozhi
 * @see QueryTransfer
 * @since 1.0.0
 */
public interface PageReqQTransfer<T extends QueryTransfer> extends QueryTransfer {

    T getReqTransfer();

    Integer getPageSize();

    Integer getPageNum();
}
