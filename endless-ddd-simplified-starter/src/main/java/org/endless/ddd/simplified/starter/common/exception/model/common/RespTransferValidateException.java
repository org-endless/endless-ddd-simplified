package org.endless.ddd.simplified.starter.common.exception.model.common;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RespTransferValidateException
 * <p>
 * create 2025/01/02 14:35
 * <p>
 * update 2025/01/02 14:35
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class RespTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0001;

    public RespTransferValidateException() {
        super(ERROR_CODE);
    }

    public RespTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public RespTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RespTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
