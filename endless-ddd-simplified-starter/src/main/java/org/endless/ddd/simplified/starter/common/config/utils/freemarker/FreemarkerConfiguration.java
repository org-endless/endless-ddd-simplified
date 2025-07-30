package org.endless.ddd.simplified.starter.common.config.utils.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

/**
 * FreemarkerConfiguration
 * <p>
 * create 2025/07/30 21:33
 * <p>
 * update 2025/07/30 21:33
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@ConditionalOnProperty(name = "spring", havingValue = "freemarker")
public class FreemarkerConfiguration {

    @Lazy
    @ConditionalOnMissingBean
    public @Bean Configuration freemarkerConfiguration(Environment environment, EndlessAutoConfiguration configuration) {
        Configuration config = new Configuration(Configuration.VERSION_2_3_34);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setDefaultEncoding(environment.getProperty("spring.freemarker.charset", configuration.charset().name()));
        config.setClassForTemplateLoading(this.getClass(), environment.getProperty("spring.freemarker.template-loader-path", "classpath:/templates"));
        return config;
    }
}
