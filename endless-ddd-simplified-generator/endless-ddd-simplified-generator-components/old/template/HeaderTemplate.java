package org.endless.ddd.simplified.generator.components.template;

import static com.tansun.ddd.generator.utils.StringTools.removeSuffix;

/**
 * HeaderTemplate
 * <p>
 * create 2024/10/11 10:15
 * <p>
 * update 2024/10/11 10:15
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class HeaderTemplate {

    /**
     * 生成头部包引用代码模版
     *
     * @param stringBuilder 字符串构建器
     * @param packageName   包名
     */
    public static void packageHeader(StringBuilder stringBuilder, String packageName) {
        stringBuilder.append("package ").append(packageName).append(";\n\n");
    }

    /**
     * 生成实体类头部Import代码模版
     * <p>TODO: 此部分应该根据实际继承包路径进行修改
     *
     * @param stringBuilder 字符串构建器
     */
    public static void importHeaderEntity(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage, Boolean hasValue, Boolean hasEnum) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append(hasValue ? "import " + domainPackage + ".domain.value.*;\n" : "")
                .append(hasEnum ? "import " + domainPackage + ".domain.type.*;\n" : "")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import ").append(groupId).append(".atp.common.config.utils.id.*;\n")
                .append("import ").append(groupId).append(".atp.common.utils.model.decimal.Decimal;\n")
                .append("import lombok.Builder;\n")
                .append("import lombok.Getter;\n")
                .append("import lombok.ToString;\n")
                .append("import org.springframework.util.CollectionUtils;\n")
                .append("import org.springframework.util.StringUtils;\n\n")
                .append("import java.math.BigDecimal;\n")
                .append("import java.util.ArrayList;\n")
                .append("import java.util.List;\n")
                .append("import java.util.Set;\n")
                .append("import java.util.stream.Collectors;\n\n");
    }

    /**
     * 生成数据库实体类头部Import代码模版
     *
     * @param stringBuilder 字符串构建器
     */
    public static void importHeaderRecord(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage, Boolean hasValue, Boolean hasEnum) {
        stringBuilder
                .append("import com.baomidou.mybatisplus.annotation.FieldFill;\n")
                .append("import com.baomidou.mybatisplus.annotation.TableField;\n")
                .append("import com.baomidou.mybatisplus.annotation.TableId;\n")
                .append("import com.baomidou.mybatisplus.annotation.TableName;\n")
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".domain.entity.*;\n")
                .append(hasValue ? "import " + domainPackage + ".domain.value.*;\n" : "")
                .append(hasEnum ? "import " + domainPackage + ".domain.type.*;\n" : "")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import ").append(groupId).append(".atp.common.config.utils.id.*;\n")
                .append("import ").append(groupId).append(".atp.common.utils.model.decimal.Decimal;\n")
                .append("import lombok.*;\n")
                .append("import org.springframework.util.CollectionUtils;\n")
                .append("import org.springframework.util.StringUtils;\n\n")
                .append("import java.math.BigDecimal;\n")
                .append("import java.util.ArrayList;\n")
                .append("import java.util.List;\n")
                .append("import java.util.stream.Collectors;\n\n");
    }

    /**
     * 生成值对象头部Import代码模版
     * <p>TODO: 此部分应该根据实际继承包路径进行修改
     *
     * @param stringBuilder 字符串构建器
     */
    public static void importHeaderValue(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage, Boolean hasEnum) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append(hasEnum ? "import " + domainPackage + ".domain.type.*;\n" : "")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import ").append(groupId).append(".atp.common.utils.model.decimal.Decimal;\n")
                .append("import lombok.Builder;\n")
                .append("import lombok.Getter;\n")
                .append("import lombok.ToString;\n")
                .append("import org.springframework.util.CollectionUtils;\n")
                .append("import org.springframework.util.StringUtils;\n\n")
                .append("import java.math.BigDecimal;\n\n");
    }

    /**
     * 生成枚举类头部Import代码模版
     *
     * @param stringBuilder 字符串构建器
     */
    public static void importHeaderEnum(StringBuilder stringBuilder, String groupId, String servicePackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import lombok.Getter;\n")
                .append("import lombok.AllArgsConstructor;\n")
                .append("import lombok.ToString;\n\n");
    }

    public static void importHeaderRepository(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".domain.entity.*;\n\n");
    }

    public static void importHeaderQueryRepository(StringBuilder stringBuilder, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".domain.anticorruption.*;\n")
                .append("import ").append(domainPackage).append(".domain.entity.*;\n\n");
    }

    public static void importHeaderCommandHandler(StringBuilder stringBuilder, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".domain.entity.*;\n\n");
    }

    public static void importHeaderCommandHandlerImpl(StringBuilder stringBuilder, String groupId, String domainPackage, String subPackage) {
        String superSubPackage = removeSuffix(subPackage, ".impl");
        stringBuilder
                .append("import ").append(domainPackage).append(".").append(superSubPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".domain.anticorruption.*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(superSubPackage).append(".*;\n\n");
    }

    public static void importHeaderQueryHandler(StringBuilder stringBuilder, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".domain.entity.*;\n\n");
    }

    public static void importHeaderQueryHandlerImpl(StringBuilder stringBuilder, String groupId, String domainPackage, String subPackage) {
        String superSubPackage = removeSuffix(subPackage, ".impl");
        stringBuilder
                .append("import ").append(domainPackage).append(".").append(superSubPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".application.query.anticorruption.*;\n")
                .append("import ").append(domainPackage).append(".domain.anticorruption.*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(superSubPackage).append(".*;\n\n");
    }

    public static void importHeaderTransfer(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage, Boolean hasValue, Boolean hasEnum) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import ").append(groupId).append(".atp.common.utils.model.decimal.Decimal;\n")
                .append(hasValue ? "import " + domainPackage + ".domain.value.*;\n" : "")
                .append(hasEnum ? "import " + domainPackage + ".domain.type.*;\n" : "")
                .append("import com.alibaba.fastjson2.annotation.JSONType;\n")
                .append("import lombok.Builder;\n")
                .append("import lombok.Getter;\n")
                .append("import lombok.ToString;\n")
                .append("import org.springframework.util.CollectionUtils;\n")
                .append("import org.springframework.util.StringUtils;\n\n")
                .append("import java.math.BigDecimal;\n")
                .append("import java.util.ArrayList;\n")
                .append("import java.util.List;\n")
                .append("import java.util.stream.Collectors;\n\n");
    }

    public static void importHeaderDataManager(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".application.query.anticorruption.*;\n")
                .append("import ").append(domainPackage).append(".domain.anticorruption.*;\n")
                .append("import ").append(domainPackage).append(".domain.entity.*;\n")
                .append("import ").append(domainPackage).append(".infrastructure.data.persistence.mapper.*;\n")
                .append("import ").append(domainPackage).append(".infrastructure.data.record.*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import org.springframework.context.annotation.Lazy;\n")
                .append("import org.springframework.stereotype.Component;\n\n")
                .append("import java.util.List;\n")
                .append("import java.util.Optional;\n\n");
    }

    public static void importHeaderMapper(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".infrastructure.data.record.*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import org.apache.ibatis.annotations.Mapper;\n")
                .append("import org.springframework.context.annotation.Lazy;\n\n");
    }

    public static void importHeaderDrivingAdapter(StringBuilder stringBuilder, String servicePackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n\n");
    }

    public static void importHeaderSpringDrivingAdapter(StringBuilder stringBuilder, String groupId, String domainPackage, String subPackage) {
        String superSubPackage = removeSuffix(subPackage, ".spring");
        stringBuilder
                .append("import ").append(domainPackage).append(".").append(superSubPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".application.command.handler.*;\n")
                .append("import ").append(domainPackage).append(".application.query.handler.*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(superSubPackage).append(".*;\n\n");
    }

    public static void importHeaderDrivingConfiguration(StringBuilder stringBuilder, String groupId, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(domainPackage).append(".application.command.handler.*;\n")
                .append("import ").append(domainPackage).append(".application.command.handler.impl.*;\n")
                .append("import ").append(domainPackage).append(".application.query.handler.*;\n")
                .append("import ").append(domainPackage).append(".application.query.handler.impl.*;\n")
                .append("import ").append(domainPackage).append(".application.query.anticorruption.*;\n")
                .append("import ").append(domainPackage).append(".domain.anticorruption.*;\n")
                .append("import ").append(domainPackage).append(".facade.adapter.*;\n")
                .append("import ").append(domainPackage).append(".facade.adapter.spring.*;\n")
                .append("import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;\n")
                .append("import org.springframework.context.annotation.Bean;\n")
                .append("import org.springframework.context.annotation.Configuration;\n")
                .append("import org.springframework.context.annotation.Lazy;\n")
                .append("import org.springframework.context.annotation.Primary;\n\n");
    }

    public static void importHeaderController(StringBuilder stringBuilder, String groupId, String servicePackage, String domainPackage, String subPackage) {
        stringBuilder
                .append("import ").append(servicePackage).append(".common.model.").append(subPackage).append(".*;\n")
                .append("import ").append(domainPackage).append(".facade.adapter.*;\n")
                .append("import ").append(groupId).append(".atp.common.exception.model.").append(subPackage).append(".*;\n")
                .append("import org.springframework.context.annotation.Lazy;\n")
                .append("import org.springframework.web.bind.annotation.RequestMapping;\n")
                .append("import org.springframework.web.bind.annotation.RestController;\n\n");
    }
}
