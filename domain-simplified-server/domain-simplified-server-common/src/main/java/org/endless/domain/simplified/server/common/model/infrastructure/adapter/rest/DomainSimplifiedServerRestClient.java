package org.endless.domain.simplified.server.common.model.infrastructure.adapter.rest;

import org.endless.ddd.simplified.starter.common.model.infrastructure.adapter.rest.RestClientAdapter;
import org.endless.domain.simplified.server.common.model.infrastructure.adapter.transfer.DomainSimplifiedServerDrivenTransfer;

/**
 * DomainSimplifiedServerDrivenRestAdapter
 * <p>
 * create 2024/09/05 09:40
 * <p>
 * update 2025/01/08 12:45
 *
 * @author Deng Haozhi
 * @see RestClientAdapter
 * @since 1.0.0
 */
public interface DomainSimplifiedServerRestClient<T extends DomainSimplifiedServerDrivenTransfer>
        extends RestClientAdapter {

}
