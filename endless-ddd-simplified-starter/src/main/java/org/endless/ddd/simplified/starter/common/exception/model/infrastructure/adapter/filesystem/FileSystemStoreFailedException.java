package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * FileSystemStoreFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2025/07/29 22:01
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class FileSystemStoreFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DFS0001;

    public FileSystemStoreFailedException() {
        super(ERROR_CODE);
    }

    public FileSystemStoreFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public FileSystemStoreFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public FileSystemStoreFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
