package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.persistence.mapper;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MapperUpsertException
 * <p>
 * create 2024/09/29 16:01
 * <p>
 * update 2024/09/29 16:02
 *
 * @author Deng Haozhi
 * @see MapperException
 * @since 1.0.0
 */
public class MapperUpsertException extends MapperException {

    private static final String DEFAULT_MESSAGE = "新增或修改异常";

    public MapperUpsertException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MapperUpsertException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MapperUpsertException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
