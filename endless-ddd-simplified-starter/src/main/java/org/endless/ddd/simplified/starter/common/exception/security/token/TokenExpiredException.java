package org.endless.ddd.simplified.starter.common.exception.security.token;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * TokenExpiresException
 * <p>
 * create 2024/12/25 13:57
 * <p>
 * update 2024/12/25 13:57
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class TokenExpiredException extends SecurityFailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SEC0014;

    public TokenExpiredException() {
        super(ERROR_CODE);
    }

    public TokenExpiredException(String message) {
        super(ERROR_CODE, message);
    }

    public TokenExpiredException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public TokenExpiredException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
