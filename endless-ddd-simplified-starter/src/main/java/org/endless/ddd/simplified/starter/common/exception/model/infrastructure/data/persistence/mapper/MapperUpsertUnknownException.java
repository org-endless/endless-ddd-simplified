package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperUpsertUnknownException
 * <p>
 * create 2024/09/29 16:01
 * <p>
 * update 2025/07/19 02:58
 *
 * @author Deng Haozhi
 * @see UnknownException
 * @since 2.0.0
 */
public class MapperUpsertUnknownException extends UnknownException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0141;

    public MapperUpsertUnknownException() {
        super(ERROR_CODE);
    }

    public MapperUpsertUnknownException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperUpsertUnknownException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperUpsertUnknownException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
