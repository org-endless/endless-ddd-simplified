package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DataManagerFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2025/01/26 18:29
 *
 * @author Deng Haozhi
 * @see UnknownException
 * @since 1.0.0
 */
public class DataManagerUnKnownException extends UnknownException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDM9000;

    public DataManagerUnKnownException() {
        super(ERROR_CODE);
    }

    public DataManagerUnKnownException(String message) {
        super(ERROR_CODE, message);
    }

    public DataManagerUnKnownException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DataManagerUnKnownException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
