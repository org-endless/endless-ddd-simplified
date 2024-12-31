package org.endless.ddd.simplified.starter.common.exception.common;

import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * UnknownException
 * <p>
 * create 2024/12/05 02:05
 * <p>
 * update 2024/12/05 02:07
 *
 * @author Deng Haozhi
 * @see AbstractException
 * @since 2.0.0
 */
public class UnknownException extends AbstractException {

    public UnknownException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnknownException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UnknownException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public UnknownException(ErrorCode errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }
}
