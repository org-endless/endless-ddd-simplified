package org.endless.ddd.simplified.generator.components.generator.project.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.type.DDDSimplifiedGeneratorEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * ProjectPersistenceFrameworkEnum
 * <p>项目持久化框架枚举
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
public enum ProjectPersistenceFrameworkEnum implements DDDSimplifiedGeneratorEnum {

    // JPA("JPA", "JPA框架"),
    MYBATIS_PLUS("MYBATIS_PLUS", "Mybatis-Plus框架");

    private final String code;

    private final String description;

    public static ProjectPersistenceFrameworkEnum fromCode(String code) {
        for (ProjectPersistenceFrameworkEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的项目持久化框架枚举: " + code);
    }
}
