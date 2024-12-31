package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperRemoveException
 * <p>
 * create 2024/09/29 16:04
 * <p>
 * update 2024/09/29 16:04
 *
 * @author Deng Haozhi
 * @see MapperException
 * @since 1.0.0
 */
public class MapperRemoveException extends MapperException {

    private static final String DEFAULT_MESSAGE = "删除异常";

    public MapperRemoveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperRemoveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperRemoveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
