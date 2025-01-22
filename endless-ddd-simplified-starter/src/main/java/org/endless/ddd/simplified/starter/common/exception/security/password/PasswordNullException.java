package org.endless.ddd.simplified.starter.common.exception.security.password;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * UsernameNullException
 * <p>
 * create 2025/01/22 15:01
 * <p>
 * update 2025/01/22 15:03
 *
 * @author Deng Haozhi
 * @see SecurityFailedException
 * @since 1.0.0
 */
public class PasswordNullException extends SecurityFailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SEC0002;

    public PasswordNullException() {
        super(ERROR_CODE);
    }

    public PasswordNullException(String message) {
        super(ERROR_CODE, message);
    }

    public PasswordNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public PasswordNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
