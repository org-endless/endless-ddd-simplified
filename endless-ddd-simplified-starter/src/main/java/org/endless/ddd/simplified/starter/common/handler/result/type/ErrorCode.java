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

    // DDD通用错误码
    // 命令处理失败
    DCD0000("DCD0000", "命令处理失败"),
    DCD0001("DCD0001", "创建命令处理失败"),
    DCD0002("DCD0002", "修改命令处理失败"),
    DCD0003("DCD0003", "删除命令处理失败"),

    // 命令状态未知
    DCD9000("DCD9000", "命令处理状态未知"),
    DCD9001("DCD9001", "创建命令处理状态未知"),
    DCD9002("DCD9002", "修改命令处理状态未知"),
    DCD9003("DCD9003", "删除命令处理状态未知"),

    // 请求传输对象
    DTS0000("DTS0000", "请求传输对象处理失败"),
    DTS0001("DTS0001", "请求传输对象校验失败"),
    DTS0100("DTS0100", "命令请求传输对象处理失败"),
    DTS0101("DTS0101", "命令请求传输对象校验失败"),
    DTS0200("DTS0200", "查询请求传输对象处理失败"),
    DTS0201("DTS0201", "查询请求传输对象校验失败"),
    DTS0300("DTS0300", "被动请求传输对象处理失败"),
    DTS0301("DTS0301", "被动请求传输对象校验失败"),

    // 响应传输对象
    DTR0000("DTR0000", "响应传输对象处理失败"),
    DTR0001("DTR0001", "响应传输对象校验失败"),
    DTR0100("DTR0100", "命令响应传输对象处理失败"),
    DTR0101("DTR0101", "命令响应传输对象校验失败"),
    DTR0200("DTR0200", "查询响应传输对象处理失败"),
    DTR0201("DTR0201", "查询响应传输对象校验失败"),
    DTR0300("DTR0300", "被动响应传输对象处理失败"),
    DTR0301("DTR0301", "被动响应传输对象校验失败"),
    DAG0000("DAG0000", "聚合行为处理失败"),
    DAG9000("DAG9000", "聚合行为处理异常"),

    // 工具类错误码
    UTL0000("UTL0000", "ID生成失败"),
    UTL0001("UTL0001", "雪花ID生成失败"),
    UTL0012("UTL0012", "树结构处理失败"),

    // 安全模块错误码
    SEC0000("SEC0000", "初始化密码不能用于登录"),
    SEC0010("SEC0010", "令牌请求无效"),
    SEC0011("SEC0011", "令牌生成失败"),
    SEC0012("SEC0012", "令牌刷新失败"),
    SEC0013("SEC0013", "令牌验证失败"),
    SEC0014("SEC0014", "令牌已过期"),
    SEC0020("SEC0020", "用户登出失败"),
    SEC0030("SEC0030", "HTTP指纹生成失败"),
    SEC9020("SEC0020", "用户登出异常，状态未知"),

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
