package org.endless.ddd.simplified.starter.common.exception.model.domain.anticorruption;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * RepositoryException
 * <p>
 * create 2024/09/29 11:29
 * <p>
 * update 2024/11/17 16:11
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class RepositoryException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "仓储接口异常";

    public RepositoryException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public RepositoryException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public RepositoryException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
