package org.endless.ddd.simplified.starter.common.config.utils;

import org.endless.ddd.simplified.starter.common.config.utils.id.IdGeneratorParameters;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * UtilsAutoConfiguration
 * <p>
 * create 2024/12/25 15:15
 * <p>
 * update 2024/12/25 15:15
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@AutoConfiguration
@Import({IdGeneratorParameters.class})
public class UtilsAutoConfiguration {

}
