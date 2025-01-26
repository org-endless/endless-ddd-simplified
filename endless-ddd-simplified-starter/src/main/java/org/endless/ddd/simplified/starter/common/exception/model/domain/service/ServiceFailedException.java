package org.endless.ddd.simplified.starter.common.exception.model.domain.service;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * ServiceFailedException
 * <p>
 * create 2024/09/29 11:33
 * <p>
 * update 2025/01/27 03:57
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class ServiceFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DSV0000;

    public ServiceFailedException() {
        super(ERROR_CODE);
    }

    public ServiceFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public ServiceFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public ServiceFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
