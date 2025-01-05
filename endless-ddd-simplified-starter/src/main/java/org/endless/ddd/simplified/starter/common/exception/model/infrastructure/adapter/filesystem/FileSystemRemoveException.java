package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * FileSystemRemoveException
 * <p>
 * create 2024/11/28 22:30
 * <p>
 * update 2024/11/28 22:31
 *
 * @author Deng Haozhi
 * @see FileSystemException
 * @since 1.0.0
 */
public class FileSystemRemoveException extends FileSystemException {

    private static final String DEFAULT_MESSAGE = "删除文件异常";

    public FileSystemRemoveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public FileSystemRemoveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public FileSystemRemoveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
