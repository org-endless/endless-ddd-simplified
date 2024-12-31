package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * FileSystemRetrieveException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/17 16:15
 *
 * @author Deng Haozhi
 * @see FileSystemException
 * @since 1.0.0
 */
public class FileSystemRetrieveException extends FileSystemException {

    private static final String DEFAULT_MESSAGE = "取回文件异常";

    public FileSystemRetrieveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public FileSystemRetrieveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public FileSystemRetrieveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
