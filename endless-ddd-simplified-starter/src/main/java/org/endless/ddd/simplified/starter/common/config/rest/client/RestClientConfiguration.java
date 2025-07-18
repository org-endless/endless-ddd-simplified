package org.endless.ddd.simplified.starter.common.config.rest.client;

import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.endless.ddd.simplified.starter.common.config.rest.converter.FastJson2HttpMessageConverter;
import org.endless.ddd.simplified.starter.common.config.rest.converter.FormHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;


/**
 * RestClientConfigutration
 * <p>
 * create 2024/11/09 19:47
 * <p>
 * update 2024/11/09 19:47
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class RestClientConfiguration {

    private final EndlessAutoConfiguration configuration;

    public RestClientConfiguration(EndlessAutoConfiguration configuration) {
        this.configuration = configuration;
    }

    @ConditionalOnMissingBean
    public @Bean RestClient restClient() {
        RestTemplate restTemplate = new RestTemplate();
        // 清除默认的 Jackson 转换器
        restTemplate.getMessageConverters().removeIf(converter ->
                converter instanceof MappingJackson2HttpMessageConverter);
        // 添加 Fastjson2 转换器
        restTemplate.getMessageConverters().add(new FastJson2HttpMessageConverter<>(configuration));
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter<>(configuration));
        restTemplate.setErrorHandler(new RestClientResponseErrorHandler());
        return RestClient.create(restTemplate);
    }
}
