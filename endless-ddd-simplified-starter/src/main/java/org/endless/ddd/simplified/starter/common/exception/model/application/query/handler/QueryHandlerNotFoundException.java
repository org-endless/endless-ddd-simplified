package org.endless.ddd.simplified.starter.common.exception.model.application.query.handler;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * QueryHandlerNotFoundException
 * <p>
 * create 2024/11/26 22:03
 * <p>
 * update 2024/12/31 23:33
 *
 * @author Deng Haozhi
 * @see QueryHandlerException
 * @since 2.0.0
 */
public class QueryHandlerNotFoundException extends QueryHandlerException {

    private static final String DEFAULT_MESSAGE = "未找到相关数据";

    public QueryHandlerNotFoundException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public QueryHandlerNotFoundException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public QueryHandlerNotFoundException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
