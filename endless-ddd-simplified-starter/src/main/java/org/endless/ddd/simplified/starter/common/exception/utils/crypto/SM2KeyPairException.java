package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SM2KeyPairException
 * <p>
 * create 2024/11/16 04:04
 * <p>
 * update 2025/03/01 23:28
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class SM2KeyPairException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0100;

    public SM2KeyPairException() {
        super(ERROR_CODE);
    }

    public SM2KeyPairException(String message) {
        super(ERROR_CODE, message);
    }

    public SM2KeyPairException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public SM2KeyPairException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
