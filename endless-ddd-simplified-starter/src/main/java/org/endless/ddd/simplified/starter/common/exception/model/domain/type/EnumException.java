package org.endless.ddd.simplified.starter.common.exception.model.domain.type;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * EnumException
 * <p>
 * create 2024/09/29 11:42
 * <p>
 * update 2024/11/17 16:14
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class EnumException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "枚举异常";

    public EnumException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public EnumException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public EnumException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
