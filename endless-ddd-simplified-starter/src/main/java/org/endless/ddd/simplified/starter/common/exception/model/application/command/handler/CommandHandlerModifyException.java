package org.endless.ddd.simplified.starter.common.exception.model.application.command.handler;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * CommandHandlerModifyException
 * <p>
 * create 2024/09/29 11:14
 * <p>
 * update 2024/11/17 16:08
 *
 * @author Deng Haozhi
 * @see CommandHandlerException
 * @since 1.0.0
 */
public class CommandHandlerModifyException extends CommandHandlerException {

    private static final String DEFAULT_MESSAGE = "修改命令异常";

    public CommandHandlerModifyException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public CommandHandlerModifyException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public CommandHandlerModifyException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
