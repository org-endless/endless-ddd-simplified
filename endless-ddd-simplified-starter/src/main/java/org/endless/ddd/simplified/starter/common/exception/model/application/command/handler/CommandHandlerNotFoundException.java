package org.endless.ddd.simplified.starter.common.exception.model.application.command.handler;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * CommandHandlerException
 * <p>
 * create 2024/09/29 11:14
 * <p>
 * update 2024/11/17 16:08
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class CommandHandlerNotFoundException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DCD0010;

    public CommandHandlerNotFoundException() {
        super(ERROR_CODE);
    }

    public CommandHandlerNotFoundException(String message) {
        super(ERROR_CODE, message);
    }

    public CommandHandlerNotFoundException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public CommandHandlerNotFoundException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
