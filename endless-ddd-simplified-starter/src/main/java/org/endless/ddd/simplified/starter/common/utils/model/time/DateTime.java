package org.endless.ddd.simplified.starter.common.utils.model.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * DateTime
 * <p>
 * create 2024/10/31 14:57
 * <p>
 * update 2024/10/31 14:57
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class DateTime {

    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String now() {
        return TimeStamp.format(Instant.now(), DEFAULT_DATE_TIME_FORMAT);
    }

    public static String from(Long timestamp) {
        return from(timestamp, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String from(Long timestamp, String pattern) {
        return TimeStamp.format(Instant.ofEpochMilli(timestamp), pattern);
    }

    public static Long formatToTimestamp(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            Date date = formatter.parse(dateTime);
            return date.getTime();
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
    }
}
