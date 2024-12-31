package org.endless.ddd.simplified.starter.common.exception.model.domain.entity;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * EntityValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:39
 *
 * @author Deng Haozhi
 * @see EntityException
 * @since 1.0.0
 */
public class EntityValidateException extends EntityException {

    private static final String DEFAULT_MESSAGE = "校验异常";

    public EntityValidateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public EntityValidateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }

    public EntityValidateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }
}
