package org.endless.ddd.simplified.starter.common.exception.config;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * MinioFailedException
 * <p>
 * create 2024/11/07 12:07
 * <p>
 * update 2024/11/16 22:59
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class MinioFailedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "MinIO处理失败";

    public MinioFailedException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public MinioFailedException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public MinioFailedException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}