package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * FileSystemRetrieveFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/17 16:16
 *
 * @author Deng Haozhi
 * @see FileSystemFailedException
 * @since 1.0.0
 */
public class FileSystemRetrieveFailedException extends FileSystemFailedException {

    private static final String DEFAULT_MESSAGE = "取回文件失败";

    public FileSystemRetrieveFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public FileSystemRetrieveFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public FileSystemRetrieveFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
