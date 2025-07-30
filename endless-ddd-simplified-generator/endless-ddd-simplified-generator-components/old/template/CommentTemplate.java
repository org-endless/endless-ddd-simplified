package org.endless.ddd.simplified.generator.components.template;

import org.springframework.util.StringUtils;

import static com.tansun.ddd.generator.utils.DDDUtils.currentDate;

/**
 * CommentTemplate
 * <p>
 * create 2024/10/11 10:23
 * <p>
 * update 2024/10/11 10:23
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class CommentTemplate {

    /**
     * 生成类注释模版代码
     *
     * @param stringBuilder  字符串构建器
     * @param className      类名
     * @param superClassName 父类名
     * @param author         作者
     * @param version        版本
     */
    public static void comment(StringBuilder stringBuilder, String className, String superClassName, String classDescription, String author, String version) {
        stringBuilder.
                append("/**\n")
                .append(" * ").append(className).append("\n")
                .append(" * <p>").append(classDescription).append("\n")
                .append(" * <p>\n")
                .append(" * create ").append(currentDate()).append("\n")
                .append(" * <p>\n")
                .append(" * update ").append(currentDate()).append("\n")
                .append(" *\n")
                .append(" * @author ").append(author).append("\n");
        if (StringUtils.hasText(superClassName)) {
            for (String name : superClassName.split(", ")) {
                stringBuilder.append(" * @see ").append(name).append("\n");
            }
        }
        stringBuilder.append(" * @since ").append(version).append("\n")
                .append(" */\n");
    }
}
