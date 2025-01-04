package org.endless.ddd.simplified.generator.utils;

import lombok.extern.slf4j.Slf4j;
import org.endless.ddd.simplified.generator.object.entity.Aggregate;
import org.endless.ddd.simplified.generator.object.entity.Entity;
import org.endless.ddd.simplified.generator.object.entity.Enum;
import org.endless.ddd.simplified.generator.object.entity.Value;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.endless.ddd.simplified.generator.utils.StringTools.*;

/**
 * DDDUtils
 * <p>
 * create 2024/09/16 12:45
 * <p>
 * update 2024/09/16 12:45
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Slf4j
public class DDDUtils {

    private static final int MAX_BACKUP_FILES = 10;

    private static final String PROJECT_ROOT = "..";

    public static String domainPackage(Aggregate aggregate) {
        String contextName = aggregate.getContextName().toLowerCase();
        String domainPackage = aggregate.getDomainName().toLowerCase();
        if (!contextName.equals(domainPackage)) {
            domainPackage = convertToDot(aggregate.getDomainName()).replaceFirst(contextName + ".", "");
        }
        return servicePackage(aggregate) + "." + aggregate.getServiceSubPackage() + "." + aggregate.getContextName().toLowerCase() + "." + domainPackage;
    }

    public static String servicePackage(Aggregate aggregate) {
        return aggregate.getGroupId() + "." + convertToDot(aggregate.getServiceName());
    }

    public static String packageName(Aggregate aggregate, String subPackage) {
        return domainPackage(aggregate) + "." + subPackage;
    }


    /**
     * 工具方法：获取类的ID属性名
     *
     * @param className 类名
     * @return {@link String }
     */
    public static String id(String className, int uppercase) {
        return StringUtils.uncapitalize(removeSuffix(className, classType(className, uppercase)) + "Id");
    }

    public static String getter(String fieldName) {
        return "get" + StringUtils.capitalize(fieldName);
    }

    /**
     * 工具方法：获取类的类型
     *
     * @param className 类名
     * @return {@link String }
     */
    public static String classType(String className, int uppercase) {
        return getLastCamelCase(className, uppercase);
    }

    /**
     * 工具方法：获取父类名
     *
     * @param serviceName 服务名
     * @param className   类名
     * @return {@link String }
     */
    public static String superClassName(String serviceName, String className, int uppercase) {
        return serviceName + classType(className, uppercase);
    }

    /**
     * 工具方法：获取父类名并带有泛型
     *
     * @param superClassName 父类名
     * @param generics       泛型
     * @return {@link String }
     */
    public static String superClassNameWithGenerics(String superClassName, String... generics) {
        return superClassName + "<" + String.join(", ", generics) + ">";
    }

    /**
     * 工具方法：获取字段方法名
     *
     * @param fieldName 字段名
     * @return {@link String }
     */
    public static String fieldMethod(String fieldName) {
        return StringUtils.capitalize(fieldName) + "()";
    }

    public static Set<String> entityNames(Aggregate aggregate) {
        return Optional.ofNullable(aggregate.getEntities())
                .orElse(Collections.emptyList()).stream()
                .map(Entity::getName)
                .collect(Collectors.toSet());
    }

    public static Set<String> valueNames(Aggregate aggregate) {
        return Optional.ofNullable(aggregate.getValues())
                .orElse(Collections.emptyList()).stream()
                .map(Value::getName)
                .collect(Collectors.toSet());
    }

    public static Set<String> enumNames(Aggregate aggregate) {
        return Optional.ofNullable(aggregate.getEnums())
                .orElse(Collections.emptyList()).stream()
                .map(Enum::getName)
                .collect(Collectors.toSet());
    }

    /**
     * 工具方法：写入文件
     *
     * @param rootPath    项目根目录
     * @param packageName 包名
     * @param className   类名
     * @param content     文件内容
     * @throws IOException 写入文件异常
     */
    public static void writeFile(String rootPath, String packageName, String className, String content) throws IOException {
        String directory = PROJECT_ROOT + "/" + rootPath + "/src/main/java/" + packageName.replace(".", "/") + "/";
        File dir = new File(directory);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("创建目录失败 " + directory);
            }
        }
        File file;
        if (directory.contains("sql")) {
            file = new File(dir, removeSuffix(className, 1) + ".ddl.sql");
        } else {
            file = new File(dir, className + ".java");
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            throw new IOException("写入文件失败 " + directory);
        }
        log.info("{} 已生成", className);
    }

    /**
     * 工具方法：获取当前日期
     *
     * @return {@link String }
     */
    public static String currentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 工具方法：获取方法修饰符
     *
     * @param className 类名
     * @return {@link String }
     */
    public static String access(String className, Boolean isAggregateInner, Boolean isEntityInner) {

        if (className.endsWith("Aggregate") && isAggregateInner) {
            return "private";
        } else if (className.endsWith("Entity") && isEntityInner) {
            return "protected";
        }
        return "public";
    }

    /**
     * 工具方法：删除文件
     *
     * @param rootPath    项目根目录
     * @param packageName 包名
     * @throws IOException 删除文件异常
     */
    public static void deleteFileIfExists(String rootPath, String packageName, String className) throws IOException {
        String directory = PROJECT_ROOT + "/" + rootPath + "/src/main/java/" + packageName.replace(".", "/") + "/";
        // 构建文件路径
        Path filePath;
        if (directory.contains("sql")) {
            filePath = Paths.get(directory, removeSuffix(className, 1) + ".ddl.sql");
        } else {
            filePath = Paths.get(directory, className + ".java");
        }
        File file = filePath.toFile();

        // 如果文件存在
        if (file.exists()) {
            // 备份文件（根据配置 enableBackup 控制备份）
            backupFileIfEnabled(filePath, directory, className);

            // 删除原文件
            if (!file.delete()) {
                log.error("无法删除文件 {}", filePath);
                throw new IOException("删除文件失败 " + filePath);
            }
            log.debug("{} 已删除", className);
        }
    }

    // 备份文件的方法
    private static void backupFileIfEnabled(Path filePath, String directory, String className) throws IOException {
        if (enableBackup()) {

            String backupDirectory = directory.replace(PROJECT_ROOT, PROJECT_ROOT + "/.ddd/backup");
            File dir = new File(backupDirectory);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new IOException("创建目录失败 " + directory);
                }
            }

            // 获取当前时间，并格式化为 "yyyyMMddHHmmss"
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // 构建备份文件路径，添加 .bak 和时间戳
            Path backupFilePath = Paths.get(backupDirectory, className + ".bak." + timeStamp);

            // 备份文件
            Files.copy(filePath, backupFilePath);
            log.debug("{} 已备份", className);

            // 删除多余的备份文件
            deleteOldestBackupIfExceeds(backupDirectory, className);
        }
    }

    private static void deleteOldestBackupIfExceeds(String directory, String className) {
        File backupDirectory = new File(directory);
        File[] backupFiles = backupDirectory.listFiles((dir, name) -> name.contains(className + ".bak."));

        if (backupFiles != null && backupFiles.length > MAX_BACKUP_FILES) {

            log.debug("删除多余的备份文件");
            // 按修改时间排序，最早的在前
            Arrays.sort(backupFiles, Comparator.comparingLong(File::lastModified));

            // 删除多余的备份文件（只保留最新的 MAX_BACKUP_FILES 个）
            int filesToDelete = backupFiles.length - MAX_BACKUP_FILES;
            for (int i = 0; i < filesToDelete; i++) {
                if (!backupFiles[i].delete()) {
                    log.error("无法删除备份文件 {}", backupFiles[i].getName());
                } else {
                    log.debug("删除了过期的备份文件 {}", backupFiles[i].getName());
                }
            }
        }
    }

    private static Boolean enableBackup() {
        return true;
    }
}
