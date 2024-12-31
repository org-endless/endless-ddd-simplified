package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperFindException
 * <p>
 * create 2024/09/29 15:58
 * <p>
 * update 2024/09/29 15:58
 *
 * @author Deng Haozhi
 * @see MapperException
 * @since 1.0.0
 */
public class MapperFindException extends MapperException {

    private static final String DEFAULT_MESSAGE = "查询异常";

    public MapperFindException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperFindException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperFindException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
