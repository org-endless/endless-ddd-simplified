package org.endless.ddd.simplified.starter.common.exception.identity;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * UnIdentityVerificationException
 * <p>
 * create 2025/01/09 17:42
 * <p>
 * update 2025/01/09 17:42
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class IdentityNotVerifiedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.IDA0000;

    public IdentityNotVerifiedException() {
        super(ERROR_CODE);
    }

    public IdentityNotVerifiedException(String message) {
        super(ERROR_CODE, message);
    }

    public IdentityNotVerifiedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public IdentityNotVerifiedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
