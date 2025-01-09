package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DrivenReqTransferNullException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/09/29 11:08
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class DrivenRespTransferNullException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0300;

    public DrivenRespTransferNullException() {
        super(ERROR_CODE);
    }

    public DrivenRespTransferNullException(String message) {
        super(ERROR_CODE, message);
    }

    public DrivenRespTransferNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DrivenRespTransferNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
