package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.exception.common.NotFoundException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RestNotFoundException
 * <p>
 * create 2024/09/08 21:47
 * <p>
 * update 2025/02/11 00:19
 *
 * @author Deng Haozhi
 * @see NotFoundException
 * @since 1.0.0
 */
public class RestNotFoundException extends NotFoundException {

    private static final ErrorCode ERROR_CODE = ErrorCode.RES0200;

    public RestNotFoundException() {
        super(ERROR_CODE);
    }

    public RestNotFoundException(String message) {
        super(ERROR_CODE, message);
    }

    public RestNotFoundException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RestNotFoundException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
