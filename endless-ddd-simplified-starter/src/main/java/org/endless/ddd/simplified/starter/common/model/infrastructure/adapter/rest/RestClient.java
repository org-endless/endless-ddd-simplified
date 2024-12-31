package org.endless.ddd.simplified.starter.common.model.infrastructure.adapter.rest;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * RestClient
 * <p>
 * create 2024/09/08 22:30
 * <p>
 * update 2024/09/08 22:30
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public interface RestClient {

    default <T> T get(RestTemplate restTemplate, String service, String scenes, Object... uriVariables) {
        // String url = UriComponentsBuilder.fromUriString()(service)
        //         .pathSegment(scenes)
        //         .buildAndExpand(paras)
        //         .toUriString();
        try {
            // return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<T>>() {
            // }).getBody();
            return null;
        } catch (RestClientException e) {
            // 可以在这里添加错误日志或者自定义处理逻辑
            throw new RuntimeException("Failed to fetch data from service: " + service, e);
        }
    }
}
