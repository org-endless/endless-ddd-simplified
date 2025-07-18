package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperRemoveFieldException
 * <p>
 * create 2024/11/06 09:37
 * <p>
 * update 2025/07/19 02:57
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class MapperRemoveFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0120;

    public MapperRemoveFailedException() {
        super(ERROR_CODE);
    }

    public MapperRemoveFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperRemoveFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperRemoveFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
