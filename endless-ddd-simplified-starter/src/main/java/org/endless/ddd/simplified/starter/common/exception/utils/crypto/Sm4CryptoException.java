package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * Sm4CryptoException
 * <p>
 * create 2024/11/18 19:43
 * <p>
 * update 2024/11/18 21:24
 *
 * @author Deng Haozhi
 * @see CryptoException
 * @since 2.0.0
 */
public class Sm4CryptoException extends CryptoException {

    private static final String DEFAULT_MESSAGE = "SM4加解密处理异常";

    public Sm4CryptoException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public Sm4CryptoException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public Sm4CryptoException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
