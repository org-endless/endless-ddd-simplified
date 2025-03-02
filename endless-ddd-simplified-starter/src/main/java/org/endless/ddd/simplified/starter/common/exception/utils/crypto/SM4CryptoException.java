package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * SM4CryptoException
 * <p>
 * create 2024/11/18 19:43
 * <p>
 * update 2024/11/18 21:24
 *
 * @author Deng Haozhi
 * @see CryptoException
 * @since 1.0.0
 */
public class SM4CryptoException extends CryptoException {

    private static final String DEFAULT_MESSAGE = "SM4加解密处理异常";

    public SM4CryptoException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public SM4CryptoException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public SM4CryptoException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
