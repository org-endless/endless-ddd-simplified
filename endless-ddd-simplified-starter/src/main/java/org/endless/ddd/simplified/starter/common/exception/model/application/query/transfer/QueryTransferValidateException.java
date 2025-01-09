package org.endless.ddd.simplified.starter.common.exception.model.application.query.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * QueryTransferValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:37
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class QueryTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTO0201;

    public QueryTransferValidateException() {
        super(ERROR_CODE);
    }

    public QueryTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public QueryTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public QueryTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
