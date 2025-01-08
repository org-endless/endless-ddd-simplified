package org.endless.ddd.simplified.starter.common.model.sidecar.rest;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * AbstractRestResponse
 * <p>Rest主动适配器响应信息模版
 * <p>Service Mesh Sidecar
 * <p>处理外部系统Rest通讯请求的响应信息
 * <p>
 * create 2024/09/06 13:45
 * <p>
 * update 2024/09/08 20:20
 *
 * @author Deng Haozhi
 * @see RestResponse
 * @since 1.0.0
 */
@Getter
@ToString
@SuperBuilder
public abstract class AbstractRestResponse implements RestResponse {

    private final String status;

    private final String errorCode;

    private final String message;

    private final Object data;


    protected AbstractRestResponse(AbstractRestResponseBuilder<?, ?> builder) {
        this.status = builder.status;
        this.errorCode = builder.errorCode;
        this.message = builder.message;
        this.data = builder.data;

    }
}
