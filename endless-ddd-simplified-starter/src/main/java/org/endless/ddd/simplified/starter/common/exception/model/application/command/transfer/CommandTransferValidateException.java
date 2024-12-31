package org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * CommandTransferValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:35
 *
 * @author Deng Haozhi
 * @see CommandTransferException
 * @since 1.0.0
 */
public class CommandTransferValidateException extends CommandTransferException {

    private static final String DEFAULT_MESSAGE = "校验异常";

    public CommandTransferValidateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public CommandTransferValidateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }

    public CommandTransferValidateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }
}
