package org.endless.ddd.simplified.starter.common.exception.security;

import lombok.Getter;
import org.endless.ddd.simplified.starter.common.exception.security.common.SecurityFailedException;
import org.endless.ddd.simplified.starter.common.handler.result.type.ErrorCode;

/**
 * InitialPasswordException
 * <p>
 * create 2024/12/05 17:58
 * <p>
 * update 2024/12/05 17:58
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
public class InitialPasswordException extends SecurityFailedException {

    private final String userId;

    public InitialPasswordException(String userId) {
        super(ErrorCode.SEC0000);
        this.userId = userId;
    }

    public InitialPasswordException(String message, String userId) {
        super(ErrorCode.SEC0000, message);
        this.userId = userId;
    }

    public InitialPasswordException(Throwable throwable, String userId) {
        super(ErrorCode.SEC0000, throwable);
        this.userId = userId;
    }

    public InitialPasswordException(String message, Throwable throwable, String userId) {
        super(ErrorCode.SEC0000, message, throwable);
        this.userId = userId;
    }
}
