package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DrivenReqTransferValidateException
 * <p>
 * create 2025/01/09 17:55
 * <p>
 * update 2025/01/09 17:56
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class DrivenReqTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTS0301;

    public DrivenReqTransferValidateException() {
        super(ERROR_CODE);
    }

    public DrivenReqTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public DrivenReqTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DrivenReqTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
