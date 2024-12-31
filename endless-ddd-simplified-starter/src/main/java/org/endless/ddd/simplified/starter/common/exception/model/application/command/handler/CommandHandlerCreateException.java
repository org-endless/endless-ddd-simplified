package org.endless.ddd.simplified.starter.common.exception.model.application.command.handler;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * CommandHandlerCreateException
 * <p>
 * create 2024/09/29 11:14
 * <p>
 * update 2024/11/17 16:07
 *
 * @author Deng Haozhi
 * @see CommandHandlerException
 * @since 1.0.0
 */
public class CommandHandlerCreateException extends CommandHandlerException {

    private static final String DEFAULT_MESSAGE = "创建命令异常";

    public CommandHandlerCreateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public CommandHandlerCreateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public CommandHandlerCreateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
