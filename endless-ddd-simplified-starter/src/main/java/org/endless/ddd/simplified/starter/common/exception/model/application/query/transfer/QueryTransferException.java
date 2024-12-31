package org.endless.ddd.simplified.starter.common.exception.model.application.query.transfer;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * QueryTransferException
 * <p>
 * create 2024/09/29 11:19
 * <p>
 * update 2024/11/17 16:10
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class QueryTransferException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "查询传输对象异常";

    public QueryTransferException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public QueryTransferException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public QueryTransferException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
