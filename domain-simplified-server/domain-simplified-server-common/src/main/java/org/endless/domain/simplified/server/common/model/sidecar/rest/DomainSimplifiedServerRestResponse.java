package org.endless.domain.simplified.server.common.model.sidecar.rest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.endless.ddd.simplified.starter.common.model.sidecar.rest.AbstractRestResponse;

/**
 * DomainSimplifiedServerRestResponse
 * <p>
 * create 2024/09/06 15:28
 * <p>
 * update 2024/09/06 15:29
 *
 * @author Deng Haozhi
 * @see AbstractRestResponse
 * @since 1.0.0
 */
@Getter
@SuperBuilder
public class DomainSimplifiedServerRestResponse extends AbstractRestResponse {


    private static final String SERVICE_DESCRIPTION = "简易DDD框架-服务端";

    @Override
    public DomainSimplifiedServerRestResponse createInstance(String status, String errorCode, String message, Object data) {
        return DomainSimplifiedServerRestResponse.builder().status(status).errorCode(errorCode).message(message).data(data).build();
    }

    @Override
    public String getServiceDescription() {
        return SERVICE_DESCRIPTION;
    }
}
