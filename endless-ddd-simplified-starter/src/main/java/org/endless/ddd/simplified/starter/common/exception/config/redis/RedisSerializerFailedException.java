package org.endless.ddd.simplified.starter.common.exception.config.redis;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RedisSerializerFailedException
 * <p>
 * create 2025/07/09 18:25
 * <p>
 * update 2025/07/09 18:25
 * update 2025/07/09 18:25
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class RedisSerializerFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CFG0410;

    public RedisSerializerFailedException() {
        super(ERROR_CODE);
    }

    public RedisSerializerFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public RedisSerializerFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RedisSerializerFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
