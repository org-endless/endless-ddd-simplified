package org.endless.ddd.simplified.starter.common.config.utils.id;

import jakarta.annotation.PostConstruct;
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
public class IdGeneratorParameters {

    private static Long cachedDataCenterId;

    private static Long cachedWorkerId;

    @Value("${server.data-center-id}")
    private Long dataCenterId;

    @Value("${server.worker-id}")
    private Long workerId;

    public static Long getDataCenterId() {
        return cachedDataCenterId;
    }

    public static Long getWorkerId() {
        return cachedWorkerId;
    }

    @PostConstruct
    public void init() {
        cachedDataCenterId = dataCenterId;
        cachedWorkerId = workerId;
    }
}
