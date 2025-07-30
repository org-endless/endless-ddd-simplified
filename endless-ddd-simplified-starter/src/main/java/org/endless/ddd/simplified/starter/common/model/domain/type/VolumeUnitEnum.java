package org.endless.ddd.simplified.starter.common.model.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;

/**
 * VolumeUnitEnum
 * <p>
 * create 2025/07/22 23:02
 * <p>
 * update 2025/07/22 23:02
 *
 * @author Deng Haozhi
 * @see Enum
 * @see BaseEnum
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@ToString
public enum VolumeUnitEnum implements BaseEnum {

    MILLILITER("ml", "毫升"),
    CENTILITER("cl", "厘升"),
    DECILITER("dl", "分升"),
    LITER("l", "升"),
    DECA_LITER("dal", "十升"),
    HECTO_LITER("hl", "百升"),

    CUBIC_METER("m3", "立方米"),
    CUBIC_CENTIMETER("cm3", "立方厘米"),
    CUBIC_MILLIMETER("mm3", "立方毫米"),
    CUBIC_INCH("in3", "立方英寸"),
    CUBIC_FOOT("ft3", "立方英尺"),
    CUBIC_YARD("yd3", "立方码"),

    GALLON_US("gal_us", "美制加仑"),
    GALLON_UK("gal_uk", "英制加仑"),
    PINT_US("pt_us", "美制品脱"),
    PINT_UK("pt_uk", "英制品脱"),
    FLUID_OUNCE_US("floz_us", "美制液体盎司"),
    FLUID_OUNCE_UK("floz_uk", "英制液体盎司");

    private final String code;

    private final String description;

    public static VolumeUnitEnum fromCode(String code) {
        for (VolumeUnitEnum unit : values()) {
            if (unit.code.equals(code)) {
                return unit;
            }
        }
        throw new EnumException("未知的体积单位枚举: " + code);
    }
}
