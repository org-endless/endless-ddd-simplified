package org.endless.ddd.simplified.starter.common.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * OpenApiAutoConfiguration
 * <p>
 * create 2024/11/22 15:47
 * <p>
 * update 2024/11/22 15:47
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@AutoConfiguration
public class OpenApiAutoConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ATP REST API")
                        .description("自动化测试平台API")
                        .version("2.0.0"))
                .components(new Components()
                        .addSecuritySchemes("jwt-token", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("JWT Token认证")))
                .addSecurityItem(new SecurityRequirement().addList("jwt-token"));
    }
}
