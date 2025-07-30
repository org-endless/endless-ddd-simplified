package org.endless.ddd.simplified.starter.common.config.rest.converter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest.RestErrorException;
import org.endless.ddd.simplified.starter.common.utils.model.json.JsonTools;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * FastJson2HttpMessageConverter
 * <p>
 * create 2024/11/09 19:28
 * <p>
 * update 2024/11/17 16:28
 *
 * @author Deng Haozhi
 * @see AbstractHttpMessageConverter
 * @since 1.0.0
 */
@Slf4j
public class FastJson2HttpMessageConverter<T> extends AbstractGenericHttpMessageConverter<T> {

    private final EndlessAutoConfiguration configuration;

    private final Charset charset;

    public FastJson2HttpMessageConverter(EndlessAutoConfiguration configuration) {
        super(MediaType.APPLICATION_JSON);
        this.configuration = configuration;
        this.charset = configuration.charset().getCharset();
    }

    @Override
    protected boolean supports(@NonNull Class<?> clazz) {
        return true;
    }

    @Override
    protected @NonNull T readInternal(@NonNull Class<? extends T> clazz, @NonNull HttpInputMessage inputMessage) {
        try {
            return read(clazz, clazz, inputMessage);
        } catch (IOException | HttpMessageNotReadableException e) {
            throw new RestErrorException("Rest反序列化对象异常: " + e.getMessage(), e);
        }
    }

    @NonNull
    @Override
    public T read(@NonNull Type type, Class<?> contextClass, @NonNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try (InputStream inputStream = inputMessage.getBody()) {
            byte[] buffer = inputStream.readAllBytes();
            String string = new String(buffer, charset);
            log.trace("[Rest反序列化对象]: {}", JsonTools.maskSensitive(string.replaceAll("[\\r\\n\\s]", "")));
            return JSON.parseObject(string, type, filter());
        } catch (Exception e) {
            throw new RestErrorException("Rest反序列化对象异常: " + e.getMessage(), e);
        }
    }

    @Override
    protected void writeInternal(@NonNull T t, @NonNull HttpOutputMessage outputMessage) {
        try {
            writeInternal(t, t.getClass(), outputMessage);
        } catch (IOException | HttpMessageNotWritableException e) {
            throw new RestErrorException("Rest序列化对象异常: " + e.getMessage(), e);
        }
    }

    @Override
    protected void writeInternal(@NonNull T t, Type type, @NonNull HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            String json = JSON.toJSONString(t, filter(), JSONWriter.Feature.PrettyFormat);
            log.trace("[Rest序列化对象]: {}", JsonTools.maskSensitive(json.replaceAll("[\\r\\n\\s]", "")));
            outputMessage.getBody().write(json.getBytes(charset));
        } catch (Exception e) {
            throw new RestErrorException("Rest序列化对象异常: " + e.getMessage(), e);
        }
    }

    private Filter filter() {
        return JSONReader.autoTypeFilter(configuration.jsonAllowedTypes());
    }
}
