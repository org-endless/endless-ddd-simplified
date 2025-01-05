package org.endless.ddd.simplified.starter.common.config.endless.constant;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * EndlessConstant
 * <p>
 * create 2024/11/19 01:58
 * <p>
 * update 2024/11/19 01:58
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class EndlessConstant {

    public static final Set<String> SENSITIVE_KEYS = Stream
            .of("password", "passcode", "pwd", "secret", "key", "salt", "token", "verification")
            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
}
