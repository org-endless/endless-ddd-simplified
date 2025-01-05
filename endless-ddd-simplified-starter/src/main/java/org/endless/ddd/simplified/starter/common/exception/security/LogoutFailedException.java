package org.endless.ddd.simplified.starter.common.exception.security;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * LogoutFailedException
 * <p>
 * create 2024/12/19 16:57
 * <p>
 * update 2024/12/19 16:57
 *
 * @author Deng Haozhi
 * @see SecurityFailedException
 * @since 1.0.0
 */
public class LogoutFailedException extends SecurityFailedException {

    public LogoutFailedException() {
        super(ErrorCode.SEC0020);
    }

    public LogoutFailedException(String message) {
        super(ErrorCode.SEC0020, message);
    }

    public LogoutFailedException(Throwable throwable) {
        super(ErrorCode.SEC0020, throwable);
    }

    public LogoutFailedException(String message, Throwable throwable) {
        super(ErrorCode.SEC0020, message, throwable);
    }
}
