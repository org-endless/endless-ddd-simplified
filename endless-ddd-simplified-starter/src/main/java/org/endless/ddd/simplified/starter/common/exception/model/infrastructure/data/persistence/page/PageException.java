package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.page;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * PageException
 * <p>
 * create 2024/09/12 12:18
 * <p>
 * update 2024/09/12 12:18
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class PageException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "分页异常";

    public PageException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public PageException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public PageException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
