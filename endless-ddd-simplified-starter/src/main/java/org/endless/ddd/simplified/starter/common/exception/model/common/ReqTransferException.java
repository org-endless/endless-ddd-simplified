package org.endless.ddd.simplified.starter.common.exception.model.common;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * ReqTransferException
 * <p>
 * create 2025/01/02 14:18
 * <p>
 * update 2025/01/02 14:18
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class ReqTransferException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTS0000;

    public ReqTransferException() {
        super(ERROR_CODE);
    }

    public ReqTransferException(String message) {
        super(ERROR_CODE, message);
    }

    public ReqTransferException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public ReqTransferException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
