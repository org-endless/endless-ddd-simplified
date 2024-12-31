package org.endless.ddd.simplified.starter.common.exception.model.application.command.handler;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * CommandHandlerRemoveException
 * <p>
 * create 2024/11/10 13:53
 * <p>
 * update 2024/11/17 16:08
 *
 * @author Deng Haozhi
 * @see CommandHandlerException
 * @since 1.0.0
 */
public class CommandHandlerRemoveException extends CommandHandlerException {

    private static final String DEFAULT_MESSAGE = "删除命令异常";

    public CommandHandlerRemoveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public CommandHandlerRemoveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public CommandHandlerRemoveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
