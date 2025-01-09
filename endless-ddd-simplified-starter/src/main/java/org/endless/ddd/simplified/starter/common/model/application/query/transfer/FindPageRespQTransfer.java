package org.endless.ddd.simplified.starter.common.model.application.query.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.common.TransferValidateException;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;

import java.util.List;

/**
 * FindPageRespTransfer
 * <p>
 * create 2025/01/02 14:08
 * <p>
 * update 2025/01/02 14:15
 *
 * @author Deng Haozhi
 * @see Transfer
 * @since 1.0.0
 */
@Getter
@ToString
@Builder
@JSONType(orders = {"transfers", "total"})
public class FindPageRespQTransfer implements PageRespQTransfer {

    private final List<? extends Transfer> rows;

    private final Long total;

    private final Integer pageSize;

    private final Integer pageNum;

    @Override
    public FindPageRespQTransfer validate() {
        validateTotal();
        validatePageSize();
        validatePageNum();
        return this;
    }

    private void validateTotal() {
        if (total == null || total < 0) {
            throw new TransferValidateException("查询总条数不正确");
        }
    }

    private void validatePageSize() {
        if (pageSize == null || pageSize < 1) {
            throw new TransferValidateException("分页大小不正确");
        }
    }

    private void validatePageNum() {
        if (pageNum == null || pageNum < 1) {
            throw new TransferValidateException("页码不正确");
        }
    }
}
