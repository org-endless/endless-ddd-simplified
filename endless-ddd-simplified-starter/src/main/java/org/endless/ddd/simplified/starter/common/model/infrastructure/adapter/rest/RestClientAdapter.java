package org.endless.ddd.simplified.starter.common.model.infrastructure.adapter.rest;

import com.alibaba.fastjson2.util.TypeUtils;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.manager.DrivenAdapterManagerException;
import org.endless.ddd.simplified.starter.common.model.common.Transfer;
import org.endless.ddd.simplified.starter.common.model.sidecar.rest.RestResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * RestClientAdapter
 * <p>
 * create 2024/09/08 22:30
 * <p>
 * update 2025/01/08 12:44
 *
 * @author Deng Haozhi
 * @see RestClient
 * @since 1.0.0
 */
public interface RestClientAdapter extends RestClient {

    default <S extends Transfer, R extends Transfer> Optional<R> post(RestTemplate restTemplate, String url, S request, Class<R> responseClass, HttpHeaders headers) {
        Optional.ofNullable(request)
                .map(Transfer::validate)
                .orElseThrow(() -> new DrivenAdapterManagerException("被动适配器请求参数不能为空"));
        return Optional.ofNullable(TypeUtils.cast(Optional.ofNullable(restTemplate.postForEntity(
                        url,
                        new HttpEntity<>(request, headers),
                        RestResponse.class).getBody())
                .orElseThrow(() -> new DrivenAdapterManagerException("被动适配器服务返回信息为空"))
                .validate(), responseClass));
    }

    default Optional<byte[]> get(RestTemplate restTemplate, String url, HttpHeaders headers) {
        ResponseEntity<byte[]> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    new URI(url),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<byte[]>() {
                    }
            );
        } catch (URISyntaxException e) {
            throw new DrivenAdapterManagerException(e);
        }
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new DrivenAdapterManagerException("被动适配器服务返回异常状态码: " + responseEntity.getStatusCode());
        }
        return Optional.ofNullable(responseEntity.getBody());
    }

    default <S extends Transfer, R extends Transfer> Optional<R> post(
            RestTemplate restTemplate,
            String url,
            S request,
            String requestPartName,
            InputStream fileStream,
            String fileName,
            String filePartName,
            Class<R> responseClass,
            HttpHeaders headers) {
        Optional.ofNullable(request)
                .map(Transfer::validate)
                .orElseThrow(() -> new DrivenAdapterManagerException("被动适配器请求参数不能为空"));

        HttpHeaders requestPartHeaders = new HttpHeaders();
        requestPartHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<S> requestPart = new HttpEntity<>(request, requestPartHeaders);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            byte[] data = new byte[4096];
            int nRead;
            while ((nRead = fileStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            throw new DrivenAdapterManagerException("读取文件流失败", e);
        }
        ByteArrayResource fileResource = new ByteArrayResource(buffer.toByteArray()) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };
        HttpHeaders filPartHeaders = new HttpHeaders();
        filPartHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<Resource> filePart = new HttpEntity<>(fileResource, filPartHeaders);

        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        multipartRequest.add(requestPartName, requestPart);
        multipartRequest.add(filePartName, filePart);

        HttpHeaders multipartRequestHeaders = new HttpHeaders();
        multipartRequestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (headers != null) {
            multipartRequestHeaders.putAll(headers);
        }

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multipartRequest, multipartRequestHeaders);
        RestResponse restResponse = Optional.ofNullable(
                restTemplate.postForEntity(url, httpEntity, RestResponse.class).getBody()
        ).orElseThrow(() -> new DrivenAdapterManagerException("被动适配器服务返回信息为空"));
        return Optional.ofNullable(TypeUtils.cast(restResponse.validate(), responseClass));
    }
}
