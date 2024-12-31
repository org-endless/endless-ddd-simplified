package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * RestBadRequestException
 * <p>
 * create 2024/09/08 21:40
 * <p>
 * update 2024/11/17 16:25
 *
 * @author Deng Haozhi
 * @see RestException
 * @since 1.0.0
 */
public class RestBadRequestException extends RestException {

    private static final String DEFAULT_MESSAGE = "请求无法处理";

    public RestBadRequestException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public RestBadRequestException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public RestBadRequestException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
