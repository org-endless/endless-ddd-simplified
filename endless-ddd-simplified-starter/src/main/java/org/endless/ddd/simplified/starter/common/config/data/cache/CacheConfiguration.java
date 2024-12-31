package org.endless.ddd.simplified.starter.common.config.data.cache;

import org.endless.ddd.simplified.starter.common.config.data.cache.redis.RedisConfiguration;
import org.endless.ddd.simplified.starter.common.config.data.cache.redis.serializer.FastJson2JsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * CacheConfiguration
 * <p>
 * create 2024/11/10 05:49
 * <p>
 * update 2024/11/17 16:00
 *
 * @author Deng Haozhi
 * @see CachingConfigurer
 * @since 1.0.0
 */
@EnableCaching
@Import(RedisConfiguration.class)
@ConditionalOnProperty(name = "spring.cache.type")
public class CacheConfiguration implements CachingConfigurer {

    @Bean
    @ConditionalOnClass(RedisCacheManager.class)
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, FastJson2JsonRedisSerializer<Object> redisSerializer) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(
                        RedisCacheConfiguration.defaultCacheConfig()
                                .disableCachingNullValues()
                                .entryTtl(Duration.ofMinutes(60))
                                .prefixCacheNameWith("atp:")
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)))
                .transactionAware()
                .build();
    }
}
