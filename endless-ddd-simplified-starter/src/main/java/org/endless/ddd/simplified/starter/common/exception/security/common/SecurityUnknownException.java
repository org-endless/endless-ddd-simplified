package org.endless.ddd.simplified.starter.common.exception.security.common;


import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SecurityUnknownException
 * <p>
 * create 2024/12/05 18:11
 * <p>
 * update 2024/12/05 18:11
 *
 * @author Deng Haozhi
 * @see AbstractSecurityException
 * @since 2.0.0
 */
public class SecurityUnknownException extends AbstractSecurityException {

    public SecurityUnknownException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SecurityUnknownException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public SecurityUnknownException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public SecurityUnknownException(ErrorCode errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }
}
