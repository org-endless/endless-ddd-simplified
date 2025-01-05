package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * Sm3CryptoException
 * <p>
 * create 2024/11/18 21:23
 * <p>
 * update 2024/11/18 21:24
 *
 * @author Deng Haozhi
 * @see CryptoException
 * @since 1.0.0
 */
public class Sm3CryptoException extends CryptoException {

    private static final String DEFAULT_MESSAGE = "SM3哈希处理异常";

    public Sm3CryptoException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public Sm3CryptoException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public Sm3CryptoException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
