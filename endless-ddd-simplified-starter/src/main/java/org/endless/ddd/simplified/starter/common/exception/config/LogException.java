package org.endless.ddd.simplified.starter.common.exception.config;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * LogException
 * <p>
 * create 2024/11/13 09:19
 * <p>
 * update 2024/11/17 16:06
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class LogException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "日志处理异常";

    public LogException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public LogException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public LogException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
