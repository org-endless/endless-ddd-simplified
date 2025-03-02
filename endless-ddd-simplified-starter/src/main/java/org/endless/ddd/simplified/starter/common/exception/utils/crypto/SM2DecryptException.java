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
public class SM2DecryptException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0102;

    public SM2DecryptException() {
        super(ERROR_CODE);
    }

    public SM2DecryptException(String message) {
        super(ERROR_CODE, message);
    }

    public SM2DecryptException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public SM2DecryptException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
