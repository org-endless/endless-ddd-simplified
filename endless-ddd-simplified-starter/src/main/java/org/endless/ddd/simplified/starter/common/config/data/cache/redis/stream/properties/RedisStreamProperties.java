package org.endless.ddd.simplified.starter.common.config.data.cache.redis.stream.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RedisStreamProperties
 * <p>
 * create 2025/07/09 17:20
 * <p>
 * update 2025/07/09 17:20
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.redis.stream")
public class RedisStreamProperties {

    private Boolean enabled;

}
