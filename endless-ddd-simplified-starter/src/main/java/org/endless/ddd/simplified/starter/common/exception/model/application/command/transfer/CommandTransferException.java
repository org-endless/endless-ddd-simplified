package org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * CommandTransferException
 * <p>
 * create 2024/09/29 11:19
 * <p>
 * update 2024/11/17 16:09
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class CommandTransferException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "命令传输对象异常";

    public CommandTransferException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public CommandTransferException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public CommandTransferException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
