package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * RestException
 * <p>
 * create 2024/09/08 19:38
 * <p>
 * update 2024/09/08 19:41
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class RestException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Rest处理异常";

    public RestException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public RestException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public RestException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
