package org.endless.ddd.simplified.starter.common.model.application.query.transfer;

import org.endless.ddd.simplified.starter.common.model.common.Transfer;

import java.io.Serializable;
import java.util.List;

/**
 * PageTransfer
 * <p>
 * create 2024/09/10 10:36
 * <p>
 * update 2024/09/12 12:36
 *
 * @author Deng Haozhi
 * @see Serializable
 * @since 1.0.0
 */
public interface PageRespQTransfer extends QueryTransfer {

    <T extends Transfer> List<T> getRows();

    Long getTotal();

    Integer getPageSize();
    Integer getPageNum();
}
