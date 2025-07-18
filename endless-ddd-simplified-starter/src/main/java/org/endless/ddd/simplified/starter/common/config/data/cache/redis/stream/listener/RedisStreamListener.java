package org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener;

/**
 * RedisStreamListener
 * <p>
 * create 2025/07/09 17:59
 * <p>
 * update 2025/07/09 17:59
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public interface RedisStreamListener<T> {

    void execute(T payload);

    String streamKey();

    String group();

    String consumer();

    Long block();

    Integer batchSize();

    Class<T> getPayloadClass();
}
