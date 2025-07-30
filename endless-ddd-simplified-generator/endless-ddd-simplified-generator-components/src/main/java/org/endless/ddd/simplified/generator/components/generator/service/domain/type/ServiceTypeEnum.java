package org.endless.ddd.simplified.generator.components.generator.service.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.type.DDDSimplifiedGeneratorEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * ServiceTypeEnum
 * <p>
 * 服务类型枚举
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
public enum ServiceTypeEnum implements DDDSimplifiedGeneratorEnum {

    REST("REST", "REST服务"),
    SERVICE_MESH("SERVICE_MESH", "服务网格");

    private final String code;

    private final String description;

    public static ServiceTypeEnum fromCode(String code) {
        for (ServiceTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的服务类型枚举: " + code);
    }
}
