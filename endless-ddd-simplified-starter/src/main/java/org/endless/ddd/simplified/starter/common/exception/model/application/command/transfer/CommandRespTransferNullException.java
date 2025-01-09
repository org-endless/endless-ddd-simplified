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
public class CommandRespTransferNullException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0101;

    public CommandRespTransferNullException() {
        super(ERROR_CODE);
    }

    public CommandRespTransferNullException(String message) {
        super(ERROR_CODE, message);
    }

    public CommandRespTransferNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public CommandRespTransferNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
