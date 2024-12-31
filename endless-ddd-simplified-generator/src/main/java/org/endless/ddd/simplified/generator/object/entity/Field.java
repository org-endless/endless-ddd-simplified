package org.endless.ddd.simplified.generator.object.entity;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * Filed
 * <p>
 * create 2024/09/16 17:42
 * <p>
 * update 2024/09/16 17:42
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
@JSONType(orders = {"name", "type", "description", "nullable"})
public class Field {

    private final String name;

    private final String type;

    private final String description;

    private final Boolean nullable;

    public Field(String name, String type, String description, Boolean nullable) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.nullable = nullable != null && nullable;
    }

    public Field validate() {
        validateName();
        validateType();
        validateDescription();
        validateNullable();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("字段名称不能为空，当前值为: " + name);
        }
        if (name.endsWith("Value") || name.endsWith("Entity")) {
            throw new IllegalArgumentException("字段名称不能以Value或Entity结尾，请修改:  " + name);
        }
    }

    private void validateType() {
        if (!StringUtils.hasText(type)) {
            throw new IllegalArgumentException("字段类型不能为空: " + name);
        }
        switch (type) {
            case "int":
                throw new IllegalArgumentException("字段类型不能为int，请使用Integer替代: " + name);
            case "long":
                throw new IllegalArgumentException("字段类型不能为long，请使用Long替代: " + name);
            case "double":
                throw new IllegalArgumentException("字段类型不能为double，请使用Double替代: " + name);
        }
        if (type.startsWith("List<")) {
            if (!name.endsWith("s") || !description.endsWith("列表")) {
                throw new IllegalArgumentException(type + " 类型字段 " + name + " 必须以s结尾，且描述必须以列表结尾！");
            }
        } else if (type.startsWith("List<String>") || type.startsWith("List<Long>")) {
            if (!name.endsWith("Ids") || !description.endsWith("ID列表")) {
                throw new IllegalArgumentException(type + " 类型字段 " + name + " 必须以Ids结尾，且描述必须以ID列表结尾！");
            }
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("字段描述不能为空，当前值为: " + description);
        }
    }

    private void validateNullable() {
        if (this.type.equals("Value") && this.nullable) {
            throw new IllegalArgumentException("Value类型字段不能为可空！");
        }
    }
}
