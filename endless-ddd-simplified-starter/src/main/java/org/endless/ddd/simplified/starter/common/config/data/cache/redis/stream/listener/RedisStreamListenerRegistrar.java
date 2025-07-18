package org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener;

import io.lettuce.core.RedisBusyException;
import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener.task.RedisStreamListenerTask;
import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.endless.ddd.simplified.starter.common.exception.config.redis.RedisStreamInitFailedException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * RedisStreamListenerRegistrar
 * <p>
 * create 2025/07/09 18:04
 * <p>
 * update 2025/07/09 18:04
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
@Slf4j
public class RedisStreamListenerRegistrar {

    private final RedisConnectionFactory connectionFactory;

    private final RedisTemplate<String, Object> redisTemplate;

    private final List<RedisStreamListener<?>> redisStreamListeners;

    private final RedisStreamListenerTask redisStreamListenerTask;

    private final EndlessAutoConfiguration configuration;

    private final String applicationName;

    public RedisStreamListenerRegistrar(
            RedisConnectionFactory connectionFactory,
            RedisTemplate<String, Object> redisTemplate,
            List<RedisStreamListener<?>> redisStreamListeners,
            RedisStreamListenerTask redisStreamListenerTask,
            EndlessAutoConfiguration configuration,
            Environment environment) {
        this.connectionFactory = connectionFactory;
        this.redisTemplate = redisTemplate;
        this.redisStreamListeners = redisStreamListeners;
        this.redisStreamListenerTask = redisStreamListenerTask;
        this.configuration = configuration;
        this.applicationName = environment.getProperty("spring.application.name");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        for (RedisStreamListener<?> redisStreamListener : redisStreamListeners) {
            register(redisStreamListener);
        }
    }

    private <T> void register(RedisStreamListener<T> redisStreamListener) {

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(Duration.ofMillis(redisStreamListener.block() == null ? 200 : redisStreamListener.block()))
                        .batchSize(redisStreamListener.batchSize() == null ? 10 : redisStreamListener.batchSize())
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
                StreamMessageListenerContainer.create(connectionFactory, containerOptions);

        String streamKey = streamKey(redisStreamListener.streamKey());
        String group = group(redisStreamListener.group());
        String consumer = consumer(redisStreamListener.consumer());

        StreamListener<String, MapRecord<String, String, String>> listener = record -> {
            redisStreamListenerTask.execute(redisStreamListener, record, streamKey, group);
        };

        initStream(streamKey, group);

        container.receive(
                Consumer.from(group, consumer),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                listener);

        container.start();
        log.trace("注册 Redis Stream 监听器: stream={}, group={}, consumer={}, handler={}", streamKey, group, consumer, redisStreamListener.getClass().getSimpleName());
    }

    private void initStream(String streamKey, String group) {
        try {
            if (!Boolean.TRUE.equals(redisTemplate.hasKey(streamKey))) {
                RecordId id = redisTemplate.opsForStream().add(
                        StreamRecords.newRecord()
                                .ofMap(Collections.singletonMap("init", "true"))
                                .withStreamKey(streamKey)
                );
                redisTemplate.opsForStream().delete(streamKey, id);
            }
            try {
                redisTemplate.opsForStream().createGroup(streamKey, group);

            } catch (RedisSystemException e) {
                if (e.getRootCause() instanceof RedisBusyException) {
                    log.trace("Redis Stream Group 已存在，跳过创建");
                } else {
                    throw e;
                }
            }
        } catch (Exception e) {
            throw new RedisStreamInitFailedException(e);
        }
    }

    public String streamKey(String streamKey) {
        return StringUtils.hasText(streamKey) ? streamKey : applicationName + "-common-stream";
    }

    private String group(String group) {
        return StringUtils.hasText(group) ? group : applicationName + "-common-group";
    }

    private String consumer(String consumer) {
        return StringUtils.hasText(consumer) ? consumer : applicationName + "-common-consumer-" + configuration.dataCenterId() + "-" + configuration.workerId();
    }
}
