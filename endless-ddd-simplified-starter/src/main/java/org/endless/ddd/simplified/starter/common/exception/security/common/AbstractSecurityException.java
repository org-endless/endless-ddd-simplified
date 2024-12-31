package org.endless.ddd.simplified.starter.common.exception.security.common;

import lombok.Getter;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;
import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;
import org.springframework.security.core.AuthenticationException;

/**
 * AbstractSecurityException
 * <p>
 * create 2024/12/05 18:09
 * <p>
 * update 2024/12/05 18:09
 *
 * @author Deng Haozhi
 * @see AuthenticationException
 * @since 2.0.0
 */
@Getter
public class AbstractSecurityException extends AuthenticationException {

    private final ErrorCode errorCode;

    public AbstractSecurityException(ErrorCode errorCode) {
        super("[" + errorCode.getDescription() + "]");
        this.errorCode = errorCode;
    }

    public AbstractSecurityException(ErrorCode errorCode, String message) {
        super(StringTools.addBrackets(message));
        this.errorCode = errorCode;
    }

    public AbstractSecurityException(ErrorCode errorCode, Throwable throwable) {
        super(StringTools.addBrackets(throwable.getMessage()), throwable);
        this.errorCode = errorCode;
    }

    public AbstractSecurityException(ErrorCode errorCode, String message, Throwable throwable) {
        super(StringTools.addBrackets(message), throwable);
        this.errorCode = errorCode;
    }
}
