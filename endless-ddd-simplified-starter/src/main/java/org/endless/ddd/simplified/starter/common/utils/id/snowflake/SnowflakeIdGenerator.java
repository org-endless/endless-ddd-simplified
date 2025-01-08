package org.endless.ddd.simplified.starter.common.utils.id.snowflake;

import org.endless.ddd.simplified.starter.common.exception.utils.id.SnowflakeIdException;
import org.endless.ddd.simplified.starter.common.utils.model.time.TimeStamp;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SnowflakeIdGenerator
 * <p>主机名要求：DataCenterXXXX-ServerXXXX，其中DataCenterXXXX为数据中心编号，DomainSimplifiedServerXXXX为服务器编号，编号要求为5位数字。
 * <p>数据中心和服务器名称可修改，但必须保证唯一性。
 * <p>
 * create 2024/10/30 10:23
 * <p>
 * update 2024/10/30 10:23
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class SnowflakeIdGenerator {

    // 容忍时间窗口，单位：毫秒
    private static final Long TIME_WINDOW = 5L;

    private static final Long DATA_CENTER_ID_BITS = 4L;

    private static final Long WORKER_ID_BITS = 6L;

    private static final Long SEQUENCE_BITS = 12L;

    private static final Long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    private static final Long WORKER_ID_SHIFT = SEQUENCE_BITS;

    private static final Long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private static final Long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    private static final Long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    private static final Long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    private final Long dataCenterId;

    private final Long workerId;

    private Long sequence = 0L;

    private Long lastTimestamp = -1L;

    private final ReentrantLock lock = new ReentrantLock(); // 使用 ReentrantLock

    public SnowflakeIdGenerator(Long dataCenterId, Long workerId) {
        validateIds(dataCenterId, workerId);
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }


    private void validateIds(Long dataCenterId, Long workerId) {
        if (dataCenterId == null || workerId == null) {
            throw new SnowflakeIdException("数据中心ID和服务器ID不能为空");
        }
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER_ID) {
            throw new SnowflakeIdException("数据中心ID无效或超出 0-15 范围: " + dataCenterId);
        }
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new SnowflakeIdException("服务器ID无效或超出 0-63 范围: " + workerId);
        }
    }

    // 提取数字部分

    public Long nextId() {
        lock.lock(); // 加锁
        try {
            Long timestamp = TimeStamp.now();

            if (timestamp < lastTimestamp) {
                if (lastTimestamp - timestamp < TIME_WINDOW) {
                    timestamp = lastTimestamp;
                } else {
                    throw new SnowflakeIdException("服务器时间回拨，请检查系统时间是否正确，上次请求UTC时间戳: " + lastTimestamp);
                }
            }

            if (lastTimestamp.equals(timestamp)) {
                sequence = (sequence + 1) & MAX_SEQUENCE;
                if (sequence == 0) {
                    // 如果序列号超限，等待1毫秒
                    timestamp = waitNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = timestamp;
            return ((timestamp << TIMESTAMP_SHIFT)) |
                    (dataCenterId << DATA_CENTER_ID_SHIFT) |
                    (workerId << WORKER_ID_SHIFT) |
                    sequence;
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    private Long waitNextMillis(Long lastTimestamp) {
        Optional.ofNullable(lastTimestamp)
                .orElseThrow(() -> new SnowflakeIdException("lastTimestamp 不能为空"));
        Long timestamp = TimeStamp.now();
        while (timestamp.compareTo(lastTimestamp) <= 0) {
            timestamp = TimeStamp.now();
        }
        return timestamp;
    }
}
