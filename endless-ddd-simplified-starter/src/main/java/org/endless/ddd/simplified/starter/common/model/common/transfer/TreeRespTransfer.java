package org.endless.ddd.simplified.starter.common.model.common.transfer;


import org.endless.ddd.simplified.starter.common.model.application.query.transfer.QueryTransfer;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;
import org.endless.ddd.simplified.starter.common.utils.model.tree.TreeNode;

import java.util.List;

/**
 * TreeRespTransfer
 * <p>
 * create 2025/01/05 15:15
 * <p>
 * update 2025/01/05 15:19
 *
 * @author Deng Haozhi
 * @see QueryTransfer
 * @since 1.0.0
 */
public interface TreeRespTransfer extends Transfer {

    List<TreeNode> getTreeNodes();
}
