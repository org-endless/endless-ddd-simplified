package org.endless.ddd.simplified.starter.common.config.rest;

import org.endless.ddd.simplified.starter.common.config.rest.client.RestClientConfiguration;
import org.endless.ddd.simplified.starter.common.config.rest.server.RestServerConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * RestAutoConfiguration
 * <p>
 * create 2024/11/09 19:48
 * <p>
 * update 2024/11/09 19:48
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnClass(WebMvcConfigurer.class)
@Import({RestClientConfiguration.class, RestServerConfiguration.class})
public class RestAutoConfiguration {

}
