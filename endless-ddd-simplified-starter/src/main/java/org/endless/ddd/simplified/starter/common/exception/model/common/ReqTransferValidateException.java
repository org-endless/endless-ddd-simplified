package org.endless.ddd.simplified.starter.common.exception.model.common;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * ReqTransferValidateException
 * <p>
 * create 2025/01/02 14:30
 * <p>
 * update 2025/01/02 14:31
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class ReqTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTS0001;

    public ReqTransferValidateException() {
        super(ERROR_CODE);
    }

    public ReqTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public ReqTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public ReqTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
