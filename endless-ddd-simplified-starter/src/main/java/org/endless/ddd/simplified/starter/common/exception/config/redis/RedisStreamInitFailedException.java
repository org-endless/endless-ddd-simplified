package org.endless.ddd.simplified.starter.common.exception.config.redis;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RedisStreamInitFailedException
 * <p>
 * create 2025/07/10 04:59
 * <p>
 * update 2025/07/10 05:01
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class RedisStreamInitFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CFG0420;

    public RedisStreamInitFailedException() {
        super(ERROR_CODE);
    }

    public RedisStreamInitFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public RedisStreamInitFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RedisStreamInitFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
