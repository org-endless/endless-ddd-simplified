package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.record;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * DataRecordException
 * <p>
 * create 2024/09/29 10:35
 * <p>
 * update 2024/09/29 10:36
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class DataRecordException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "数据库记录实体异常";

    public DataRecordException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public DataRecordException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public DataRecordException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
