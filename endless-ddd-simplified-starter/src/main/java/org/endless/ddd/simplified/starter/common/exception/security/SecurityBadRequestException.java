package org.endless.ddd.simplified.starter.common.exception.security;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SecurityBadRequestException
 * <p>
 * create 2025/01/22 14:49
 * <p>
 * update 2025/01/22 15:02
 *
 * @author Deng Haozhi
 * @see SecurityFailedException
 * @since 1.0.0
 */
public class SecurityBadRequestException extends SecurityFailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SEC0040;

    public SecurityBadRequestException() {
        super(ERROR_CODE);
    }

    public SecurityBadRequestException(String message) {
        super(ERROR_CODE, message);
    }

    public SecurityBadRequestException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public SecurityBadRequestException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
