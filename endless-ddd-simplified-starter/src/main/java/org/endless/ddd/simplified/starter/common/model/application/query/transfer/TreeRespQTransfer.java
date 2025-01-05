package org.endless.ddd.simplified.starter.common.model.application.query.transfer;


import org.endless.ddd.simplified.starter.common.utils.model.tree.TreeNode;

import java.util.List;

/**
 * TreeRespQTransfer
 * <p>
 * create 2025/01/05 15:15
 * <p>
 * update 2025/01/05 15:19
 *
 * @author Deng Haozhi
 * @see QueryTransfer
 * @since 1.0.0
 */
public interface TreeRespQTransfer extends QueryTransfer {

    List<TreeNode> getTreeNodes();
}
