package org.endless.ddd.simplified.generator.common.model.infrastructure.adapter;

import org.endless.ddd.simplified.generator.common.model.domain.anticorruption.DDDSimplifiedGeneratorDrivenAdapter;
import org.endless.ddd.simplified.starter.common.exception.common.FailedException;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem.FileSystemRemoveFailedException;
import org.endless.ddd.simplified.starter.common.exception.model.infrastructure.adapter.filesystem.FileSystemStoreFailedException;
import org.endless.ddd.simplified.starter.common.utils.model.time.DateTime;
import org.endless.ddd.simplified.starter.common.utils.model.time.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

/**
 * DDDSimplifiedGeneratorFileDrivenAdapter
 * <p>
 * create 2025/07/29 21:23
 * <p>
 * update 2025/07/29 21:25
 *
 * @author Deng Haozhi
 * @see DDDSimplifiedGeneratorDrivenAdapter
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorFileDrivenAdapter extends DDDSimplifiedGeneratorDrivenAdapter {

    String SERVICE_COMMON_PACKAGE = "common";
    String SERVICE_COMPONENTS_PACKAGE = "components";
    String SERVICE_CORE_PACKAGE = "core";
    String SERVICE_SUPPORT_PACKAGE = "support";

    String DEFAULT_DESIGN_DIRECTORY = "design";
    String DEFAULT_MODEL_DIRECTORY = "model";
    String DEFAULT_DRAWING_DIRECTORY = "drawing";
    String DEFAULT_BACKUP_DIRECTORY = ".ddd";
    Integer MAX_BACKUP_COUNTS = 10;

    Logger log = LoggerFactory.getLogger(DDDSimplifiedGeneratorFileDrivenAdapter.class);


    default void write(String filePath, String fileName, String content) {
        try {
            File directory = new File(filePath);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new FileSystemStoreFailedException("创建目录失败 " + directory);
                }
            }
            File file = Paths.get(filePath, fileName).toFile();
            if (!file.exists()) {
                remove(filePath, fileName, content);
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            } catch (Exception e) {
                throw new FileSystemStoreFailedException("文件写入失败: " + file);

            }
            log.trace("文件 {} 已生成", fileName);
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new FileSystemStoreFailedException("文件存储失败: " + fileName, e);
        }
    }

    default void remove(String filePath, String fileName, String content) {
        try {
            File file = Paths.get(filePath, fileName).toFile();
            if (file.exists()) {
                backup(file, filePath, fileName);
                if (!file.delete()) {
                    throw new FileSystemRemoveFailedException("删除文件失败 " + fileName);
                }
                log.trace("文件 {} 已删除", fileName);
            }
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new FileSystemRemoveFailedException("文件删除失败: " + fileName, e);
        }
    }

    default void backup(File file, String filePath, String fileName) {
        try {
            String backupPath = DEFAULT_BACKUP_DIRECTORY + "/" + filePath;
            File directory = new File(backupPath);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new FileSystemStoreFailedException("创建备份目录失败 " + directory);
                }
            }
            File backupFile = Paths.get(backupPath,
                            fileName + "." + DateTime.from(TimeStamp.now(), "yyyyMMddHHmmssSSS"))
                    .toFile();
            Files.copy(file.toPath(), backupFile.toPath());
            log.trace("文件 {} 已备份", fileName);
            removeBackup(backupPath, fileName);
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new FileSystemStoreFailedException("文件备份失败: " + fileName, e);
        }
    }

    default void removeBackup(String backupPath, String fileName) {
        try {
            File directory = new File(backupPath);
            File[] backupFiles = directory.listFiles((dir, name) -> name.contains(fileName));
            if (backupFiles != null && backupFiles.length > MAX_BACKUP_COUNTS) {
                Arrays.sort(backupFiles, Comparator.comparingLong(File::lastModified));
                int removeCounts = backupFiles.length - MAX_BACKUP_COUNTS;
                for (int i = 0; i < removeCounts; i++) {
                    if (!backupFiles[i].delete()) {
                        throw new FileSystemRemoveFailedException("删除备份文件失败 " + backupFiles[i].getName());
                    }
                }
                log.trace("已删除多余的备份文件");
            }
        } catch (FailedException e) {
            throw e;
        } catch (Exception e) {
            throw new FileSystemRemoveFailedException("备份文件删除失败: " + fileName, e);
        }
    }
}
