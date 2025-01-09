package org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * CommandTransferValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:35
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class CommandTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTO0101;

    public CommandTransferValidateException() {
        super(ERROR_CODE);
    }

    public CommandTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public CommandTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public CommandTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
