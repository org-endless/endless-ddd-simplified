package org.endless.ddd.simplified.generator.template;

/**
 * EndTemplate
 * <p>
 * create 2024/10/11 10:52
 * <p>
 * update 2024/10/11 10:52
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class EndTemplate {

    public static void end(StringBuilder stringBuilder) {
        // 移除最后一个多余的换行符
        if (!stringBuilder.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        stringBuilder.append("}\n");
    }
}
