package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperBulkException
 * <p>
 * create 2025/07/01 16:34
 * <p>
 * update 2025/07/01 16:52
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class MapperBulkException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0160;

    public MapperBulkException() {
        super(ERROR_CODE);
    }

    public MapperBulkException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperBulkException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperBulkException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
