package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperUpsertException
 * <p>
 * create 2024/09/29 16:01
 * <p>
 * update 2024/11/17 16:22
 *
 * @author Deng Haozhi
 * @see MapperFailedException
 * @since 1.0.0
 */
public class MapperUpsertFailedException extends MapperFailedException {

    private static final String DEFAULT_MESSAGE = "新增或更新失败";

    public MapperUpsertFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperUpsertFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperUpsertFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
