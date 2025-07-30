package org.endless.ddd.simplified.starter.common.model.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * MassUnitEnum
 * <p>质量单位
 * <p>
 * create 2025/07/22 10:05
 * <p>
 * update 2025/07/22 22:33
 *
 * @author Deng Haozhi
 * @see Enum
 * @see BaseEnum
 * @since 0.0.1
 */
@Getter
@AllArgsConstructor
@ToString
public enum MassUnitEnum implements BaseEnum {

    GRAM("g", "克"),
    KILOGRAM("kg", "千克"),
    MILLIGRAM("mg", "毫克"),
    MICROGRAM("µg", "微克"),
    TONNE("t", "吨"),
    KILO_TONNE("kt", "千吨"),
    MEGA_TONNE("Mt", "兆吨"),
    GIGA_TONNE("Gt", "吉吨"),
    TERA_TONNE("Tt", "太吨"),

    CARAT("ct", "克拉"),
    OUNCE("oz", "盎司"),
    POUND("lb", "磅"),
    STONE("st", "英石"),
    SHORT_TONNE("ton_us", "美吨"),
    LONG_TONNE("ton_uk", "英吨"),

    ATOMIC_MASS_UNIT("u", "原子质量单位（道尔顿）");

    private final String code;

    private final String description;

    public static MassUnitEnum fromCode(String code) {
        for (MassUnitEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的质量单位枚举: " + code);
    }
}
