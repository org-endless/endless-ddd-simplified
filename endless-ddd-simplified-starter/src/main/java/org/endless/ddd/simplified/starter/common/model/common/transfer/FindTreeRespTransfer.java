package org.endless.ddd.simplified.starter.common.model.common.transfer;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.model.application.query.transfer.QueryTransfer;
import org.endless.ddd.simplified.starter.common.utils.model.tree.TreeNode;

import java.util.List;

/**
 * FindTreeRespTransfer
 * <p>
 * create 2024/12/27 15:50
 * <p>
 * update 2024/12/27 15:52
 *
 * @author Deng Haozhi
 * @see QueryTransfer
 * @since 1.0.0
 */
@Getter
@ToString
@Builder
@JSONType(orders = {"treeNodes"})
public class FindTreeRespTransfer implements TreeRespTransfer {

    /**
     * 树节点列表
     */
    private final List<TreeNode> treeNodes;

    @Override
    public FindTreeRespTransfer validate() {
        return this;
    }
}
