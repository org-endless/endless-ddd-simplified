package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * RestErrorException
 * <p>
 * create 2024/09/08 19:49
 * <p>
 * update 2024/11/17 16:25
 *
 * @author Deng Haozhi
 * @see RestException
 * @since 1.0.0
 */
public class RestErrorException extends RestException {

    private static final String DEFAULT_MESSAGE = "请求失败";

    public RestErrorException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public RestErrorException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public RestErrorException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
