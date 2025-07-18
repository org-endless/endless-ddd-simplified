package org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream;

import org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.client.RedisStreamClient;
import org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener.RedisStreamListenerRegistrar;
import org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.listener.task.RedisStreamListenerTaskImpl;
import org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.properties.RedisStreamProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisStreamConfiguration
 * <p>
 * create 2025/07/09 17:13
 * <p>
 * update 2025/07/09 17:13
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@ConditionalOnProperty(name = "spring.redis.stream.enabled", havingValue = "true")
@EnableConfigurationProperties(RedisStreamProperties.class)
@Import({RedisStreamListenerRegistrar.class, RedisStreamListenerTaskImpl.class})
public class RedisStreamConfiguration {

    private final RedisStreamProperties properties;

    public RedisStreamConfiguration(RedisStreamProperties properties) {
        this.properties = properties;
    }

    @Lazy
    public <T> @Bean RedisStreamClient<T> redisStreamClient(RedisTemplate<String, Object> redisTemplate, Environment environment) {
        return new RedisStreamClient<T>(redisTemplate, environment);
    }

    public RedisStreamProperties properties() {
        return properties;
    }
}
