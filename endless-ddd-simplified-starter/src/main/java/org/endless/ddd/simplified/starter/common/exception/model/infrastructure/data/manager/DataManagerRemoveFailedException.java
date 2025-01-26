package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DataManagerRemoveFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/17 16:20
 *
 * @author Deng Haozhi
 * @see DataManagerFailedException
 * @since 1.0.0
 */
public class DataManagerRemoveFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDM0002;

    public DataManagerRemoveFailedException() {
        super(ERROR_CODE);
    }

    public DataManagerRemoveFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public DataManagerRemoveFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DataManagerRemoveFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
