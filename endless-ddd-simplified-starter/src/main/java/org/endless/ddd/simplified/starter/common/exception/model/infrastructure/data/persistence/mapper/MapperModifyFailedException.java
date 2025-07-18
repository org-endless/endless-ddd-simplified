package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * MapperModifyFailedException
 * <p>
 * create 2024/09/29 15:58
 * <p>
 * update 2025/07/19 02:56
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class MapperModifyFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DDP0130;

    public MapperModifyFailedException() {
        super(ERROR_CODE);
    }

    public MapperModifyFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public MapperModifyFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public MapperModifyFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
