package org.endless.ddd.simplified.starter.common.exception.utils.model;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DecimalFormatException
 * <p>
 * create 2025/01/11 00:11
 * <p>
 * update 2025/01/11 00:25
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class DecimalEmptyException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0012;

    public DecimalEmptyException() {
        super(ERROR_CODE);
    }

    public DecimalEmptyException(String message) {
        super(ERROR_CODE, message);
    }

    public DecimalEmptyException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DecimalEmptyException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
