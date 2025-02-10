package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.manager;

import org.endless.ddd.simplified.starter.common.exception.common.NotFoundException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * DataManagerFindFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/17 16:20
 *
 * @author Deng Haozhi
 * @see DataManagerFailedException
 * @since 1.0.0
 */
public class DataManagerNotFoundException extends NotFoundException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDM0011;

    public DataManagerNotFoundException() {
        super(ERROR_CODE);
    }

    public DataManagerNotFoundException(String message) {
        super(ERROR_CODE, message);
    }

    public DataManagerNotFoundException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public DataManagerNotFoundException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
