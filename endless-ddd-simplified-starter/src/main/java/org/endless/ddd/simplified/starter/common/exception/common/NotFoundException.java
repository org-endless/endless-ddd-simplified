package org.endless.ddd.simplified.starter.common.exception.common;

import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * FailedException
 * <p>
 * create 2024/12/05 02:08
 * <p>
 * update 2025/01/27 03:57
 *
 * @author Deng Haozhi
 * @see AbstractException
 * @since 1.0.0
 */
public class NotFoundException extends AbstractException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public NotFoundException(ErrorCode errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }
}
