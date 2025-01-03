package org.endless.ddd.simplified.starter.common.config.endless.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.model.domain.type.CharsetTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * EndlessProperties
 * <p>
 * create 2024/11/09 19:38
 * <p>
 * update 2024/11/09 19:38
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "endless")
public class EndlessProperties {

    private CharsetTypeEnum charset = CharsetTypeEnum.UTF8;

    private String datePattern = "yyyy-MM-dd";

    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss:SSS";

    private List<String> jsonAllowedTypes = Arrays.asList(
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Boolean",
            "java.util.List",
            "java.util.Map"
    );

    private String springdocPath = "/springdoc";
}
