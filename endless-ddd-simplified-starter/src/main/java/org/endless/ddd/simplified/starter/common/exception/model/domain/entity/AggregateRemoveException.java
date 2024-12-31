package org.endless.ddd.simplified.starter.common.exception.model.domain.entity;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * AggregateRemoveException
 * <p>
 * create 2024/10/12 10:11
 * <p>
 * update 2024/11/17 16:12
 *
 * @author Deng Haozhi
 * @see AggregateException
 * @since 1.0.0
 */
public class AggregateRemoveException extends AggregateException {

    private static final String DEFAULT_MESSAGE = "删除行为异常";

    public AggregateRemoveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public AggregateRemoveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public AggregateRemoveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
