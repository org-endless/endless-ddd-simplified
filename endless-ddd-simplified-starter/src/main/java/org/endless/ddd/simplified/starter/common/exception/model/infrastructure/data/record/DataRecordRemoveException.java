package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.record;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * DataRecordRemoveException
 * <p>
 * create 2024/09/29 10:35
 * <p>
 * update 2024/11/15 14:31
 *
 * @author Deng Haozhi
 * @see DataRecordException
 * @since 1.0.0
 */
public class DataRecordRemoveException extends DataRecordException {

    private static final String DEFAULT_MESSAGE = "删除异常";

    public DataRecordRemoveException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public DataRecordRemoveException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public DataRecordRemoveException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
