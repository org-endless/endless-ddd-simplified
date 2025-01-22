package org.endless.ddd.simplified.starter.common.exception.security;

import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * OrganizationException
 * <p>
 * create 2025/01/22 18:27
 * <p>
 * update 2025/01/22 18:27
 *
 * @author Deng Haozhi
 * @see SecurityFailedException
 * @since 1.0.0
 */
public class OrganizationException extends SecurityFailedException {

    private static final ErrorCode ERROR_CODE = ErrorCode.SEC0050;

    public OrganizationException() {
        super(ERROR_CODE);
    }

    public OrganizationException(String message) {
        super(ERROR_CODE, message);
    }

    public OrganizationException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public OrganizationException(String message, Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
