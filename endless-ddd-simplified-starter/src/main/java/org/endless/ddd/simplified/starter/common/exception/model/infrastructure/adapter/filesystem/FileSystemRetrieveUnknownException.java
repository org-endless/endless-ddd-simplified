package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * FileSystemRetrieveException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2025/07/29 21:59
 *
 * @author Deng Haozhi
 * @see UnknownException
 * @since 1.0.0
 */
public class FileSystemRetrieveUnknownException extends UnknownException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DFS9003;

    public FileSystemRetrieveUnknownException() {
        super(ERROR_CODE);
    }

    public FileSystemRetrieveUnknownException(String message) {
        super(ERROR_CODE, message);
    }

    public FileSystemRetrieveUnknownException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public FileSystemRetrieveUnknownException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
