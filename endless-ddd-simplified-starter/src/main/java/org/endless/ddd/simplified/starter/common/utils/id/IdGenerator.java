package org.endless.ddd.simplified.starter.common.utils.id;

import org.endless.ddd.simplified.starter.common.config.utils.id.IdGeneratorParameters;
import org.endless.ddd.simplified.starter.common.exception.utils.id.IdException;
import org.endless.ddd.simplified.starter.common.exception.utils.id.SnowflakeIdException;
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

    private static final SnowflakeIdGenerator idGenerator;

    static {
        idGenerator = new SnowflakeIdGenerator(
                IdGeneratorParameters.getDataCenterId(),
                IdGeneratorParameters.getWorkerId());
    }

    public static String of() {
        try {
            String nextId = String.valueOf(idGenerator.nextId());
            if (!StringUtils.hasText(nextId)) {
                throw new IdException("ID生成异常");
            }
            return nextId;
        } catch (IdException | SnowflakeIdException e) {
            throw e;
        } catch (Exception e) {
            throw new IdException("ID生成异常: " + e.getMessage(), e);
        }
    }
}
