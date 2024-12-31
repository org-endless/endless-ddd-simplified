package org.endless.ddd.simplified.starter.common.exception.model.domain.event;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * EventException
 * <p>
 * create 2024/09/29 11:32
 * <p>
 * update 2024/11/17 16:13
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class EventException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "领域事件异常";

    public EventException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public EventException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public EventException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
