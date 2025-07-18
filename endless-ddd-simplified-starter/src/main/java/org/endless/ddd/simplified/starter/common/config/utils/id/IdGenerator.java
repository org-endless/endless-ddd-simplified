package org.endless.ddd.simplified.starter.common.config.utils.id;

import org.endless.ddd.simplified.starter.common.config.endless.EndlessAutoConfiguration;
import org.endless.ddd.simplified.starter.common.exception.utils.id.IdException;
import org.endless.ddd.simplified.starter.common.utils.id.snowflake.SnowflakeIdGenerator;
import org.springframework.util.StringUtils;

/**
 * IdGenerator
 * <p>
 * create 2024/11/06 17:06
 * <p>
 * update 2024/11/06 17:06
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class IdGenerator {

    private static SnowflakeIdGenerator idGenerator;

    private static Long dataCenterId;

    private static Long workerId;

    public IdGenerator(EndlessAutoConfiguration configuration) {
        IdGenerator.dataCenterId = configuration.dataCenterId();
        IdGenerator.workerId = configuration.workerId();
    }

    private static synchronized void init() {
        if (idGenerator == null) {
            idGenerator = new SnowflakeIdGenerator(dataCenterId, workerId);
        }
    }

    public static String of() {
        if (idGenerator == null) {
            init(); // 懒加载
        }
        try {
            String nextId = String.valueOf(idGenerator.nextId());
            if (!StringUtils.hasText(nextId)) {
                throw new IdException("ID生成异常");
            }
            return nextId;
        } catch (Exception e) {
            throw new IdException("ID生成异常: " + e.getMessage(), e);
        }
    }
}
