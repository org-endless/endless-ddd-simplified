package org.endless.ddd.simplified.starter.common.model.application.query.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;
import org.endless.ddd.simplified.starter.common.utils.model.tree.TreeNode;

import java.util.List;

/**
 * FindTreeRespQTransfer
 * <p>
 * create 2024/12/27 15:50
 * <p>
 * update 2024/12/27 15:52
 *
 * @author Deng Haozhi
 * @see QueryTransfer
 * @since 2.0.0
 */
@Getter
@ToString
@Builder
@JSONType(orders = {"treeNodes"})
public class FindTreeRespQTransfer implements QueryTransfer {

    /**
     * 树节点列表
     */
    private final List<TreeNode> treeNodes;

    @Override
    public Transfer validate() {
        return this;
    }
}
