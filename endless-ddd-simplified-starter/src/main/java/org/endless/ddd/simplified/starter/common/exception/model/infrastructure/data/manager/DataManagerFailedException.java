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
public class DataManagerFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDM0000;

    public DataManagerFailedException() {
        super(ERROR_CODE);
    }

    public DataManagerFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public DataManagerFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DataManagerFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
