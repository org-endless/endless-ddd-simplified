package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * RestResponseException
 * <p>
 * create 2025/03/19 16:53
 * <p>
 * update 2025/03/19 16:53
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class RestResponseException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DRT0100;

    public RestResponseException() {
        super(ERROR_CODE);
    }

    public RestResponseException(String message) {
        super(ERROR_CODE, message);
    }

    public RestResponseException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public RestResponseException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
