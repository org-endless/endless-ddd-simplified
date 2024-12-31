package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * Sm2VerifyException
 * <p>
 * create 2024/11/16 04:04
 * <p>
 * update 2024/11/16 23:54
 *
 * @author Deng Haozhi
 * @see CryptoException
 * @since 1.0.0
 */
public class Sm2VerifyException extends CryptoException {

    private static final String DEFAULT_MESSAGE = "使用SM2验签异常";

    public Sm2VerifyException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public Sm2VerifyException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }

    public Sm2VerifyException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }
}
