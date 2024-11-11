package org.endless.domain.simplified.server.supporting.security.token.application.query.handler.impl;

import org.endless.domain.simplified.server.supporting.security.token.application.query.handler.*;
import org.endless.domain.simplified.server.supporting.security.token.application.query.repository.*;
import org.endless.domain.simplified.server.supporting.security.token.domain.anticorruption.*;
import org.endless.ddd.simplified.starter.common.exception.application.query.handler.*;
import lombok.extern.slf4j.Slf4j;

/**
 * TokenQueryHandlerImpl
 * <p>令牌领域查询处理器
 * <p>
 * create 2024/11/11 23:38
 * <p>
 * update 2024/11/11 23:38
 *
 * @author Deng Haozhi
 * @see TokenQueryHandler
 * @since 2.0.0
 */
@Slf4j
public class TokenQueryHandlerImpl implements TokenQueryHandler {

    /**
     * 令牌聚合查询仓储接口
     */
    private final TokenQueryRepository tokenQueryRepository;

    /**
     * 令牌信息实体查询仓储接口
     */
    private final TokenInfoQueryRepository tokenInfoQueryRepository;

    public TokenQueryHandlerImpl(TokenQueryRepository tokenQueryRepository, TokenInfoQueryRepository tokenInfoQueryRepository) {
        this.tokenQueryRepository = tokenQueryRepository;
        this.tokenInfoQueryRepository = tokenInfoQueryRepository;
    }
}
