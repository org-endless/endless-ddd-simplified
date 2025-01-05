package org.endless.ddd.simplified.starter.common.exception.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;
import org.springframework.security.core.AuthenticationException;

/**
 * RestUnauthorizedException
 * <p>
 * create 2024/09/08 19:52
 * <p>
 * update 2024/12/05 15:18
 *
 * @author Deng Haozhi
 * @see AuthenticationException
 * @since 2.0.0
 */
public class RestUnauthorizedException extends AuthenticationException {

    private static final String DEFAULT_MESSAGE = "身份认证失败";

    public RestUnauthorizedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public RestUnauthorizedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public RestUnauthorizedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
