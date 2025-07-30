package ${packageName};

<#-- 导入语句 -->
<#if imports??>
<#list imports as import>
import ${import};
</#list>
</#if>

<#-- 类注释 -->
/**
 * ${className}
 * <p>
 * create ${createTime}
 * <p>
 * update ${updateTime}
 *
 * @author ${author}
 * @since ${version}
 */
<#-- 类定义 -->
<#if superClassName??>
public class ${className} extends ${superClassName} {
<#else>
public class ${className} {
</#if>

<#-- 字段定义 -->
<#if fields??>
<#list fields as field>
    /**
     * ${field.description}
     */
    private ${field.type} ${field.name};
</#list>
</#if>

<#-- 构造函数 -->
<#if fields??>
    public ${className}() {
    }

    public ${className}(<#list fields as field>${field.type} ${field.name}<#if field_has_next>, </#if></#list>) {
<#list fields as field>
        this.${field.name} = ${field.name};
</#list>
    }
</#if>

<#-- Getter和Setter方法 -->
<#if fields??>
<#list fields as field>
    public ${field.type} get${field.name?cap_first}() {
        return ${field.name};
    }

    public void set${field.name?cap_first}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }
</#list>
</#if>

<#-- 业务方法 -->
<#if businessMethods??>
<#list businessMethods as method>
    ${method}
</#list>
</#if>

<#-- 验证方法 -->
<#if validateMethods??>
<#list validateMethods as method>
    ${method}
</#list>
</#if>

    @Override
    public String toString() {
        return "${className}{" +
<#if fields??>
<#list fields as field>
                "${field.name}=" + ${field.name} +<#if field_has_next> "," +</#if>
</#list>
</#if>
                "}";
    }
}
