package org.endless.ddd.simplified.starter.common.exception.model.application.query.transfer;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * QueryReqTransferValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:37
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class QueryReqTransferValidateException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DTS0201;

    public QueryReqTransferValidateException() {
        super(ERROR_CODE);
    }

    public QueryReqTransferValidateException(String message) {
        super(ERROR_CODE, message);
    }

    public QueryReqTransferValidateException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public QueryReqTransferValidateException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
