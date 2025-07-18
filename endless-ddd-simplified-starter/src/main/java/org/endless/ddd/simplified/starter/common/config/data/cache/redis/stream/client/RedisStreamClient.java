package org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.client;

import org.endless.ddd.simplified.starter.common.exception.config.redis.RedisStreamAddFailedException;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisStreamClient
 * <p>
 * create 2025/07/09 19:19
 * <p>
 * update 2025/07/09 19:19
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class RedisStreamClient<T> {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String applicationName;

    public RedisStreamClient(RedisTemplate<String, Object> redisTemplate, Environment environment) {
        this.redisTemplate = redisTemplate;
        this.applicationName = environment.getProperty("spring.application.name");
    }

    public void add(String streamKey, T message) {
        try {
            ObjectRecord<String, T> record = StreamRecords.newRecord()
                    .ofObject(message)
                    .withStreamKey(streamKey(streamKey));
            redisTemplate.opsForStream().add(record);
        } catch (Exception e) {
            throw new RedisStreamAddFailedException(e);
        }
    }

    private String streamKey(String streamKey) {
        return streamKey == null ? applicationName + "-common-stream" : streamKey;
    }
}
