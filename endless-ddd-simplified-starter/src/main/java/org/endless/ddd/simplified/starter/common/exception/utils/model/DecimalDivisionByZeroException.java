package org.endless.ddd.simplified.starter.common.exception.utils.model;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DecimalFormatException
 * <p>
 * create 2025/01/11 00:11
 * <p>
 * update 2025/01/11 00:28
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class DecimalDivisionByZeroException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0011;

    public DecimalDivisionByZeroException() {
        super(ERROR_CODE);
    }

    public DecimalDivisionByZeroException(String message) {
        super(ERROR_CODE, message);
    }

    public DecimalDivisionByZeroException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DecimalDivisionByZeroException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
