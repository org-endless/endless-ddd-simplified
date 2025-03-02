package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SM2DecryptException
 * <p>
 * create 2024/11/16 04:04
 * <p>
 * update 2024/11/16 23:54
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class RSADecryptException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0132;

    public RSADecryptException() {
        super(ERROR_CODE);
    }

    public RSADecryptException(String message) {
        super(ERROR_CODE, message);
    }

    public RSADecryptException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RSADecryptException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
