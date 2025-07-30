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
@Getter
@ToString
public class ${className} extends ${superClassName} {

<#-- 字段定义 -->
<#if fields??>
<#list fields as field>
    /**
     * ${field.description}
     */
    private ${field.type} ${field.name};
</#list>
</#if>

<#-- 创建方法 -->
    public static ${className} create(${className}Builder builder) {
        return builder
<#if fields??>
<#list fields as field>
<#if field.name == "id">
                .${field.name}(IdGenerator.of())
<#elseif field.name == "isRemoved">
                .${field.name}(false)
<#elseif field.name == "modifyUserId">
                .${field.name}(builder.createUserId)
<#elseif field.type?starts_with("List<")>
                .${field.name}(builder.${field.name} == null ? new ArrayList<>() : new ArrayList<>(builder.${field.name}))
<#else>
                .${field.name}(builder.${field.name})
</#if>
</#list>
</#if>
            .innerBuild()
            .validate();
    }

<#-- 删除方法 -->
    public ${className} remove(String modifyUserId) {
        if (this.isRemoved) {
            throw new AggregateRemoveException("已经被删除的聚合根<${description}>不能再次删除, ID: " + ${idField});
        }
        
        if (!canRemove()) {
            throw new AggregateRemoveException("聚合根<${description}>处于不可删除状态, ID: " + ${idField});
        }
        
        this.isRemoved = true;
        this.modifyUserId = modifyUserId;
        this.modifyTime = LocalDateTime.now();
        
        return this;
    }

<#-- 添加项目方法 -->
<#if fields??>
<#list fields as field>
<#if field.type?starts_with("List<")>
    public ${className} add${field.name?cap_first}(${field.type?replace("List<", "")?replace(">", "")} item) {
        if (item == null) {
            throw new IllegalArgumentException("${field.description}不能为空");
        }
        if (this.${field.name} == null) {
            this.${field.name} = new ArrayList<>();
        }
        this.${field.name}.add(item);
        return this;
    }

    public ${className} addAll${field.name?cap_first}(List<${field.type?replace("List<", "")?replace(">", "")}> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("${field.description}列表不能为空");
        }
        if (this.${field.name} == null) {
            this.${field.name} = new ArrayList<>();
        }
        this.${field.name}.addAll(items);
        return this;
    }

    public ${className} remove${field.name?cap_first}(${field.type?replace("List<", "")?replace(">", "")} item) {
        if (this.${field.name} != null) {
            this.${field.name}.remove(item);
        }
        return this;
    }
</#if>
</#list>
</#if>

<#-- 验证方法 -->
    public void validate() {
<#if fields??>
<#list fields as field>
<#if !field.nullable>
        if (StringUtils.isEmpty(this.${field.name})) {
            throw new IllegalArgumentException("${field.description}不能为空");
        }
</#if>
</#list>
</#if>
    }

    public boolean canRemove() {
        // 实现删除条件判断逻辑
        return true;
    }

<#-- Builder类 -->
    @Builder
    public static class ${className}Builder {
<#if fields??>
<#list fields as field>
        private ${field.type} ${field.name};
</#list>
</#if>

        public ${className} innerBuild() {
            return new ${className}(
<#if fields??>
<#list fields as field>
                ${field.name}<#if field_has_next>,</#if>
</#list>
</#if>
            );
        }
    }
} 