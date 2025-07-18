package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2025/07/01 16:52
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class MapperFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0100;

    public MapperFailedException() {
        super(ERROR_CODE);
    }

    public MapperFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
