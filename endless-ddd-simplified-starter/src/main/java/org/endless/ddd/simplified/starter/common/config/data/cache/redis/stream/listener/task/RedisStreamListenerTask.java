package org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener.task;

import org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener.RedisStreamListener;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.scheduling.annotation.Async;

/**
 * RedisStreamListenerTask
 * <p>
 * create 2025/07/10 05:37
 * <p>
 * update 2025/07/10 05:37
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public interface RedisStreamListenerTask {

    @Async("virtualThreadExecutor")
    <T> void execute(RedisStreamListener<T> redisStreamListener, MapRecord<String, String, String> record, String streamKey, String group);
}
