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
public enum ${className} {

<#-- 枚举值定义 -->
<#if enumValues??>
<#list enumValues as enumValue>
    ${enumValue.code}("${enumValue.code}", "${enumValue.description}")<#if enumValue_has_next>,<#else>;</#if>
</#list>
</#if>

<#-- 字段定义 -->
<#if fields??>
<#list fields as field>
    private final ${field.type} ${field.name};
</#list>
</#if>

<#-- 构造函数 -->
    ${className}(<#if fields??><#list fields as field>${field.type} ${field.name}<#if field_has_next>, </#if></#list></#if>) {
<#if fields??>
<#list fields as field>
        this.${field.name} = ${field.name};
</#list>
</#if>
    }

<#-- Getter方法 -->
<#if fields??>
<#list fields as field>
    public ${field.type} get${field.name?cap_first}() {
        return ${field.name};
    }
</#list>
</#if>

<#-- 根据代码获取枚举值的方法 -->
    public static ${className} fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (${className} value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("未知的${description}代码: " + code);
    }

    public static ${className} fromCodeOrDefault(String code, ${className} defaultValue) {
        if (code == null) {
            return defaultValue;
        }
        for (${className} value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return defaultValue;
    }
} 