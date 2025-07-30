package org.endless.ddd.simplified.starter.common.utils.model.time;

import org.endless.ddd.simplified.starter.common.exception.utils.model.TimeStampException;
import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * TimeStamp
 * <p>
 * create 2024/10/30 11:24
 * <p>
 * update 2024/10/30 11:24
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class TimeStamp {

    public static Long ONE_SECOND = 1000L;

    public static Long ONE_MINUTE = 60 * ONE_SECOND;

    public static Long ONE_HOUR = 60 * ONE_MINUTE;

    public static Long ONE_DAY = 24 * ONE_HOUR;

    public static Long ONE_WEEK = 7 * ONE_DAY;

    public static Long ONE_MONTH = 30 * ONE_DAY;

    public static Long now() {
        return Instant.now().toEpochMilli();
    }

    public static Long between(Long start, Long end) {
        return Duration.between(Instant.ofEpochMilli(start), Instant.ofEpochMilli(end)).toMillis();
    }

    public static Long from(String timestamp, String pattern) {
        if (!StringUtils.hasText(timestamp) || !StringUtils.hasText(pattern)) {
            throw new TimeStampException("格式化时间戳失败，时间或格式化字符串为空");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            if (pattern.contains("H")) {
                // 包含小时，解析为 LocalDateTime（未指定部分补零）
                LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);
                if (!pattern.contains("m")) {
                    dateTime = dateTime.withMinute(0);
                }
                if (!pattern.contains("s")) {
                    dateTime = dateTime.withSecond(0);
                }
                return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            } else if (pattern.contains("y") || pattern.contains("M") || pattern.contains("d")) {
                // 只有日期部分，解析为 LocalDate
                LocalDate date = LocalDate.parse(timestamp, formatter);
                return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            } else {
                throw new TimeStampException("格式化时间戳失败，不支持的时间格式: " + pattern);
            }
        } catch (Exception e) {
            throw new TimeStampException("格式化时间戳失败，时间或格式化字符串格式错误，格式: " + pattern + " 错误信息: " + e.getMessage(), e);
        }
    }

    public static Long fromISO(String timestamp) {
        if (!StringUtils.hasText(timestamp)) {
            throw new TimeStampException("格式化时间戳失败，时间为空");
        }
        try {
            return Instant.parse(timestamp).toEpochMilli();
        } catch (Exception e) {
            throw new TimeStampException("格式化时间戳失败，时间格式错误: " + timestamp, e);
        }
    }

    protected static String format(Instant timestamp, String pattern) {
        ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return zonedDateTime.format(formatter);
    }

    public static Long months(Long timestamp) {
        return timestamp / ONE_MONTH;
    }

    public static Long weeks(Long timestamp) {
        return timestamp / ONE_WEEK;
    }

    public static Long days(Long timestamp) {
        return timestamp / ONE_DAY;
    }

    public static Long hours(Long timestamp) {
        return timestamp / ONE_HOUR;
    }

    public static Long minutes(Long timestamp) {
        return timestamp / ONE_MINUTE;
    }

    public static Long seconds(Long timestamp) {
        return timestamp / ONE_SECOND;
    }
}
