package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SM4CryptoException
 * <p>
 * create 2024/11/18 19:43
 * <p>
 * update 2025/07/19 03:46
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class SM4CryptoException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0120;

    public SM4CryptoException() {
        super(ERROR_CODE);
    }

    public SM4CryptoException(String message) {
        super(ERROR_CODE, message);
    }

    public SM4CryptoException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public SM4CryptoException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
