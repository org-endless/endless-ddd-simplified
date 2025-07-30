package org.endless.ddd.simplified.generator.common.model.sidecar.rest;

import org.endless.ddd.simplified.starter.common.model.sidecar.rest.RestController;

/**
 * DDDSimplifiedGeneratorRestController
 * <p>
 * create 2024/09/06 15:29
 * <p>
 * update 2024/09/11 00:59
 *
 * @author Deng Haozhi
 * @see RestController
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorRestController extends RestController {

    default DDDSimplifiedGeneratorRestResponse response() {
        return DDDSimplifiedGeneratorRestResponse.builder().build();
    }
}
