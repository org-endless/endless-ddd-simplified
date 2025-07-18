package org.endless.ddd.simplified.starter.common.model.common.transfer;

import org.endless.ddd.simplified.starter.common.model.common.Transfer;

import java.util.List;

/**
 * PageRespTransfer
 * <p>
 * create 2024/09/10 10:36
 * <p>
 * update 2025/07/19 03:04
 *
 * @author Deng Haozhi
 * @see Transfer
 * @since 1.0.0
 */
public interface PageRespTransfer extends Transfer {

    List<?> getRows();

    Long getTotal();

    Integer getPageSize();

    Integer getPageNum();
}
