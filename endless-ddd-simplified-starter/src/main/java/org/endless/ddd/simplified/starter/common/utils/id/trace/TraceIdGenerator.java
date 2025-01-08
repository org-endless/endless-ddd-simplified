package org.endless.ddd.simplified.starter.common.utils.id.trace;

import com.yomahub.tlog.id.TLogIdGenerator;
import org.endless.ddd.simplified.starter.common.config.utils.id.IdGenerator;

/**
 * TraceIdGenerator
 * <p>
 * create 2024/11/25 22:40
 * <p>
 * update 2024/11/25 22:42
 *
 * @author Deng Haozhi
 * @see TLogIdGenerator
 * @since 1.0.0
 */
public class TraceIdGenerator extends TLogIdGenerator {

    @Override
    public String generateTraceId() {
        return IdGenerator.of();
    }
}
