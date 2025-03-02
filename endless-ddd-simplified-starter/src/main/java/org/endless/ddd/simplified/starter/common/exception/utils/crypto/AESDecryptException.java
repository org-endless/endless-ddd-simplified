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
public class AESDecryptException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0142;

    public AESDecryptException() {
        super(ERROR_CODE);
    }

    public AESDecryptException(String message) {
        super(ERROR_CODE, message);
    }

    public AESDecryptException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public AESDecryptException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
