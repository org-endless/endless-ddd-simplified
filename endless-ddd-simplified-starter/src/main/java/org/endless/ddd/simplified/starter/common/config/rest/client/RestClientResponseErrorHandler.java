package org.endless.ddd.simplified.starter.common.config.rest.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.ResponseErrorHandler;

import java.net.URI;

/**
 * RestClientResponseErrorHandler
 * <p>
 * create 2025/03/19 18:31
 * <p>
 * update 2025/03/19 18:31
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class RestClientResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(@NonNull ClientHttpResponse response) {
        return false;
    }

    @Override
    public void handleError(@NonNull URI url, @NonNull HttpMethod method, @NonNull ClientHttpResponse response) {
    }
}
