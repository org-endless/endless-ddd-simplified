package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.exception.common.UnknownException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * FileSystemStoreException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2025/07/29 22:02
 *
 * @author Deng Haozhi
 * @see UnknownException
 * @since 1.0.0
 */
public class FileSystemStoreUnknownException extends UnknownException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DFS9001;

    public FileSystemStoreUnknownException() {
        super(ERROR_CODE);
    }

    public FileSystemStoreUnknownException(String message) {
        super(ERROR_CODE, message);
    }

    public FileSystemStoreUnknownException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public FileSystemStoreUnknownException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);

    }
}
