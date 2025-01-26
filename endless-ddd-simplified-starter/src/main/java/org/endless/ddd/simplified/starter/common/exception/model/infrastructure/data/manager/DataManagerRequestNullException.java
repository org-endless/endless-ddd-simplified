package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DataManagerFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/09/29 11:08
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class DataManagerRequestNullException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDM0100;

    public DataManagerRequestNullException() {
        super(ERROR_CODE);
    }

    public DataManagerRequestNullException(String message) {
        super(ERROR_CODE, message);
    }

    public DataManagerRequestNullException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DataManagerRequestNullException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
