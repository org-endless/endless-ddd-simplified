package org.endless.ddd.simplified.starter.common.exception.security.common;

import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SecurityFailedException
 * <p>
 * create 2024/12/05 18:10
 * <p>
 * update 2024/12/31 23:39
 *
 * @author Deng Haozhi
 * @see AbstractSecurityException
 * @since 1.0.0
 */
public class SecurityFailedException extends AbstractSecurityException {

    public SecurityFailedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SecurityFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public SecurityFailedException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public SecurityFailedException(ErrorCode errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }
}
