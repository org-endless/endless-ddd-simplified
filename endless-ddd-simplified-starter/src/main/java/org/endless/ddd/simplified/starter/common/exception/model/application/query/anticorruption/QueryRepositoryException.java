package org.endless.ddd.simplified.starter.common.exception.model.application.query.anticorruption;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * QueryRepositoryException
 * <p>
 * create 2024/10/28 09:03
 * <p>
 * update 2024/11/17 16:10
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class QueryRepositoryException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "查询仓储接口异常";

    public QueryRepositoryException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public QueryRepositoryException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public QueryRepositoryException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
