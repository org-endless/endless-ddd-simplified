package org.endless.ddd.simplified.starter.common.exception.model.domain.value;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * ValueValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:40
 *
 * @author Deng Haozhi
 * @see ValueException
 * @since 1.0.0
 */
public class ValueValidateException extends ValueException {

    private static final String DEFAULT_MESSAGE = "校验异常";

    public ValueValidateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public ValueValidateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }

    public ValueValidateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }
}
