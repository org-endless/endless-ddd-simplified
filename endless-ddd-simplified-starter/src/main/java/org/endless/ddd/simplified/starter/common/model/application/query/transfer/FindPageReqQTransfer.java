package org.endless.ddd.simplified.starter.common.model.application.query.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.common.TransferValidateException;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;

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
@JSONType(orders = {"reqTransfer", "pageSize", "pageNum"})
public class FindPageReqQTransfer<T extends QueryTransfer> implements PageReqQTransfer<T> {

    /**
     * 查询传输对象
     */
    private final T reqTransfer;

    /**
     * 页码
     */
    private final Integer pageNum;

    /**
     * 分页大小
     */
    private final Integer pageSize;


    @Override
    public FindPageReqQTransfer<T> validate() {
        validatePageSize();
        validatePageNum();
        return this;
    }

    private void validatePageNum() {
        if (pageNum == null || pageNum < 1) {
            throw new TransferValidateException("页码不正确");
        }
    }

    private void validatePageSize() {
        if (pageSize == null || pageSize < 1) {
            throw new TransferValidateException("分页大小不正确");
        }
    }

}
