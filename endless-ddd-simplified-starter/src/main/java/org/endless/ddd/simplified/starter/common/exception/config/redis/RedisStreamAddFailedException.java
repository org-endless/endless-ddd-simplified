package org.endless.ddd.simplified.starter.common.exception.config.redis;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RedisStreamAddFailedException
 * <p>
 * create 2025/07/10 03:58
 * <p>
 * update 2025/07/10 03:58
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class RedisStreamAddFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CFG0421;

    public RedisStreamAddFailedException() {
        super(ERROR_CODE);
    }

    public RedisStreamAddFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public RedisStreamAddFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RedisStreamAddFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
