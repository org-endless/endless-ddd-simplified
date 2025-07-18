package org.endless.ddd.simplified.starter.common.config.endless;

import org.endless.ddd.simplified.starter.common.config.endless.properties.EndlessProperties;
import org.endless.ddd.simplified.starter.common.model.domain.type.CharsetTypeEnum;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * EndlessAutoConfiguration
 * <p>
 * create 2024/11/09 19:39
 * <p>
 * update 2024/11/09 19:39
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@EnableConfigurationProperties(EndlessProperties.class)
public class EndlessAutoConfiguration {

    private final EndlessProperties properties;


    public EndlessAutoConfiguration(EndlessProperties properties) {
        this.properties = properties;
    }

    public CharsetTypeEnum charset() {
        return properties.getCharset();
    }

    public String datePattern() {
        return properties.getDatePattern();
    }

    public String dateTimePattern() {
        return properties.getDateTimePattern();
    }

    public String[] jsonAllowedTypes() {
        return properties.getJsonAllowedTypes().toArray(new String[0]);
    }

    public String springdoc() {
        return properties.getSpringdocPath();
    }

    public Long dataCenterId() {
        return properties.getDataCenterId();
    }

    public Long workerId() {
        return properties.getWorkerId();
    }
}
