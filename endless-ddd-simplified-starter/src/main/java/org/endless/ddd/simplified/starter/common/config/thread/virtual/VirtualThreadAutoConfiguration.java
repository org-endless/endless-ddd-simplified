package org.endless.ddd.simplified.starter.common.config.thread.virtual;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * VirtualThreadAutoConfiguration
 * <p>
 * create 2025/07/19 03:28
 * <p>
 * update 2025/07/19 03:28
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
@EnableAsync
@AutoConfiguration
public class VirtualThreadAutoConfiguration {

    @Bean(name = "virtualThreadExecutor", destroyMethod = "shutdown")
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
