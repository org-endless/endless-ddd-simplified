package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperModifyFailedException
 * <p>
 * create 2024/09/29 15:58
 * <p>
 * update 2024/11/17 16:23
 *
 * @author Deng Haozhi
 * @see MapperFailedException
 * @since 1.0.0
 */
public class MapperModifyFailedException extends MapperFailedException {

    private static final String DEFAULT_MESSAGE = "修改失败";

    public MapperModifyFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperModifyFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperModifyFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
