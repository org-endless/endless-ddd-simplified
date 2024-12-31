package org.endless.domain.simplified.server.common.model.infrastructure.adapter.rest;

import org.endless.ddd.simplified.starter.common.model.infrastructure.adapter.rest.RestClient;
import org.endless.domain.simplified.server.common.model.infrastructure.adapter.transfer.DomainSimplifiedServerDrivenTransfer;

/**
 * DomainSimplifiedServerDrivenRestAdapter
 * <p>
 * create 2024/09/05 09:40
 * <p>
 * update 2024/09/10 12:15
 *
 * @author Deng Haozhi
 * @see RestClient
 * @since 1.0.0
 */
public interface DomainSimplifiedServerRestClient<T extends DomainSimplifiedServerDrivenTransfer>
        extends RestClient {

}
