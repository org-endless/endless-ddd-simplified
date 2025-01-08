package org.endless.ddd.simplified.starter.common.config.utils.id;

import org.endless.ddd.simplified.starter.common.exception.utils.id.IdException;
import org.endless.ddd.simplified.starter.common.utils.id.snowflake.SnowflakeIdGenerator;
import org.springframework.context.annotation.Import;
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
@Import(IdGeneratorParameters.class)
public class IdGenerator {

    private static SnowflakeIdGenerator idGenerator;

    private static IdGeneratorParameters parameters;

    public IdGenerator(IdGeneratorParameters parameters) {
        IdGenerator.parameters = parameters;
    }

    private static synchronized void init() {
        if (idGenerator == null) {
            idGenerator = new SnowflakeIdGenerator(
                    parameters.getDataCenterId(),
                    parameters.getWorkerId()
            );
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
