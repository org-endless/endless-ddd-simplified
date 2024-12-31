package org.endless.ddd.simplified.starter.common.exception.model.domain.service;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * ServiceException
 * <p>
 * create 2024/09/29 11:33
 * <p>
 * update 2024/11/17 16:13
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class ServiceException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "领域服务异常";

    public ServiceException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public ServiceException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public ServiceException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
