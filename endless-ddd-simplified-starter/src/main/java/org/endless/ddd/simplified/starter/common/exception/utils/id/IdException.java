package org.endless.ddd.simplified.starter.common.exception.utils.id;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * IdException
 * <p>
 * create 2024/12/25 11:17
 * <p>
 * update 2024/12/25 11:17
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class IdException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0000;

    public IdException() {
        super(ERROR_CODE);
    }

    public IdException(String message) {
        super(ERROR_CODE, message);
    }

    public IdException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public IdException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
