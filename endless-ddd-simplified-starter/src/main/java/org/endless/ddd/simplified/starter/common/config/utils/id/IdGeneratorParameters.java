package org.endless.ddd.simplified.starter.common.config.utils.id;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


/**
 * IdGeneratorParameters
 * <p>
 * create 2024/12/25 10:32
 * <p>
 * update 2024/12/25 10:32
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
public class IdGeneratorParameters {

    @Value("${server.data-center-id:1}")
    private Long dataCenterId;

    @Value("${server.worker-id:1}")
    private Long workerId;
}
