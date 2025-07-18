package org.endless.ddd.simplified.starter.common.utils.model.file;

import java.util.HashMap;
import java.util.Map;

/**
 * FileTools
 * <p>
 * create 2025/07/12 15:13
 * <p>
 * update 2025/07/12 15:13
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class FileTools {

    private static final Map<String, String> CONTENT_TYPE_TO_EXTENSION = new HashMap<>();

    private static final Map<String, String> EXTENSION_TO_CONTENT_TYPE = new HashMap<>();

    static {
        add(".jpg", "image/jpeg");
        add(".jpeg", "image/jpeg");
        add(".png", "image/png");
        add(".gif", "image/gif");
        add(".pdf", "application/pdf");
        add(".doc", "application/msword");
        add(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        add(".xls", "application/vnd.ms-excel");
        add(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        add(".txt", "text/plain");
        add(".zip", "application/zip");
        add(".rar", "application/x-rar-compressed");
        add(".bin", "application/octet-stream");
        add(".json", "application/json");
        add(".csv", "text/csv");
    }

    private static void add(String extension, String contentType) {
        CONTENT_TYPE_TO_EXTENSION.put(contentType, extension);
        EXTENSION_TO_CONTENT_TYPE.put(extension, contentType);
    }

    public static String extension(String contentType) {
        return CONTENT_TYPE_TO_EXTENSION.getOrDefault(contentType, "");
    }

    public static String contentType(String extension) {
        return EXTENSION_TO_CONTENT_TYPE.getOrDefault(extension.toLowerCase(), "application/octet-stream");
    }
}
