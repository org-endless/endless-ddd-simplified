package org.endless.ddd.simplified.starter.common.exception.config.redis;

import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RedisConfigUnknownException
 * <p>
 * create 2025/07/09 18:22
 * <p>
 * update 2025/07/09 18:22
 * update 2025/07/09 18:23
 *
 * @author Deng Haozhi
 * @see UnknownException
 * @since 1.0.0
 */
public class RedisConfigUnknownException extends UnknownException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CFG0401;

    public RedisConfigUnknownException() {
        super(ERROR_CODE);
    }

    public RedisConfigUnknownException(String message) {
        super(ERROR_CODE, message);
    }

    public RedisConfigUnknownException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RedisConfigUnknownException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
