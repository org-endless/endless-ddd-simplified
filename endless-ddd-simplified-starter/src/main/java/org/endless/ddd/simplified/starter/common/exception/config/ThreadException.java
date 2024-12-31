package org.endless.ddd.simplified.starter.common.exception.config;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * ThreadException
 * <p>
 * create 2024/11/10 15:19
 * <p>
 * update 2024/11/17 16:06
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class ThreadException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "线程异步处理异常";

    public ThreadException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public ThreadException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public ThreadException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
