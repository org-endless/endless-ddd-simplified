package org.endless.ddd.simplified.starter.common.exception.utils.model;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DecimalFormatException
 * <p>
 * create 2025/01/11 00:11
 * <p>
 * update 2025/01/11 00:11
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class DecimalFormatException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL0013;

    public DecimalFormatException() {
        super(ERROR_CODE);
    }

    public DecimalFormatException(String message) {
        super(ERROR_CODE, message);
    }

    public DecimalFormatException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DecimalFormatException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
