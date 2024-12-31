package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperSaveFailedException
 * <p>
 * create 2024/09/12 12:12
 * <p>
 * update 2024/11/17 16:23
 *
 * @author Deng Haozhi
 * @see MapperFailedException
 * @since 1.0.0
 */
public class MapperSaveFailedException extends MapperFailedException {

    private static final String DEFAULT_MESSAGE = "存入失败";

    public MapperSaveFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperSaveFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperSaveFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
