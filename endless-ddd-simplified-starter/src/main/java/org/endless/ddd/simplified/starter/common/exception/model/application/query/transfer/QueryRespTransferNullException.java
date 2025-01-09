package org.endless.ddd.simplified.starter.common.exception.model.application.query.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * QueryReqTransferNullException
 * <p>
 * create 2024/09/29 11:19
 * <p>
 * update 2025/01/09 18:01
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class QueryRespTransferNullException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTR0200;

    public QueryRespTransferNullException() {
        super(ERROR_CODE);
    }

    public QueryRespTransferNullException(String message) {
        super(ERROR_CODE, message);
    }

    public QueryRespTransferNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public QueryRespTransferNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
