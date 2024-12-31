package org.endless.ddd.simplified.starter.common.exception.model.domain.entity;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * AggregateValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:38
 *
 * @author Deng Haozhi
 * @see AggregateException
 * @since 1.0.0
 */
public class AggregateValidateException extends AggregateException {

    private static final String DEFAULT_MESSAGE = "校验异常";

    public AggregateValidateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public AggregateValidateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }

    public AggregateValidateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }
}
