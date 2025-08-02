package org.endless.ddd.simplified.starter.common.config.utils.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
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
@ConditionalOnProperty(name = "spring.freemarker.enabled", havingValue = "true", matchIfMissing = true)
public class FreemarkerConfiguration {

    private final Environment environment;

    private final EndlessAutoConfiguration configuration;

    public FreemarkerConfiguration(Environment environment, EndlessAutoConfiguration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Lazy
    @ConditionalOnMissingBean
    public @Bean BeanPostProcessor freemarkerConfigCustomizer() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof Configuration && "freeMarkerConfiguration".equals(beanName)) {
                    ((Configuration) bean).setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                    ((Configuration) bean).setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                    ((Configuration) bean).setDefaultEncoding(environment.getProperty("spring.freemarker.charset", configuration.charset().name()));
                    ((Configuration) bean).setClassForTemplateLoading(this.getClass(),
                            environment.getProperty("spring.freemarker.template-loader-path", "classpath:/templates/freemarker"));
                }
                return bean;
            }
        };
    }

}
