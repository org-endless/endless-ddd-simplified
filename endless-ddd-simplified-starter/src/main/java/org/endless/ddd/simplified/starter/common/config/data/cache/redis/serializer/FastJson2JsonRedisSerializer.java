package org.endless.ddd.simplified.starter.common.config.data.cache.redis.serializer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.endless.ddd.simplified.starter.common.exception.config.redis.RedisSerializerFailedException;
import org.endless.ddd.simplified.starter.common.utils.model.json.JsonTools;
import org.endless.ddd.simplified.starter.common.utils.model.object.ObjectTools;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;

/**
 * FastJsonRedisSerializer
 * <p>
 * create 2024/11/07 10:59
 * <p>
 * update 2024/11/17 15:59
 *
 * @author Deng Haozhi
 * @see RedisSerializer
 * @since 1.0.0
 */
@Slf4j
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private final EndlessAutoConfiguration configuration;

    private final Class<T> clazz;

    public FastJson2JsonRedisSerializer(EndlessAutoConfiguration configuration, Class<T> clazz) {
        this.configuration = configuration;
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) {
        try {
            if (t == null) {
                return new byte[0];
            }
            log.trace("[Redis序列化对象]: {}", ObjectTools.maskSensitive(t).replaceAll("[\\r\\n\\s]", ""));
            return JSON.toJSONString(t, filter(), JSONWriter.Feature.PrettyFormat).getBytes(charset());
        } catch (Exception e) {
            throw new RedisSerializerFailedException("Redis序列化对象异常: " + e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            String string = new String(bytes, charset());
            log.trace("[Redis反序列化对象]: {}", JsonTools.maskSensitive(string.replaceAll("[\\r\\n\\s]", "")));
            return JSON.parseObject(string, clazz, filter());
        } catch (Exception e) {
            throw new RedisSerializerFailedException("Redis反序列化对象异常: " + e.getMessage(), e);
        }
    }

    private Filter filter() {
        return JSONReader.autoTypeFilter(configuration.jsonAllowedTypes());
    }

    private Charset charset() {
        return configuration.charset().getCharset();
    }

}
