package org.endless.ddd.simplified.generator.common.rest;

import org.endless.ddd.simplified.generator.common.model.sidecar.rest.DDDSimplifiedGeneratorRestResponse;
import org.endless.ddd.simplified.starter.common.handler.exception.rest.AbstractRestAdapterExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * DDDSimplifiedGeneratorRestExceptionHandler
 * <p>
 * create 2024/11/02 06:53
 * <p>
 * update 2024/11/03 14:33
 *
 * @author Deng Haozhi
 * @see AbstractRestAdapterExceptionHandler
 * @since 1.0.0
 */
@RestControllerAdvice
public class DDDSimplifiedGeneratorRestAdapterExceptionHandler extends AbstractRestAdapterExceptionHandler {

    @Override
    public DDDSimplifiedGeneratorRestResponse response() {
        return DDDSimplifiedGeneratorRestResponse.builder().build();
    }
}
