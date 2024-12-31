package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperModifyException
 * <p>
 * create 2024/09/29 15:58
 * <p>
 * update 2024/09/29 15:58
 *
 * @author Deng Haozhi
 * @see MapperException
 * @since 1.0.0
 */
public class MapperModifyException extends MapperException {

    private static final String DEFAULT_MESSAGE = "修改异常";

    public MapperModifyException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperModifyException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperModifyException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
