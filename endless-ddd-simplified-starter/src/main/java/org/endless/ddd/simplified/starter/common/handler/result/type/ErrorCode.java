package org.endless.ddd.simplified.starter.common.handler.result.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.starter.common.exception.model.domain.type.EnumException;
import org.endless.ddd.simplified.starter.common.model.domain.type.BaseEnum;

/**
 * ErrorCode
 * <p>
 * create 2024/11/15 00:31
 * <p>
 * update 2024/11/15 00:31
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum ErrorCode implements BaseEnum {


    // 业务错误码
    SUCCESS("SUCCESS", "服务处理成功"),
    FAILURE("FAILURE", "服务处理失败"),
    UNKNOWN("UNKNOWN", "服务处理状态未知"),
    UN_AUTH("UN_AUTH", "身份认证失败"),
    BAD_REQ("BAD_REQ", "请求参数错误"),
    NOT_FND("NOT_FND", "未找到相关资源"),
    FORBIDN("FORBIDN", "服务访问被拒绝"),

    // DDD通用错误码
    // 聚合行为
    DAG0000("DAG0000", "聚合行为处理失败"),
    DAG9000("DAG9000", "聚合行为处理异常"),
    // 枚举
    DEM0000("DEM0000", "枚举处理失败"),

    // 命令处理器处理失败
    DCD0000("DCD0000", "命令处理失败"),
    DCD0001("DCD0001", "创建命令处理失败"),
    DCD0002("DCD0002", "修改命令处理失败"),
    DCD0003("DCD0003", "删除命令处理失败"),
    DCD0010("DCD0010", "命令未找到相关信息"),
    // 命令处理器状态未知
    DCD9000("DCD9000", "命令处理状态未知"),
    DCD9001("DCD9001", "创建命令处理状态未知"),
    DCD9002("DCD9002", "修改命令处理状态未知"),
    DCD9003("DCD9003", "删除命令处理状态未知"),

    // 查询处理器处理失败
    DQR0000("DQR0000", "查询处理失败"),
    DQR0001("DQR0001", "未查询到相关信息"),


    // 数据管理器处理失败
    DDM0000("DDM0000", "数据管理器处理失败"),
    DDM0001("DDM0001", "保存数据失败"),
    DDM0002("DDM0002", "修改数据失败"),
    DDM0003("DDM0003", "删除数据失败"),
    DDM0010("DDM0010", "查询数据失败"),
    DDM0011("DDM0011", "未查询到相关数据"),
    // 数据管理器状态未知
    DDM9000("DDM9000", "数据管理器处理状态未知"),
    DDM9001("DDM9001", "保存数据状态未知"),
    DDM9002("DDM9002", "修改数据状态未知"),
    DDM9003("DDM9003", "删除数据状态未知"),

    // 传输对象
    DTO0000("DTO0000", "传输对象为空"),
    DTO0001("DTO0001", "传输对象校验失败"),
    DTO0101("DTO0101", "命令传输对象校验失败"),
    DTO0201("DTO0201", "查询传输对象校验失败"),
    DTO0301("DTO0301", "被动传输对象校验失败"),
    // 请求传输对象
    DTS0000("DTS0000", "请求传输对象为空"),
    DTS0100("DTS0100", "命令请求传输对象为空"),
    DTS0200("DTS0200", "查询请求传输对象为空"),
    DTS0300("DTS0300", "被动请求传输对象为空"),
    // 响应传输对象
    DTR0000("DTR0000", "响应传输对象为空"),
    DTR0100("DTR0100", "命令响应传输对象为空"),
    DTR0200("DTR0200", "查询响应传输对象为空"),
    DTR0300("DTR0300", "被动响应传输对象为空"),

    // 工具类错误码
    UTL0000("UTL0000", "ID生成失败"),
    UTL0001("UTL0001", "雪花ID生成失败"),
    UTL0010("UTL0010", "精度数值计算错误"),
    UTL0011("UTL0011", "精度计算除数不能为0"),
    UTL0012("UTL0012", "精度数值为空"),
    UTL0013("UTL0013", "精度数值格式错误"),
    UTL0014("UTL0014", "精度数值超出范围"),
    UTL0022("UTL0022", "树结构处理失败"),

    // 安全模块错误码
    SEC0000("SEC0000", "初始化密码不能用于登录"),
    SEC0001("SEC0001", "用户名为空"),
    SEC0002("SEC0002", "密码为空"),
    SEC0003("SEC0003", "密码错误"),
    SEC0004("SEC0004", "密码格式错误"),
    SEC0010("SEC0010", "令牌请求无效"),
    SEC0011("SEC0011", "令牌生成失败"),
    SEC0012("SEC0012", "令牌刷新失败"),
    SEC0013("SEC0013", "令牌验证失败"),
    SEC0014("SEC0014", "令牌已过期"),
    SEC0020("SEC0020", "用户登出失败"),
    SEC0030("SEC0030", "HTTP指纹生成失败"),
    SEC0040("SEC0040", "认证请求参数错误"),
    SEC0050("SEC0050", "没有组织的访问权限"),
    SEC9020("SEC9020", "用户登出异常，状态未知"),

    // 身份认证模块错误码
    IDA0000("IDA0000", "客户未身份认证"),
    ;

    private final String code;

    private final String description;

    public static ErrorCode fromCode(String code) {
        for (ErrorCode type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new EnumException("未知的错误码: " + code);
    }
}
