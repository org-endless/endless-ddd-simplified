package org.endless.ddd.simplified.starter.common.exception.config;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MinioException
 * <p>
 * create 2024/11/07 12:07
 * <p>
 * update 2024/11/17 16:06
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class MinioException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "MinIO异常";

    public MinioException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MinioException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MinioException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
