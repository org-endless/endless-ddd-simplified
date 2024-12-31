package org.endless.ddd.simplified.starter.common.exception.model.domain.entity;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * AggregateAddItemException
 * <p>
 * create 2024/10/12 11:02
 * <p>
 * update 2024/11/13 13:49
 *
 * @author Deng Haozhi
 * @see AggregateException
 * @since 1.0.0
 */
public class AggregateAddItemException extends AggregateException {

    private static final String DEFAULT_MESSAGE = "添加子实体行为异常";

    public AggregateAddItemException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public AggregateAddItemException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public AggregateAddItemException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
