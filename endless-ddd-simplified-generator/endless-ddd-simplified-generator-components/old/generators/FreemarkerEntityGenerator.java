package org.endless.ddd.simplified.generator.components.generators;

import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.generator.components.generator.domain.domain.entity.Field;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.endless.ddd.simplified.generator.service.FreemarkerTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于Freemarker的实体生成器
 * <p>
 * create 2024/12/19 10:30
 * <p>
 * update 2024/12/19 10:30
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Slf4j
@Component
public class FreemarkerEntityGenerator {

    @Autowired
    private FreemarkerTemplateService templateService;

    /**
     * 生成聚合根类
     *
     * @param aggregate 聚合信息
     * @throws Exception 异常
     */
    public void generateAggregate(Aggregate aggregate) throws Exception {
        if (aggregate == null || aggregate.getFields() == null || aggregate.getFields().isEmpty()
                || aggregate.getAggregateName() == null) {
            throw new IllegalArgumentException("聚合格式定义错误");
        }

        String className = aggregate.getAggregateName();
        String description = aggregate.getDescription() + "聚合根";

        // 构建数据模型
        Map<String, Object> dataModel = templateService.buildAggregateModel(
                convertAggregateToMap(aggregate),
                className,
                convertFieldsToList(aggregate.getFields()),
                description);

        // 生成代码
        String generatedCode = templateService.generateCode("domain.ftl", dataModel);

        // 写入文件
        writeToFile(aggregate, className, generatedCode);

        log.info("成功生成聚合根类: {}", className);
    }

    /**
     * 生成实体类
     *
     * @param aggregate 聚合信息
     * @param entity    实体信息
     * @throws Exception 异常
     */
    public void generateEntity(Aggregate aggregate, Entity entity) throws Exception {
        if (entity == null || entity.getFields() == null || entity.getFields().isEmpty() || entity.getName() == null) {
            throw new IllegalArgumentException("实体格式定义错误");
        }

        String className = entity.getName();
        String description = entity.getDescription() + "实体";

        // 构建数据模型
        Map<String, Object> dataModel = templateService.buildEntityModel(
                convertAggregateToMap(aggregate),
                className,
                convertFieldsToList(entity.getFields()),
                description);

        // 生成代码
        String generatedCode = templateService.generateCode("entity.ftl", dataModel);

        // 写入文件
        writeToFile(aggregate, className, generatedCode);

        log.info("成功生成实体类: {}", className);
    }

    /**
     * 生成枚举类
     *
     * @param aggregate   聚合信息
     * @param enumName    枚举名称
     * @param enumValues  枚举值列表
     * @param fields      字段列表
     * @param description 描述
     * @throws Exception 异常
     */
    public void generateEnum(Aggregate aggregate, String enumName, List<Object> enumValues, List<Object> fields,
                             String description) throws Exception {
        // 构建数据模型
        Map<String, Object> dataModel = templateService.buildEnumModel(
                convertAggregateToMap(aggregate),
                enumName,
                enumValues,
                fields,
                description);

        // 生成代码
        String generatedCode = templateService.generateCode("enum.ftl", dataModel);

        // 写入文件
        writeToFile(aggregate, enumName, generatedCode);

        log.info("成功生成枚举类: {}", enumName);
    }

    /**
     * 将Aggregate对象转换为Map
     */
    private Map<String, Object> convertAggregateToMap(Aggregate aggregate) {
        Map<String, Object> map = new HashMap<>();
        map.put("author", aggregate.getAuthor());
        map.put("version", aggregate.getVersion());
        map.put("groupId", aggregate.getGroupId());
        map.put("serviceName", aggregate.getServiceName());
        map.put("contextName", aggregate.getContextName());
        map.put("domainName", aggregate.getDomainName());
        map.put("rootPath", aggregate.getRootPath());
        return map;
    }

    /**
     * 将Field列表转换为Object列表
     */
    private List<Object> convertFieldsToList(List<Field> fields) {
        return fields.stream().map(field -> {
            Map<String, Object> fieldMap = new HashMap<>();
            fieldMap.put("name", field.name());
            fieldMap.put("type", field.type());
            fieldMap.put("description", field.description());
            fieldMap.put("nullable", field.nullable());
            return (Object) fieldMap;
        }).toList();
    }

    /**
     * 写入文件
     */
    private void writeToFile(Aggregate aggregate, String className, String content) throws IOException {
        // 构建文件路径
        String packagePath = buildPackagePath(aggregate);
        String fileName = className + ".java";

        Path filePath = Paths.get(aggregate.getRootPath(), "src", "main", "java", packagePath.replace(".", "/"),
                fileName);

        // 创建目录
        Files.createDirectories(filePath.getParent());

        // 写入文件
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write(content);
        }
    }

    /**
     * 构建包路径
     */
    private String buildPackagePath(Aggregate aggregate) {
        return String.format("%s.%s.%s.%s.domain.entity",
                aggregate.getGroupId(),
                aggregate.getServiceName(),
                aggregate.getContextName(),
                aggregate.getDomainName());
    }
}
