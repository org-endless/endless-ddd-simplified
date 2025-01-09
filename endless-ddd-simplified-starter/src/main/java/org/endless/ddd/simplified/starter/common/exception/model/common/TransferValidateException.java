package org.endless.ddd.simplified.starter.common.exception.model.common;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * TransferValidateException
 * <p>
 * create 2025/01/02 14:30
 * <p>
 * update 2025/01/02 14:31
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class TransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTO0001;

    public TransferValidateException() {
        super(ERROR_CODE);
    }

    public TransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public TransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public TransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
