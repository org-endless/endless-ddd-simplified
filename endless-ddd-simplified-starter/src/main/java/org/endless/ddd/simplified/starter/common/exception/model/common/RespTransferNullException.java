package org.endless.ddd.simplified.starter.common.exception.model.common;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RespTransferNullException
 * <p>
 * create 2025/01/02 14:33
 * <p>
 * update 2025/01/02 14:34
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class RespTransferNullException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0000;

    public RespTransferNullException() {
        super(ERROR_CODE);
    }

    public RespTransferNullException(String message) {
        super(ERROR_CODE, message);
    }

    public RespTransferNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RespTransferNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
