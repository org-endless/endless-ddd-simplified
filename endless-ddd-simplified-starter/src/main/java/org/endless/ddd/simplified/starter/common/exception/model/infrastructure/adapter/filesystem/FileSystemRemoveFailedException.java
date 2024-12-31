package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * FileSystemRemoveFailedException
 * <p>
 * create 2024/11/28 22:31
 * <p>
 * update 2024/11/28 22:32
 *
 * @author Deng Haozhi
 * @see FileSystemFailedException
 * @since 2.0.0
 */
public class FileSystemRemoveFailedException extends FileSystemFailedException {

    private static final String DEFAULT_MESSAGE = "删除文件失败";

    public FileSystemRemoveFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public FileSystemRemoveFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public FileSystemRemoveFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
