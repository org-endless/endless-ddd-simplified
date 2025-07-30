package org.endless.ddd.simplified.generator.components.generator.service.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.common.model.domain.entity.DDDSimplifiedGeneratorAggregate;
import org.endless.ddd.simplified.generator.components.generator.service.domain.type.ServiceTypeEnum;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateRemoveException;
import org.endless.ddd.simplified.starter.common.exception.model.domain.entity.AggregateValidateException;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ServiceAggregate
 * <p>
 * 服务领域聚合根
 * <p>
 * create 2025/07/29 16:16
 * <p>
 * update 2025/07/29 16:16
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorAggregate
 * @since 0.0.1
 */
@Getter
@ToString
@Builder(buildMethodName = "innerBuild")
public class ServiceAggregate implements DDDSimplifiedGeneratorAggregate {

    /**
     * 服务构件ID
     */
    private String serviceArtifactId;

    /**
     * 项目构件ID
     */
    private String projectArtifactId;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 服务版本
     */
    private String version;

    /**
     * 服务作者
     */
    private String author;

    /**
     * 服务根路径
     */
    private String rootPath;

    /**
     * 服务基础包名
     */
    private String basePackage;

    /**
     * 服务类型
     */
    private ServiceTypeEnum type;

    /**
     * 服务端口
     */
    private Integer port;

    /**
     * 限界上下文名称列表
     */
    private List<String> contextNames;

    /**
     * 服务创建时间
     */
    private String createAt;

    /**
     * 服务更新时间
     */
    private String updateAt;

    public static ServiceAggregate create(ServiceAggregateBuilder builder) {
        return builder
                .innerBuild()
                .validate();
    }

    public ServiceAggregate remove(String modifyUserId) {
        if (!canRemove()) {
            throw new AggregateRemoveException("聚合根<服务领域聚合根>处于不可删除状态");
        }
        return this;
    }

    private boolean canRemove() {
        return true;
    }

    @Override
    public ServiceAggregate validate() {
        validateServiceArtifactId();
        validateProjectArtifactId();
        validateName();
        validateDescription();
        validateVersion();
        validateAuthor();
        validateRootPath();
        validateBasePackage();
        validateType();
        validatePort();
        validateCreateAt();
        validateUpdateAt();
        return this;
    }

    private void validateServiceArtifactId() {
        if (!StringUtils.hasText(serviceArtifactId)) {
            throw new AggregateValidateException("服务构件ID不能为空");
        }
    }

    private void validateProjectArtifactId() {
        if (!StringUtils.hasText(projectArtifactId)) {
            throw new AggregateValidateException("项目构件ID不能为空");
        }
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new AggregateValidateException("服务名称不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new AggregateValidateException("服务描述不能为空");
        }
    }

    private void validateVersion() {
        if (!StringUtils.hasText(version)) {
            throw new AggregateValidateException("服务版本不能为空");
        }
    }

    private void validateAuthor() {
        if (!StringUtils.hasText(author)) {
            throw new AggregateValidateException("服务作者不能为空");
        }
    }

    private void validateRootPath() {
        if (!StringUtils.hasText(rootPath)) {
            throw new AggregateValidateException("服务根路径不能为空");
        }
    }

    private void validateBasePackage() {
        if (!StringUtils.hasText(basePackage)) {
            throw new AggregateValidateException("服务基础包名不能为空");
        }
    }

    private void validateType() {
        if (type == null) {
            throw new AggregateValidateException("服务类型不能为 null");
        }
    }

    private void validatePort() {
        if (port == null || port <= 0) {
            throw new AggregateValidateException("服务端口不能为空或无效");
        }
    }

    private void validateCreateAt() {
        if (!StringUtils.hasText(createAt)) {
            throw new AggregateValidateException("服务创建时间不能为空");
        }
    }

    private void validateUpdateAt() {
        if (!StringUtils.hasText(updateAt)) {
            throw new AggregateValidateException("服务更新时间不能为空");
        }
    }
}
