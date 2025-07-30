package org.endless.ddd.simplified.generator.components.generator.project.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.type.DDDSimplifiedGeneratorEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * ProjectJavaVersionEnum
 * <p>
 * create 2025/07/29 21:19
 * <p>
 * update 2025/07/29 21:21
 *
 * @author Deng Haozhi
 * @see Enum
 * @see DDDSimplifiedGeneratorEnum
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@ToString
public enum ProjectJavaVersionEnum implements DDDSimplifiedGeneratorEnum {

    JAVA8("JAVA8", "Java 1.8"),
    JAVA21("JAVA21", "Java 21");

    private final String code;

    private final String description;

    public static ProjectJavaVersionEnum fromCode(String code) {
        for (ProjectJavaVersionEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的Java版本枚举: " + code);
    }
}
