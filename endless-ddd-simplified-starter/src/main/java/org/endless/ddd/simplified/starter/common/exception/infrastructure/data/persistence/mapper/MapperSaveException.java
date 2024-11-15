package org.endless.ddd.simplified.starter.common.exception.infrastructure.data.persistence.mapper;

/**
 * MapperSaveFailedException
 * <p>
 * create 2024/09/12 12:12
 * <p>
 * update 2024/11/15 00:28
 *
 * @author Deng Haozhi
 * @see MapperException
 * @since 2.0.0
 */
public class MapperSaveException extends MapperException {

    private static final String DEFAULT_MESSAGE = " MyBatis-Plus 数据库存入异常";

    public MapperSaveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]<" + message + ">");
    }

    public MapperSaveException(Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }

    public MapperSaveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]<" + message + ">", throwable);
    }
}
