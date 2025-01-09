package org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * CommandReqTransferNullException
 * <p>
 * create 2024/09/29 11:19
 * <p>
 * update 2024/11/17 16:09
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class CommandReqTransferNullException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTS0100;

    public CommandReqTransferNullException() {
        super(ERROR_CODE);
    }

    public CommandReqTransferNullException(String message) {
        super(ERROR_CODE, message);
    }

    public CommandReqTransferNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public CommandReqTransferNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
