package org.endless.ddd.simplified.starter.common.exception.config.redis;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RedisConfigFailedException
 * <p>
 * create 2025/07/09 18:20
 * <p>
 * update 2025/07/09 18:20
 * update 2025/07/09 18:20
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class RedisConfigFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CFG0400;

    public RedisConfigFailedException() {
        super(ERROR_CODE);
    }

    public RedisConfigFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public RedisConfigFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RedisConfigFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
