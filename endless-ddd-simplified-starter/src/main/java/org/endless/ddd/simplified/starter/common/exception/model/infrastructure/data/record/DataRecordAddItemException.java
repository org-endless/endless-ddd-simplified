package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.record;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * DataRecordAddItemException
 * <p>
 * create 2024/10/29 14:58
 * <p>
 * update 2024/11/17 16:24
 *
 * @author Deng Haozhi
 * @see DataRecordException
 * @since 1.0.0
 */
public class DataRecordAddItemException extends DataRecordException {

    private static final String DEFAULT_MESSAGE = "增加子实体异常";

    public DataRecordAddItemException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public DataRecordAddItemException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public DataRecordAddItemException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
