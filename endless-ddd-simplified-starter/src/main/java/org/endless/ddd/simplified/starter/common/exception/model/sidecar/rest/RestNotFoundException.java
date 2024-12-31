package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * RestNotFoundException
 * <p>
 * create 2024/09/08 21:47
 * <p>
 * update 2024/11/17 16:25
 *
 * @author Deng Haozhi
 * @see RestException
 * @since 1.0.0
 */
public class RestNotFoundException extends RestException {

    private static final String DEFAULT_MESSAGE = "请求的资源不存在";

    public RestNotFoundException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public RestNotFoundException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public RestNotFoundException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
