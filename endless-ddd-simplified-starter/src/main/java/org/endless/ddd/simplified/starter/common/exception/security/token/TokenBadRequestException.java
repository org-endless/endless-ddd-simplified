package org.endless.ddd.simplified.starter.common.exception.security.token;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * TokenBadRequestException
 * <p>
 * create 2024/12/06 00:26
 * <p>
 * update 2024/12/06 00:27
 *
 * @author Deng Haozhi
 * @see SecurityFailedException
 * @since 1.0.0
 */
public class TokenBadRequestException extends SecurityFailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SEC0010;

    public TokenBadRequestException() {
        super(ERROR_CODE);
    }

    public TokenBadRequestException(String message) {
        super(ERROR_CODE, message);
    }

    public TokenBadRequestException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public TokenBadRequestException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
