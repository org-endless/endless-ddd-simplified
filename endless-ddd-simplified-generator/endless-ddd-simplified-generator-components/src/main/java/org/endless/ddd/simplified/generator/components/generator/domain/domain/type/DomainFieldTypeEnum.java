package org.endless.ddd.simplified.generator.components.generator.domain.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.type.DDDSimplifiedGeneratorEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * DomainFieldTypeEnum
 * <p>
 * 领域字段类型枚举
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorEnum
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor
@ToString
public enum DomainFieldTypeEnum implements DDDSimplifiedGeneratorEnum {

    STRING("STRING", "String"),
    INTEGER("INTEGER", "Integer"),
    LONG("LONG", "Long"),
    DOUBLE("DOUBLE", "Double"),
    ENTITY("ENTITY", "Entity"),
    VALUE("VALUE", "Value"),
    ENUM("ENUM", "Enum"),
    LIST_STRING("LIST_STRING", "List<String>"),
    LIST_ENTITY("LIST_OBJECT", "List<Object>"),
    LIST_VALUE("LIST_OBJECT", "List<Object>"),
    LIST_ENUM("LIST_OBJECT", "List<Object>"),
    BIG_DECIMAL("BIG_DECIMAL", "BigDecimal");

    private final String code;

    private final String description;


    public static DomainFieldTypeEnum fromCode(String code) {
        for (DomainFieldTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的领域字段类型枚举: " + code);
    }
}
