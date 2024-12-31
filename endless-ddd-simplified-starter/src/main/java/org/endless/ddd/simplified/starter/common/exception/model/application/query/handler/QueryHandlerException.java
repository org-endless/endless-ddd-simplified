package org.endless.ddd.simplified.starter.common.exception.model.application.query.handler;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * QueryHandlerException
 * <p>
 * create 2024/09/29 11:17
 * <p>
 * update 2024/11/17 16:10
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class QueryHandlerException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "查询处理器异常";

    public QueryHandlerException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public QueryHandlerException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public QueryHandlerException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
