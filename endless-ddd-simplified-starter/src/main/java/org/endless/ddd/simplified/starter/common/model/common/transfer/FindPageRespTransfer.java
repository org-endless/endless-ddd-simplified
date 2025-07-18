package org.endless.ddd.simplified.starter.common.model.common.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import com.alibaba.fastjson2.util.TypeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.common.TransferValidateException;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
@JSONType(orders = {"rows", "total", "pageSize", "pageNum"})
public class FindPageRespTransfer implements PageRespTransfer {

    /**
     * 查询结果
     */
    private final List<?> rows;

    /**
     * 查询总条数
     */
    private final Long total;

    /**
     * 页码
     */
    private final Integer pageNum;

    /**
     * 分页大小
     */
    private final Integer pageSize;


    @Override
    public FindPageRespTransfer validate() {
        validateTotal();
        validatePageSize();
        validatePageNum();
        return this;
    }

    public <T extends Transfer> List<T> getRows(Class<T> clazz) {
        return Optional.ofNullable(rows)
                .filter(l -> !CollectionUtils.isEmpty(l))
                .orElse(Collections.emptyList())
                .stream()
                .map(t -> TypeUtils.cast(t, clazz))
                .collect(Collectors.toList());
    }

    private void validateTotal() {
        if (total == null || total < 0) {
            throw new TransferValidateException("查询总条数不正确");
        }
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
