package org.endless.ddd.simplified.generator.common.model.infrastructure.adapter;

import com.alibaba.fastjson2.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.endless.ddd.simplified.generator.common.model.domain.anticorruption.DDDSimplifiedGeneratorDrivenAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.StringWriter;
import java.util.LinkedHashMap;

/**
 * DDDSimplifiedGeneratorContentDrivenAdapter
 * <p>
 * create 2025/07/30 11:53
 * <p>
 * update 2025/07/30 11:53
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public interface DDDSimplifiedGeneratorContentDrivenAdapter extends DDDSimplifiedGeneratorDrivenAdapter {

    Logger log = LoggerFactory.getLogger(DDDSimplifiedGeneratorContentDrivenAdapter.class);

    default <T> String yaml(T object) {
        if (object == null) {
            throw new YAMLException("对象转换为YAML格式失败，输入对象为空");
        }
        try {
            // 将 JSON 字符串解析为有序的 LinkedHashMap
            LinkedHashMap<?, ?> jsonMap = JSON.parseObject(JSON.toJSONString(object), LinkedHashMap.class);

            // 配置输出选项，设置为块样式（标准 YAML 格式），并设置缩进
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);  // 使用块样式
            options.setPrettyFlow(true);  // 美化输出
            options.setIndent(4);  // 设置缩进为 4
            options.setIndicatorIndent(2);  // 数组前的缩进
            Yaml yaml = new Yaml(options);
            try (StringWriter writer = new StringWriter()) {
                yaml.dump(jsonMap, writer);
                writer.flush();
                log.trace("对象转换为YAML格式成功");
                return writer.toString();
            }
        } catch (Exception e) {
            throw new YAMLException("对象转换为YAML格式失败: " + e.getMessage(), e);
        }
    }

    default <T> String freemarker(Configuration freemarkerConfig, T object, String templateFileName) {
        if (object == null) {
            throw new YAMLException("Freemarker模板渲染失败，输入对象为空");
        }
        try {
            Template template = freemarkerConfig.getTemplate(templateFileName);
            try (StringWriter writer = new StringWriter()) {
                template.process(object, writer);
                writer.flush();
                log.trace("Freemarker模板渲染成功");
                return writer.toString();
            }
        } catch (Exception e) {
            throw new YAMLException("Freemarker模板渲染失败: " + e.getMessage(), e);
        }
    }
}
