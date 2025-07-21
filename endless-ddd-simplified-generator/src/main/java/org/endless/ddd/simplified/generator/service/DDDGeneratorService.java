package org.endless.ddd.simplified.generator.service;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.generator.generators.*;
import org.endless.ddd.simplified.generator.object.dto.AggregateDTO;
import org.endless.ddd.simplified.generator.object.dto.GeneratorDTO;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.endless.ddd.simplified.generator.object.type.AdapterType;
import org.endless.ddd.simplified.generator.object.type.CQRSType;
import org.endless.ddd.simplified.generator.utils.StringTools;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DDDGeneratorService
 * <p>
 * create 2024/10/16 19:29
 * <p>
 * update 2024/10/16 19:29
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Slf4j
@Service
public class DDDGeneratorService {

    private static final String MODEL_DIR = "model";

    public List<String> services() {
        File directory = new FileSystemResource(MODEL_DIR).getFile();
        if (!directory.exists()) {
            throw new IllegalArgumentException("DDD模型目录model不存在！");
        }
        try {
            log.debug("读取服务");
            return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isDirectory).map(File::getName).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("读取服务目录失败: " + e.getMessage());
        }
    }

    public List<String> contexts(String serviceName) {
        String path = MODEL_DIR + "/" + serviceName;
        File directory = new FileSystemResource(path).getFile();
        if (!directory.exists()) {
            throw new IllegalArgumentException("DDD模型目录model/" + serviceName + "不存在！");
        }
        try {
            log.debug("读取限界上下文");
            return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isDirectory).map(File::getName).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("读取限界上下文目录失败: " + e.getMessage());
        }
    }

    public List<String> domains(String serviceName, String contextName) {
        String path = MODEL_DIR + "/" + serviceName + "/" + contextName;
        File directory = new FileSystemResource(path).getFile();
        if (!directory.exists()) {
            throw new IllegalArgumentException("DDD模型目录model/" + serviceName + "/" + contextName + "不存在！");
        }
        try {
            log.debug("读取领域");
            return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isDirectory).map(File::getName).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("读取领域目录失败: " + e.getMessage());
        }
    }

    public List<String> aggregates(String serviceName, String contextName, String domainName) {
        String path = MODEL_DIR + "/" + serviceName + "/" + contextName + "/" + domainName;
        File directory = new FileSystemResource(path).getFile();
        if (!directory.exists()) {
            throw new IllegalArgumentException("DDD模型目录model/" + serviceName + "/" + contextName + "/" + domainName + "不存在！");
        }
        try {
            log.debug("读取聚合");
            return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isFile).map(File::getName).filter(name -> name.endsWith(".yaml")).map(name -> StringTools.removeSuffix(name, ".yaml")).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("读取聚合文件失败: " + e.getMessage());
        }
    }

    public void generate(GeneratorDTO generatorDTO) {
        log.debug("开始生成代码");
        log.debug(generatorDTO.toString());
        try {
            Aggregate aggregate = getAggregate(
                    AggregateDTO.builder()
                            .serviceName(generatorDTO.getServiceName())
                            .contextName(generatorDTO.getContextName())
                            .domainName(generatorDTO.getDomainName())
                            .aggregateName(generatorDTO.getAggregateName())
                            .build());

            // 处理聚合根
            if (generatorDTO.getEnableEntity()) {
                new EntityGenerator().generateAggregate(aggregate);
            }
            new AssociationGenerator().generate(aggregate, generatorDTO.getEnableRecord(), generatorDTO.getEnableMapper());
            // 处理数据库记录实体
            if (generatorDTO.getEnableRecord()) {
                new DataRecordGenerator().generateAggregate(aggregate);
                // new DataRecordGenerator().generateAssociation(aggregate, aggregate.getFields(), aggregate.getAggregateName());
            }
            // 处理值对象
            Optional.ofNullable(aggregate.getValues())
                    .filter(values -> generatorDTO.getEnableValue() && !values.isEmpty())
                    .ifPresent(values -> values.forEach(value -> {
                        try {
                            new ValueGenerator().generateValue(aggregate, value);
                        } catch (Exception e) {
                            throw new RuntimeException("生成值对象 " + value.getName() + " 失败: " + e.getMessage());
                        }
                    }));
            // 处理枚举类
            Optional.ofNullable(aggregate.getEnums())
                    .filter(enums -> generatorDTO.getEnableEnum() && !enums.isEmpty())
                    .ifPresent(enums -> enums.forEach(enumObject -> {
                        try {
                            new EnumGenerator().generateEnum(aggregate, enumObject);
                        } catch (Exception e) {
                            throw new RuntimeException("生成枚举类 " + enumObject.getName() + " 失败: " + e.getMessage());
                        }
                    }));
            // 处理仓储
            if (generatorDTO.getEnableRepository()) {
                new RepositoryGenerator().generateAggregate(aggregate);
                new RepositoryGenerator().generateAggregateQuery(aggregate);
            }
            // 处理命令处理器
            if (generatorDTO.getEnableCommandHandler()) {
                new CommandHandlerGenerator().generateAggregate(aggregate);
            }
            // 处理查询处理器
            if (generatorDTO.getEnableQueryHandler()) {
                new QueryHandlerGenerator().generateAggregate(aggregate);
            }

            if (generatorDTO.getEnableDataManager()) {
                new DataManagerGenerator().generateAggregate(aggregate);
            }
            if (generatorDTO.getEnableMapper()) {
                new MapperGenerator().generateAggregate(aggregate);
            }
            // 处理主动适配器
            if (generatorDTO.getEnableDrivingAdapter()) {
                new DrivingAdapterGenerator().generateDomain(aggregate);
            }
            // 处理Web控制器
            if (generatorDTO.getEnableController()) {
                new ControllerGenerator().generateAggregate(aggregate);
            }
            // 处理传输对象
            Optional.ofNullable(aggregate.getTransfers())
                    .filter(transfers -> !transfers.isEmpty())
                    .ifPresent(transfers -> transfers.forEach(transfer -> {
                        try {
                            if (generatorDTO.getEnableCommandTransfer() && transfer.getCqrsType() == CQRSType.COMMAND && transfer.getAdapterType() == AdapterType.DRIVING) {
                                new TransferGenerator().generate(aggregate, transfer);
                            } else if (generatorDTO.getEnableQueryTransfer() && transfer.getCqrsType() == CQRSType.QUERY && transfer.getAdapterType() == AdapterType.DRIVING) {
                                new TransferGenerator().generate(aggregate, transfer);
                            } else if (generatorDTO.getEnableDrivenTransfer() && transfer.getAdapterType() == AdapterType.DRIVEN) {
                                new TransferGenerator().generate(aggregate, transfer);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("生成传输对象 " + transfer.getName() + " 失败: " + e.getMessage());
                        }
                    }));
            // 处理其他实体类
            Optional.ofNullable(aggregate.getEntities())
                    .filter(entities -> !entities.isEmpty())
                    .ifPresent(entities -> entities.forEach(entity -> {
                        try {

                            if (generatorDTO.getEnableEntity()) {
                                new EntityGenerator().generateEntity(aggregate, entity);
                            }
                            if (generatorDTO.getEnableRecord()) {
                                DataRecordGenerator dataRecordGenerator = new DataRecordGenerator();
                                dataRecordGenerator.generateEntity(aggregate, entity);
                            }
                            if (generatorDTO.getEnableRepository()) {
                                new RepositoryGenerator().generateEntityQuery(aggregate, entity);
                            }
                            if (generatorDTO.getEnableDataManager()) {
                                new DataManagerGenerator().generateEntity(aggregate, entity);
                            }
                            if (generatorDTO.getEnableMapper()) {
                                new MapperGenerator().generateEntity(aggregate, entity);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("生成实体 " + entity.getName() + " 失败: " + e.getMessage());
                        }
                    }));
        } catch (Exception e) {
            log.error("代码生成失败!", e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Aggregate getAggregate(AggregateDTO aggregateDTO) {
        log.debug("开始读取聚合信息");
        try {
            Yaml yaml = new Yaml();
            String fileName = MODEL_DIR + "/" + aggregateDTO.getServiceName() + "/" + aggregateDTO.getContextName() + "/" + aggregateDTO.getDomainName() + "/" + aggregateDTO.getAggregateName() + ".yaml";
            // 读取 YAML 文件并转换为 Map
            Map<String, Object> yamlMap = yaml.load(new FileSystemResource(fileName).getInputStream());

            // 将 Map 转换为 Aggregate 对象
            String jsonString = JSON.toJSONString(yamlMap);
            return JSON.parseObject(jsonString, Aggregate.class);
        } catch (Exception e) {
            log.error("读取聚合信息失败!", e);
            throw new RuntimeException("读取聚合信息失败", e);
        }
    }

    public void putAggregate(Aggregate aggregate) {
        log.debug("开始更新聚合信息");
        try {
            if (aggregate == null) {
                throw new IllegalArgumentException("聚合对象不能为空");
            }
            aggregate.validate();
            List<Entity> entities = aggregate.getEntities();
            if (entities != null) {
                entities.forEach(entity -> {
                });
            }

            // 将 JSON 字符串解析为有序的 LinkedHashMap
            LinkedHashMap<?, ?> jsonMap = JSON.parseObject(JSON.toJSONString(aggregate), LinkedHashMap.class);
            // 配置输出选项，设置为块样式（标准 YAML 格式），并设置缩进
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);  // 使用块样式
            options.setPrettyFlow(true);  // 美化输出
            options.setIndent(4);  // 设置缩进为 4
            options.setIndicatorIndent(2);  // 数组前的缩进

            // 创建 Yaml 对象
            Yaml yaml = new Yaml(options);

            // 生成目标文件路径
            String directory = MODEL_DIR + "/" + aggregate.getServiceName() + "/" + aggregate.getContextName() + "/" + aggregate.getDomainName();
            String fileName = aggregate.getAggregateName() + ".yaml";

            File dir = new File(directory);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new IOException("创建目录失败 " + directory);
                }
            }

            // 打开文件输出流
            FileSystemResource fileResource = new FileSystemResource(directory + "/" + fileName);
            try (Writer writer = new OutputStreamWriter(fileResource.getOutputStream())) {
                yaml.dump(jsonMap, writer);
                writer.flush();
            }
            log.debug("更新聚合信息成功: {}", fileName);

        } catch (Exception e) {
            log.error("更新聚合信息失败", e);
            throw new RuntimeException("更新聚合信息失败, 文件IO异常: " + e.getMessage(), e);
        }
    }
}
