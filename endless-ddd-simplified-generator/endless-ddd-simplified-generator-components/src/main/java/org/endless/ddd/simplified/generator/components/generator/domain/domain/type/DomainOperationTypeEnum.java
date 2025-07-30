package org.endless.ddd.simplified.generator.components.generator.domain.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.type.DDDSimplifiedGeneratorEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * DomainOperationTypeEnum
 * <p>
 * 领域操作类型枚举
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
public enum DomainOperationTypeEnum implements DDDSimplifiedGeneratorEnum {

    COMMAND("COMMAND", "命令"),
    QUERY("QUERY", "查询");

    private final String code;

    private final String description;


    public static DomainOperationTypeEnum fromCode(String code) {
        for (DomainOperationTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的领域操作类型枚举: " + code);
    }
}
