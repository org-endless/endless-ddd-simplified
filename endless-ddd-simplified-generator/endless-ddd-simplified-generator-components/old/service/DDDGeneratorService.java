// package org.endless.ddd.simplified.generator.components.service;
//
// import com.alibaba.fastjson2.JSON;
// import lombok.extern.slf4j.Slf4j;
// import org.endless.ddd.simplified.generator.components.generators.*;
// import StringTools;
// import org.endless.ddd.simplified.starter.common.model.domain.entity.Aggregate;
// import org.springframework.core.io.FileSystemResource;
// import org.springframework.stereotype.Service;
// import org.yaml.snakeyaml.Yaml;
//
// import java.io.File;
// import java.util.*;
// import java.util.stream.Collectors;
//
// /**
//  * DDDGeneratorService
//  * <p>
//  * create 2024/10/16 19:29
//  * <p>
//  * update 2024/10/16 19:29
//  *
//  * @author Deng Haozhi
//  * @since 2.0.0
//  */
// @Slf4j
// @Service
// public class DDDGeneratorService {
//
//     private static final String MODEL_DIR = "model";
//
//     public List<String> services() {
//         File directory = new FileSystemResource(MODEL_DIR).getFile();
//         if (!directory.exists()) {
//             throw new IllegalArgumentException("DDD模型目录model不存在！");
//         }
//         try {
//             log.debug("读取服务");
//             return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isDirectory).map(File::getName).collect(Collectors.toList());
//         } catch (Exception e) {
//             throw new RuntimeException("读取服务目录失败: " + e.getMessage());
//         }
//     }
//
//     public List<String> contexts(String serviceName) {
//         String path = MODEL_DIR + "/" + serviceName;
//         File directory = new FileSystemResource(path).getFile();
//         if (!directory.exists()) {
//             throw new IllegalArgumentException("DDD模型目录model/" + serviceName + "不存在！");
//         }
//         try {
//             log.debug("读取限界上下文");
//             return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isDirectory).map(File::getName).collect(Collectors.toList());
//         } catch (Exception e) {
//             throw new RuntimeException("读取限界上下文目录失败: " + e.getMessage());
//         }
//     }
//
//     public List<String> domains(String serviceName, String contextName) {
//         String path = MODEL_DIR + "/" + serviceName + "/" + contextName;
//         File directory = new FileSystemResource(path).getFile();
//         if (!directory.exists()) {
//             throw new IllegalArgumentException("DDD模型目录model/" + serviceName + "/" + contextName + "不存在！");
//         }
//         try {
//             log.debug("读取领域");
//             return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isDirectory).map(File::getName).collect(Collectors.toList());
//         } catch (Exception e) {
//             throw new RuntimeException("读取领域目录失败: " + e.getMessage());
//         }
//     }
//
//     public List<String> aggregates(String serviceName, String contextName, String domainName) {
//         String path = MODEL_DIR + "/" + serviceName + "/" + contextName + "/" + domainName;
//         File directory = new FileSystemResource(path).getFile();
//         if (!directory.exists()) {
//             throw new IllegalArgumentException("DDD模型目录model/" + serviceName + "/" + contextName + "/" + domainName + "不存在！");
//         }
//         try {
//             log.debug("读取聚合");
//             return Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isFile).map(File::getName).filter(name -> name.endsWith(".yaml")).map(name -> StringTools.removeSuffix(name, ".yaml")).collect(Collectors.toList());
//         } catch (Exception e) {
//             throw new RuntimeException("读取聚合文件失败: " + e.getMessage());
//         }
//     }
//
//     public void generate(org.endless.ddd.simplified.generator.object.dto.GeneratorDTO generatorDTO) {
//         log.debug("开始生成代码");
//         log.debug(generatorDTO.toString());
//         try {
//             Aggregate domain = getAggregate(
//                     org.endless.ddd.simplified.generator.object.dto.AggregateDTO.builder()
//                             .serviceName(generatorDTO.getServiceName())
//                             .contextName(generatorDTO.getContextName())
//                             .domainName(generatorDTO.getDomainName())
//                             .aggregateName(generatorDTO.getAggregateName())
//                             .build());
//
//             // 处理聚合根
//             if (generatorDTO.getEnableEntity()) {
//                 new EntityGenerator().generateAggregate(domain);
//             }
//             // 处理关系银蛇
//             new AssociationGenerator().generate(domain, generatorDTO.getEnableRecord(), generatorDTO.getEnableMapper());
//             // 处理数据库记录实体
//             if (generatorDTO.getEnableRecord()) {
//                 new DataRecordGenerator().generateAggregate(domain);
//             }
//             // 处理值对象
//             Optional.ofNullable(domain.getValues())
//                     .filter(values -> generatorDTO.getEnableValue() && !values.isEmpty())
//                     .ifPresent(values -> values.forEach(value -> {
//                         try {
//                             new ValueGenerator().generateValue(domain, value);
//                         } catch (Exception e) {
//                             throw new RuntimeException("生成值对象 " + value.getName() + " 失败: " + e.getMessage());
//                         }
//                     }));
//             // 处理枚举类
//             Optional.ofNullable(domain.getEnums())
//                     .filter(enums -> generatorDTO.getEnableEnum() && !enums.isEmpty())
//                     .ifPresent(enums -> enums.forEach(enumObject -> {
//                         try {
//                             new EnumGenerator().generateEnum(domain, enumObject);
//                         } catch (Exception e) {
//                             throw new RuntimeException("生成枚举类 " + enumObject.getName() + " 失败: " + e.getMessage());
//                         }
//                     }));
//             // 处理仓储
//             if (generatorDTO.getEnableRepository()) {
//                 new RepositoryGenerator().generateAggregate(domain);
//                 new RepositoryGenerator().generateAggregateQuery(domain);
//             }
//
//             // 处理命令处理器
//             if (generatorDTO.getEnableCommandHandler()) {
//                 new CommandHandlerGenerator().generateAggregate(domain);
//             }
//             // 处理查询处理器
//             if (generatorDTO.getEnableQueryHandler()) {
//                 new QueryHandlerGenerator().generateAggregate(domain);
//             }
//
//             if (generatorDTO.getEnableDataManager()) {
//                 new DataManagerGenerator().generateAggregate(domain);
//             }
//             if (generatorDTO.getEnableMapper()) {
//                 new MapperGenerator().generateAggregate(domain);
//             }
//             // 处理主动适配器
//             if (generatorDTO.getEnableDrivingAdapter()) {
//                 new DrivingAdapterGenerator().generateDomain(domain);
//             }
//             // 处理Web控制器
//             if (generatorDTO.getEnableController()) {
//                 new ControllerGenerator().generateAggregate(domain);
//             }
//             // 处理传输对象
//             Optional.ofNullable(domain.getTransfers())
//                     .filter(transfers -> !transfers.isEmpty())
//                     .ifPresent(transfers -> transfers.forEach(transfer -> {
//                         try {
//                             if (generatorDTO.getEnableCommandTransfer() && transfer.getCqrsType() == org.endless.ddd.simplified.generator.object.type.DomainOperationTypeEnum.COMMAND && transfer.getAdapterType() == org.endless.ddd.simplified.generator.object.type.DomainAdapterTypeEnum.DRIVING) {
//                                 new TransferGenerator().generate(domain, transfer);
//                             } else if (generatorDTO.getEnableQueryTransfer() && transfer.getCqrsType() == org.endless.ddd.simplified.generator.object.type.DomainOperationTypeEnum.QUERY && transfer.getAdapterType() == org.endless.ddd.simplified.generator.object.type.DomainAdapterTypeEnum.DRIVING) {
//                                 new TransferGenerator().generate(domain, transfer);
//                             } else if (generatorDTO.getEnableDrivenTransfer() && transfer.getAdapterType() == org.endless.ddd.simplified.generator.object.type.DomainAdapterTypeEnum.DRIVEN) {
//                                 new TransferGenerator().generate(domain, transfer);
//                             }
//                         } catch (Exception e) {
//                             throw new RuntimeException("生成传输对象 " + transfer.getName() + " 失败: " + e.getMessage());
//                         }
//                     }));
//             // 处理其他实体类
//             Optional.ofNullable(domain.getEntities())
//                     .filter(entities -> !entities.isEmpty())
//                     .ifPresent(entities -> entities.forEach(entity -> {
//                         try {
//
//                             if (generatorDTO.getEnableEntity()) {
//                                 new EntityGenerator().generateEntity(domain, entity);
//                             }
//                             if (generatorDTO.getEnableRecord()) {
//                                 DataRecordGenerator dataRecordGenerator = new DataRecordGenerator();
//                                 dataRecordGenerator.generateEntity(domain, entity);
//                             }
//                             if (generatorDTO.getEnableRepository()) {
//                                 new RepositoryGenerator().generateEntityQuery(domain, entity);
//                             }
//                             if (generatorDTO.getEnableDataManager()) {
//                                 new DataManagerGenerator().generateEntity(domain, entity);
//                             }
//                             if (generatorDTO.getEnableMapper()) {
//                                 new MapperGenerator().generateEntity(domain, entity);
//                             }
//                         } catch (Exception e) {
//                             throw new RuntimeException("生成实体 " + entity.getName() + " 失败: " + e.getMessage());
//                         }
//                     }));
//         } catch (Exception e) {
//             log.error("代码生成失败!", e);
//             throw new IllegalArgumentException(e.getMessage());
//         }
//     }
//
//     public Aggregate getAggregate(org.endless.ddd.simplified.generator.object.dto.AggregateDTO aggregateDTO) {
//         log.debug("开始读取聚合信息");
//         try {
//             Yaml yaml = new Yaml();
//             String fileName = MODEL_DIR + "/" + aggregateDTO.getServiceName() + "/" + aggregateDTO.getContextName() + "/" + aggregateDTO.getDomainName() + "/" + aggregateDTO.getAggregateName() + ".yaml";
//             // 读取 YAML 文件并转换为 Map
//             Map<String, Object> yamlMap = yaml.load(new FileSystemResource(fileName).getInputStream());
//
//             // 将 Map 转换为 Aggregate 对象
//             String jsonString = JSON.toJSONString(yamlMap);
//             return JSON.parseObject(jsonString, Aggregate.class);
//         } catch (Exception e) {
//             log.error("读取聚合信息失败!", e);
//             throw new RuntimeException("读取聚合信息失败", e);
//         }
//     }
// }
