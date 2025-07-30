package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * FileSystemFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2025/07/29 22:00
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class FileSystemFailedException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DFS0000;

    public FileSystemFailedException() {
        super(ERROR_CODE);
    }

    public FileSystemFailedException(String message) {
        super(ERROR_CODE, message);
    }

    public FileSystemFailedException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public FileSystemFailedException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
