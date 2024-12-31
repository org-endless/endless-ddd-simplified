package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperRemoveFieldException
 * <p>
 * create 2024/11/06 09:37
 * <p>
 * update 2024/11/17 16:23
 *
 * @author Deng Haozhi
 * @see MapperFailedException
 * @since 1.0.0
 */
public class MapperRemoveFailedException extends MapperFailedException {

    private static final String DEFAULT_MESSAGE = "删除失败";

    public MapperRemoveFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperRemoveFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperRemoveFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
