package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperSaveFailedException
 * <p>
 * create 2024/09/12 12:12
 * <p>
 * update 2025/07/19 02:57
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 2.0.0
 */
public class MapperSaveFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0110;

    public MapperSaveFailedException() {
        super(ERROR_CODE);
    }

    public MapperSaveFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperSaveFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperSaveFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
