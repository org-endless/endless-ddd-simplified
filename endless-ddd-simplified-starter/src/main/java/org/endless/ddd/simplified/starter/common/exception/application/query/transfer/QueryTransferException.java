package org.endless.ddd.simplified.starter.common.exception.application.query.transfer;

/**
 * QueryTransferException
 * <p>
 * create 2024/09/29 11:19
 * <p>
 * update 2024/09/29 11:19
 *
 * @author lanruirui
 * @since 2.0.0
 */
public class QueryTransferException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "查询传输对象异常";

    public QueryTransferException(String message) {
        super(message == null ? DEFAULT_MESSAGE + "：" : message);
    }

    public QueryTransferException(String message, Throwable throwable) {
        super(message == null ? DEFAULT_MESSAGE + "：" : message, throwable);
    }

    public QueryTransferException(Throwable throwable) {
        super(throwable);
    }
}
