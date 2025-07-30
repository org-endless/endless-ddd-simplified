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
public interface ${className} {

<#-- 基本CRUD方法 -->
    /**
     * 保存${entityDescription}
     *
     * @param ${entityName?uncap_first} ${entityDescription}
     * @return 保存后的${entityDescription}
     */
    ${entityName} save(${entityName} ${entityName?uncap_first});

    /**
     * 根据ID查找${entityDescription}
     *
     * @param id ID
     * @return ${entityDescription}
     */
    ${entityName} findById(String id);

    /**
     * 根据ID查找${entityDescription}（不抛出异常）
     *
     * @param id ID
     * @return ${entityDescription}，如果不存在返回null
     */
    ${entityName} findByIdOrNull(String id);

    /**
     * 根据ID删除${entityDescription}
     *
     * @param id ID
     */
    void deleteById(String id);

    /**
     * 检查${entityDescription}是否存在
     *
     * @param id ID
     * @return 是否存在
     */
    boolean existsById(String id);

<#-- 查询方法 -->
<#if queryMethods??>
<#list queryMethods as method>
    /**
     * ${method.description}
     *
<#if method.parameters??>
<#list method.parameters as param>
     * @param ${param.name} ${param.description}
</#list>
</#if>
     * @return ${method.returnDescription}
     */
    ${method.returnType} ${method.name}(<#if method.parameters??><#list method.parameters as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list></#if>);

</#list>
</#if>
} 