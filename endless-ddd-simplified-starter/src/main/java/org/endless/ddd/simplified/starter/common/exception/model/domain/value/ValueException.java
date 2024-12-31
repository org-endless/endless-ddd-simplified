package org.endless.ddd.simplified.starter.common.exception.model.domain.value;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * ValueException
 * <p>
 * create 2024/09/29 11:33
 * <p>
 * update 2024/11/17 16:14
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class ValueException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "值对象异常";

    public ValueException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public ValueException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public ValueException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
