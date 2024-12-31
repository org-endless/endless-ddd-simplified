package org.endless.ddd.simplified.starter.common.exception.utils.model;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * ObjectToolsException
 * <p>
 * create 2024/11/19 10:25
 * <p>
 * update 2024/11/19 10:26
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 2.0.0
 */
public class ObjectToolsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "对象工具异常";

    public ObjectToolsException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public ObjectToolsException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public ObjectToolsException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
