package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperSaveFailedException
 * <p>
 * create 2024/09/12 12:12
 * <p>
 * update 2024/11/15 00:28
 *
 * @author Deng Haozhi
 * @see MapperException
 * @since 1.0.0
 */
public class MapperSaveException extends MapperException {

    private static final String DEFAULT_MESSAGE = "存入异常";

    public MapperSaveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperSaveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperSaveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
