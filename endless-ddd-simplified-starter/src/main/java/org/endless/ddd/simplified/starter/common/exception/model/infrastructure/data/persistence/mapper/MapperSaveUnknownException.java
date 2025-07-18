package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperSaveUnknownException
 * <p>
 * create 2024/09/12 12:12
 * <p>
 * update 2025/07/19 02:58
 *
 * @author Deng Haozhi
 * @see UnknownException
 * @since 2.0.0
 */
public class MapperSaveUnknownException extends UnknownException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0111;

    public MapperSaveUnknownException() {
        super(ERROR_CODE);
    }

    public MapperSaveUnknownException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperSaveUnknownException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperSaveUnknownException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
