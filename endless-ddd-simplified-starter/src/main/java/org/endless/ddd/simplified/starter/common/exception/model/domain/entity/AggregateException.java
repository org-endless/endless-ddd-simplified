package org.endless.ddd.simplified.starter.common.exception.model.domain.entity;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * AggregateException
 * <p>
 * create 2024/09/29 11:30
 * <p>
 * update 2024/11/17 16:12
 *
 * @author Deng Haozhi
 * @see EntityException
 * @since 1.0.0
 */
public class AggregateException extends EntityException {

    private static final String DEFAULT_MESSAGE = "聚合根异常";

    public AggregateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public AggregateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public AggregateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
