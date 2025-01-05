package org.endless.ddd.simplified.starter.common.exception.model.common;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RespTransferException
 * <p>
 * create 2025/01/02 14:33
 * <p>
 * update 2025/01/02 14:34
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class RespTransferException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0000;

    public RespTransferException() {
        super(ERROR_CODE);
    }

    public RespTransferException(String message) {
        super(ERROR_CODE, message);
    }

    public RespTransferException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RespTransferException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
