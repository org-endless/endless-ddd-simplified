package org.endless.ddd.simplified.starter.common.exception.utils.crypto;

import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * SM2KeyPairException
 * <p>
 * create 2024/11/16 04:04
 * <p>
 * update 2025/03/01 23:28
 *
 * @author Deng Haozhi
 * @see FailedException
 * @since 1.0.0
 */
public class PEMCertException extends FailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.UTL7000;

    public PEMCertException() {
        super(ERROR_CODE);
    }

    public PEMCertException(String message) {
        super(ERROR_CODE, message);
    }

    public PEMCertException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public PEMCertException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
