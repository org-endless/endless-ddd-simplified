package org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.transfer;

import org.endless.ddd.simplified.starter.common.utils.model.string.StringTools;

/**
 * DrivenTransferException
 * <p>
 * create 2024/09/29 10:55
 * <p>
 * update 2024/09/29 11:08
 *
 * @author Deng Haozhi
 * @see RuntimeException
 * @since 1.0.0
 */
public class DrivenTransferException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "被动适配器传输对象异常";

    public DrivenTransferException(String message) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message));
    }

    public DrivenTransferException(Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(throwable.getMessage()), throwable);
    }

    public DrivenTransferException(String message, Throwable throwable) {
        super("[" + DEFAULT_MESSAGE + "]" + StringTools.addBrackets(message), throwable);
    }
}
