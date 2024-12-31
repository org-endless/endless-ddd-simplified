package org.endless.ddd.simplified.starter.common.exception.security;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityUnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * LogoutUnknownException
 * <p>
 * create 2024/12/19 16:54
 * <p>
 * update 2024/12/19 16:54
 *
 * @author Deng Haozhi
 * @see SecurityUnknownException
 * @since 2.0.0
 */
public class LogoutUnknownException extends SecurityUnknownException {

    public LogoutUnknownException() {
        super(ErrorCode.SEC9020);
    }

    public LogoutUnknownException(String message) {
        super(ErrorCode.SEC9020, message);
    }

    public LogoutUnknownException(Throwable throwable) {
        super(ErrorCode.SEC9020, throwable);
    }

    public LogoutUnknownException(String message, Throwable throwable) {
        super(ErrorCode.SEC9020, message, throwable);
    }
}
