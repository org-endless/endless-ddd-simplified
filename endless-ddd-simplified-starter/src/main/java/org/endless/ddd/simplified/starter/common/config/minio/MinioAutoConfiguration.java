package org.endless.ddd.simplified.starter.common.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.endless.ddd.simplified.starter.common.config.minio.okhttp.OkHttpClientConfiguration;
import org.endless.ddd.simplified.starter.common.config.minio.properties.MinioProperties;
import org.endless.ddd.simplified.starter.common.exception.config.MinioFailedException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * MinioAutoConfiguration
 * <p>
 * create 2024/08/09 10:09
 * <p>
 * update 2024/11/07 13:31
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(MinioClient.class)
@Import(OkHttpClientConfiguration.class)
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(prefix = "minio", name = {"endpoint", "accessKey", "secretKey"})
public class MinioAutoConfiguration {

    private final MinioProperties properties;

    private final OkHttpClient okHttpClient;

    public MinioAutoConfiguration(MinioProperties properties, OkHttpClient okHttpClient) {
        this.properties = properties;
        this.okHttpClient = okHttpClient;
    }

    @ConditionalOnMissingBean
    public @Bean MinioClient minioClient() {
        try {
            log.trace("[开始初始化MinIO客户端]");
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint())
                    .credentials(accessKey(), secretKey())
                    .httpClient(okHttpClient)  // 使用自定义的 OkHttpClient
                    .build();
            log.trace("[检查桶<{}>是否存在]", bucket());
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket()).build())) {
                log.trace("[开始创建桶<{}>]", bucket());
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucket())
                                .build());
                log.trace("[桶<{}>创建成功]", bucket());
            }
            log.trace("[MinIO客户端初始化成功]");
            return minioClient;
        } catch (Exception e) {
            throw new MinioFailedException("MinIO客户端初始化失败: " + e.getMessage(), e);
        }
    }

    public String endpoint() {
        return properties.getEndpoint();
    }

    public String accessKey() {
        return properties.getAccessKey();
    }

    public String secretKey() {
        return properties.getSecretKey();
    }

    public String bucket() {
        return properties.getBucket();
    }

    public int retentionDays() {
        return Integer.parseInt(properties.getRetentionDays());
    }
}
