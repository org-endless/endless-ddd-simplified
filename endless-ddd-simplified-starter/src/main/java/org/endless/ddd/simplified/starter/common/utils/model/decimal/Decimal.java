package org.endless.ddd.simplified.starter.common.utils.model.decimal;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.exception.utils.model.DecimalCalculationException;
import org.endless.ddd.simplified.starter.common.exception.utils.model.DecimalDivisionByZeroException;
import org.endless.ddd.simplified.starter.common.exception.utils.model.DecimalEmptyException;
import org.endless.ddd.simplified.starter.common.exception.utils.model.DecimalFormatException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Decimal
 * <p>
 * create 2025/01/11 00:02
 * <p>
 * update 2025/01/11 00:02
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class Decimal {

    private static final int DEFAULT_SCALE = 2;

    private static final int FIVE_SCALE = 5;

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    public static Boolean isAmount(String amount) {
        String regex = "^\\d{1,15}(\\.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(regex);
        return isDecimal(amount, pattern, "金额不能为空", "金额格式错误，最多15位整数，2位小数");
    }

    public static Boolean isRate(String rate) {
        String regex = "^\\d{1,3}(\\.\\d{1,5})?$";
        Pattern pattern = Pattern.compile(regex);
        return isDecimal(rate, pattern, "比率不能为空", "比率格式错误，最多3位整数，5位小数");
    }

    public static Boolean isPercentage(String percentage) {
        String regex = "^\\d{1,3}(\\.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(regex);
        return isDecimal(percentage, pattern, "百分比不能为空", "百分比格式错误，最多3位整数，2位小数");
    }

    public static Boolean isDecimal(String decimal, Pattern pattern, String emptyMessage, String formatMessage) {
        Optional.ofNullable(decimal)
                .filter(StringUtils::hasText)
                .orElseThrow(() -> new DecimalEmptyException(emptyMessage));
        if (pattern.matcher(decimal).matches()) {
            return true;
        } else {
            throw new DecimalFormatException(formatMessage);
        }
    }

    public static BigDecimal format(BigDecimal decimal) {
        return format(decimal, DEFAULT_SCALE);
    }

    public static BigDecimal format(String decimal) {
        try {
            return Optional.ofNullable(decimal)
                    .filter(StringUtils::hasText)
                    .map(BigDecimal::new)
                    .map(Decimal::format)
                    .orElseThrow(DecimalEmptyException::new);
        } catch (DecimalEmptyException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalFormatException(e);
        }
    }

    public static BigDecimal format(Object decimal) {
        return Optional.ofNullable(decimal)
                .map(String::valueOf)
                .map(Decimal::format)
                .orElseThrow(DecimalEmptyException::new);
    }

    public static BigDecimal format5Bit(BigDecimal decimal) {
        return format(decimal, FIVE_SCALE);
    }

    public static BigDecimal format5Bit(String decimal) {
        return Optional.ofNullable(decimal)
                .filter(StringUtils::hasText)
                .map(BigDecimal::new)
                .map(Decimal::format5Bit)
                .orElseThrow(DecimalEmptyException::new);
    }

    public static BigDecimal format5Bit(Object decimal) {
        return Optional.ofNullable(decimal)
                .map(String::valueOf)
                .map(Decimal::format5Bit)
                .orElseThrow(DecimalEmptyException::new);
    }

    private static BigDecimal format(BigDecimal decimal, int scale) {
        return Optional.ofNullable(decimal)
                .map(d -> d.setScale(scale, ROUNDING_MODE))
                .orElseThrow(DecimalEmptyException::new);
    }

    public static BigDecimal add(BigDecimal decimal, BigDecimal augend) {
        if (Stream.of(decimal, augend)
                .map(Optional::ofNullable).anyMatch(Optional::isEmpty)) {
            throw new DecimalEmptyException();
        }
        try {
            return format5Bit(decimal.add(augend));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal subtract(BigDecimal decimal, BigDecimal subtrahend) {
        if (Stream.of(decimal, subtrahend)
                .map(Optional::ofNullable).anyMatch(Optional::isEmpty)) {
            throw new DecimalEmptyException();
        }
        try {
            return format5Bit(decimal.subtract(subtrahend));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal multiply(BigDecimal decimal, BigDecimal multiplicand) {
        if (Stream.of(decimal, multiplicand)
                .map(Optional::ofNullable).anyMatch(Optional::isEmpty)) {
            throw new DecimalEmptyException();
        }
        try {
            return format5Bit(decimal.multiply(multiplicand));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal divide(BigDecimal decimal, BigDecimal divisor) {
        if (Stream.of(decimal, divisor)
                .map(Optional::ofNullable).anyMatch(Optional::isEmpty)) {
            throw new DecimalEmptyException();
        }
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new DecimalDivisionByZeroException();
        }
        try {
            return format5Bit(decimal.divide(divisor, FIVE_SCALE, ROUNDING_MODE));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal max(List<BigDecimal> decimals) {
        try {
            return format(decimals.stream()
                    .filter(Objects::nonNull)
                    .max(BigDecimal::compareTo)
                    .orElseThrow(DecimalEmptyException::new));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal min(List<BigDecimal> decimals) {
        try {
            return format(decimals.stream()
                    .filter(Objects::nonNull)
                    .min(BigDecimal::compareTo)
                    .orElseThrow(DecimalEmptyException::new));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal average(List<BigDecimal> decimals) {
        List<BigDecimal> validDecimals = Optional.ofNullable(decimals)
                .filter(l -> !CollectionUtils.isEmpty(l))
                .orElseThrow(DecimalEmptyException::new)
                .stream()
                .filter(Objects::nonNull).toList();
        if (validDecimals.isEmpty()) {
            throw new DecimalEmptyException();
        }
        try {
            BigDecimal sum = validDecimals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            return format(divide(sum, BigDecimal.valueOf(validDecimals.size())));
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }

    public static BigDecimal median(List<BigDecimal> decimals) {
        List<BigDecimal> validDecimals = Optional.ofNullable(decimals)
                .filter(l -> !CollectionUtils.isEmpty(l))
                .orElseThrow(DecimalEmptyException::new)
                .stream()
                .filter(Objects::nonNull)
                .sorted().toList();
        if (validDecimals.isEmpty()) {
            throw new DecimalEmptyException();
        }
        try {
            int size = validDecimals.size();
            if (size % 2 == 0) {
                BigDecimal mid1 = validDecimals.get(size / 2 - 1);
                BigDecimal mid2 = validDecimals.get(size / 2);
                return format(divide(add(mid1, mid2), BigDecimal.valueOf(2)));
            } else {
                return format(validDecimals.get(size / 2));
            }
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new DecimalCalculationException(e);
        }
    }
}
