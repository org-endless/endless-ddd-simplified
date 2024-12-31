package org.endless.ddd.simplified.starter.common.exception.model.facade.adapter;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * DrivingAdapterException
 * <p>
 * create 2024/09/29 11:35
 * <p>
 * update 2024/11/17 16:14
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class DrivingAdapterException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "主动适配器异常";

    public DrivingAdapterException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public DrivingAdapterException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public DrivingAdapterException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
