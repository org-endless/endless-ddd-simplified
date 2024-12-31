package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.data.record;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * DataRecordValidateException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/11/16 23:43
 *
 * @author Deng Haozhi
 * @see DataRecordException
 * @since 1.0.0
 */
public class DataRecordValidateException extends DataRecordException {

    private static final String DEFAULT_MESSAGE = "校验异常";

    public DataRecordValidateException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public DataRecordValidateException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public DataRecordValidateException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
