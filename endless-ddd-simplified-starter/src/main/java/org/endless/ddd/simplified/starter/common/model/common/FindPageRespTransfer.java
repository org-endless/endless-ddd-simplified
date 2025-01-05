package org.endless.ddd.simplified.starter.common.model.common;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.common.RespTransferValidateException;

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
 * @since 2.0.0
 */
@Getter
@ToString
@Builder
@JSONType(orders = {"transfers", "total"})
public class FindPageRespTransfer<T extends Transfer> implements Transfer {

    private final List<T> transfers;

    private final Long total;

    @Override
    public Transfer validate() {
        validateTotal();
        return this;
    }

    private void validateTotal() {
        if (total == null || total < 0) {
            throw new RespTransferValidateException("查询总条数不正确");
        }
    }
}
