package org.endless.ddd.simplified.starter.common.config.thread.mapper;

import org.endless.ddd.simplified.starter.common.config.thread.model.AbstractThreadConfiguration;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * MapperThreadConfiguration
 * <p>
 * create 2024/11/14 23:03
 * <p>
 * update 2024/11/17 16:29
 *
 * @author Deng Haozhi
 * @see AbstractThreadConfiguration
 * @since 1.0.0
 */
@EnableAsync
@EnableConfigurationProperties(MapperThreadProperties.class)
public class MapperThreadConfiguration extends AbstractThreadConfiguration {

    public MapperThreadConfiguration(MapperThreadProperties threadProperties) {
        super(threadProperties);
    }

    @Override
    @Bean("mapperTaskExecutor")
    public Executor getAsyncExecutor() {
        return super.getAsyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return super.getAsyncUncaughtExceptionHandler();
    }
}
