package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DrivenTransferValidateException
 * <p>
 * create 2025/01/09 17:55
 * <p>
 * update 2025/01/09 17:56
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class DrivenTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTO0301;

    public DrivenTransferValidateException() {
        super(ERROR_CODE);
    }

    public DrivenTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public DrivenTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DrivenTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
