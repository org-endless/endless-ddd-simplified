package org.endless.ddd.simplified.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * DDDSimplifiedGeneratorApplication
 * <p>
 * create 2024/10/16 20:26
 * <p>
 * update 2024/10/16 20:26
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
public class DDDSimplifiedGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DDDSimplifiedGeneratorApplication.class, args);
    }
}
