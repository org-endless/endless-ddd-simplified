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
public class DrivenRespTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0301;

    public DrivenRespTransferValidateException() {
        super(ERROR_CODE);
    }

    public DrivenRespTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public DrivenRespTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DrivenRespTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
