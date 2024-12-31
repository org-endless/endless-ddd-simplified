package org.endless.ddd.simplified.starter.common.exception.model.domain.entity;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * EntityException
 * <p>
 * create 2024/09/29 11:29
 * <p>
 * update 2024/11/17 16:12
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class EntityException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "实体类异常";

    public EntityException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public EntityException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public EntityException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
