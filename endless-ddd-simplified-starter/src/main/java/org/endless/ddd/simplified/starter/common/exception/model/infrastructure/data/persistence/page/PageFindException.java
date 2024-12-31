package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.page;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * PageFindException
 * <p>
 * create 2024/09/12 12:19
 * <p>
 * update 2024/09/12 12:20
 *
 * @author Deng Haozhi
 * @see PageException
 * @since 1.0.0
 */
public class PageFindException extends PageException {

    private static final String DEFAULT_MESSAGE = "查询异常";

    public PageFindException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public PageFindException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public PageFindException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
