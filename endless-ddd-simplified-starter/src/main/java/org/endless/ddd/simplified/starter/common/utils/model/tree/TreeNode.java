package org.endless.ddd.simplified.starter.common.utils.model.tree;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TreeNode
 * <p>
 * create 2024/12/18 12:45
 * <p>
 * update 2024/12/18 12:45
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public record TreeNode(Object parentId, List<TreeNode> children, Map<String, Object> fields) {

    @Builder
    public TreeNode(Object parentId, List<TreeNode> children, Map<String, Object> fields) {
        this.parentId = parentId;
        this.children = children == null ? new ArrayList<>() : children;
        this.fields = fields == null ? new HashMap<>() : fields;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}
