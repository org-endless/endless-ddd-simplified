package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperFailedException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:11
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class MapperFailedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "MyBatis-Plus数据库操作失败";

    public MapperFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
