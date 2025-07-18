package org.endless.ddd.simplified.starter.common.utils.model.tree;

import cn.hutool.core.collection.CollUtil;
import org.endless.ddd.simplified.starter.common.exception.utils.model.TreeException;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tree
 * <p>
 * create 2024/11/21 10:59
 * <p>
 * update 2024/11/21 10:59
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class Tree {

    public static <T extends Transfer> List<TreeNode> from(List<T> nodes, String idFieldName, String parentIdFieldName) {
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }
        if (!StringUtils.hasText(idFieldName) || !StringUtils.hasText(parentIdFieldName)) {
            throw new TreeException("树结构对象ID或父ID字段名称不能为空");
        }
        Map<Class<?>, Field[]> classFieldCache = new HashMap<>();
        Map<Object, TreeNode> nodeMap = new HashMap<>();
        List<TreeNode> roots = new ArrayList<>();
        try {
            for (T node : nodes) {
                Field[] fields = classFieldCache.computeIfAbsent(node.getClass(), Class::getDeclaredFields);
                Object id = null;
                Object parentId = null;
                Map<String, Object> treeNodeFields = new HashMap<>();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = field.get(node);
                    if (fieldName.equals(idFieldName)) {
                        id = fieldValue;
                    } else if (fieldName.equals(parentIdFieldName)) {
                        parentId = fieldValue;
                    }
                    treeNodeFields.put(fieldName, fieldValue);
                }
                if (id != null) {
                    if (nodeMap.containsKey(id)) {
                        throw new TreeException("树结构对象ID重复: " + id);
                    }
                    TreeNode treeNode = TreeNode.builder()
                            .parentId(parentId)
                            .fields(treeNodeFields).build();
                    nodeMap.put(id, treeNode);
                }
            }
            for (TreeNode node : nodeMap.values()) {
                Object parentId = node.parentId();
                if (parentId == null || !nodeMap.containsKey(parentId)) {
                    roots.add(node);
                } else {
                    TreeNode parentNode = nodeMap.get(parentId);
                    parentNode.addChild(node);
                }
            }
            return roots;
        } catch (IllegalAccessException e) {
            throw new TreeException("树结构对象属性访问异常", e);
        } catch (Exception e) {
            throw new TreeException("树结构对象转换异常", e);
        }
    }

    public static <T extends Transfer> List<TreeNode> from(List<String> parentIds, List<T> nodes, String idFieldName, String parentIdFieldName) {
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }
        if (!StringUtils.hasText(idFieldName) || !StringUtils.hasText(parentIdFieldName)) {
            throw new TreeException("树结构对象ID或父ID字段名称不能为空");
        }
        Map<Class<?>, Field[]> classFieldCache = new HashMap<>();
        Map<Object, TreeNode> nodeMap = new HashMap<>();
        List<TreeNode> roots = new ArrayList<>();
        try {
            for (T node : nodes) {
                Field[] fields = classFieldCache.computeIfAbsent(node.getClass(), Class::getDeclaredFields);
                Object id = null;
                Object parentId = null;
                Map<String, Object> treeNodeFields = new HashMap<>();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = field.get(node);
                    if (fieldName.equals(idFieldName)) {
                        id = fieldValue;
                    } else if (fieldName.equals(parentIdFieldName)) {
                        parentId = fieldValue;
                    }
                    treeNodeFields.put(fieldName, fieldValue);
                }
                if (id != null) {
                    if (nodeMap.containsKey(id)) {
                        throw new TreeException("树结构对象ID重复: " + id);
                    }
                    TreeNode treeNode = TreeNode.builder()
                            .parentId(parentId)
                            .fields(treeNodeFields).build();
                    nodeMap.put(id, treeNode);
                }
            }
            Set<String> parentIdSet = parentIds.stream().collect(Collectors.toSet());
            for (TreeNode node : nodeMap.values()) {
                Object parentId = node.parentId();
                Object id = node.fields().get(idFieldName);
                if (parentId == null || !nodeMap.containsKey(parentId)) {
                    if (CollUtil.isEmpty(parentIdSet) || parentIdSet.contains(id)) {
                        roots.add(node);
                    }
                } else {
                    TreeNode parentNode = nodeMap.get(parentId);
                    parentNode.addChild(node);
                }
            }
            return roots;
        } catch (IllegalAccessException e) {
            throw new TreeException("树结构对象属性访问异常", e);
        } catch (Exception e) {
            throw new TreeException("树结构对象转换异常", e);
        }
    }
}
