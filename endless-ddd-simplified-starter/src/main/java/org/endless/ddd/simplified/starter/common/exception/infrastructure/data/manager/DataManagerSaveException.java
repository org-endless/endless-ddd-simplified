package org.endless.ddd.simplified.starter.common.exception.infrastructure.data.manager;

/**
 * MapperException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/09/29 11:08
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 2.0.0
 */
public class DataManagerSaveException extends DataManagerException {

    private static final String DEFAULT_MESSAGE = "数据管理器写入异常";

    public DataManagerSaveException(String message) {
        super(message == null ? DEFAULT_MESSAGE + "：" : message);
    }

    public DataManagerSaveException(Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }

    public DataManagerSaveException(String message, Throwable throwable) {
        super(message == null ? DEFAULT_MESSAGE + "：" : message, throwable);
    }
}
