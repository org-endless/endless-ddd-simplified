package org.endless.ddd.simplified.generator.object.entity;


import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Entity
 * <p>
 * create 2024/09/16 17:43
 * <p>
 * update 2024/09/16 17:43
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
@JSONType(orders = {"name", "description", "fields"})
public class Entity {

    private final String name;

    private final String description;

    private final List<Field> fields;

    public Entity validate() {
        validateName();
        validateDescription();
        validateFields();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("实体名称不能为空，当前值为: " + name);
        }
        if (!name.endsWith("Entity")) {
            throw new IllegalArgumentException("实体名称必须以Entity结尾，请修改:  " + name);
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("实体描述不能为空，当前值为: " + description);
        }
    }

    private void validateFields() {
        if (fields != null && !fields.isEmpty()) {
            fields.stream()
                    .peek(Field::validate)  // 执行每个字段的 validate 方法
                    .filter(field -> field.type().startsWith("List") || field.type().endsWith("Entity"))
                    .findAny()
                    .ifPresent(field -> {
                        throw new IllegalArgumentException("实体字段 " + field.name() + " 不支持类型: " + field.type());
                    });
        } else {
            throw new IllegalArgumentException("实体字段不能为空");
        }
    }
}
