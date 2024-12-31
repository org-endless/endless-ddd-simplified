package org.endless.ddd.simplified.starter.common.utils.model.time;

import org.springframework.util.StringUtils;

import java.time.Instant;


/**
 * Date
 * <p>
 * create 2024/10/30 11:17
 * <p>
 * update 2024/10/30 11:17
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class Date {

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static String now() {
        return TimeStamp.format(Instant.now(), DEFAULT_DATE_FORMAT);
    }

    public static String from(Long timestamp) {
        return from(timestamp, DEFAULT_DATE_FORMAT);
    }

    public static String from(Long timestamp, String pattern) {
        if (timestamp == null || !StringUtils.hasText(pattern)) {
            throw new IllegalArgumentException("timestamp or pattern is null or empty");
        }
        return TimeStamp.format(Instant.ofEpochMilli(timestamp), pattern);
    }
}
