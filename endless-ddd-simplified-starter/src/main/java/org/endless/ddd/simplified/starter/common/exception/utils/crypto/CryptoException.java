package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * CryptoException
 * <p>
 * create 2024/11/16 23:52
 * <p>
 * update 2024/11/16 23:53
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class CryptoException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "加解密处理异常";

    public CryptoException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public CryptoException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public CryptoException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
