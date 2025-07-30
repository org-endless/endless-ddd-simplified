package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * FileSystemRemoveFailedException
 * <p>
 * create 2024/11/28 22:31
 * <p>
 * update 2025/07/29 22:00
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class FileSystemRemoveFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DFS0002;

    public FileSystemRemoveFailedException() {
        super(ERROR_CODE);
    }

    public FileSystemRemoveFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public FileSystemRemoveFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public FileSystemRemoveFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
