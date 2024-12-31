package org.endless.ddd.simplified.starter.common.exception.model.application.query.transfer;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * QueryTransferValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:37
 *
 * @author Deng Haozhi
 * @see QueryTransferException
 * @since 1.0.0
 */
public class QueryTransferValidateException extends QueryTransferException {

    private static final String DEFAULT_MESSAGE = "校验异常";

    public QueryTransferValidateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public QueryTransferValidateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }

    public QueryTransferValidateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }
}
