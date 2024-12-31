package org.endless.ddd.simplified.starter.common.exception.security.token;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * TokenGenerateException
 * <p>
 * create 2024/12/24 16:52
 * <p>
 * update 2024/12/24 16:54
 *
 * @author Deng Haozhi
 * @see SecurityFailedException
 * @since 2.0.0
 */
public class TokenGenerateException extends SecurityFailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SEC0011;

    public TokenGenerateException() {
        super(ERROR_CODE);
    }

    public TokenGenerateException(String message) {
        super(ERROR_CODE, message);
    }

    public TokenGenerateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public TokenGenerateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
