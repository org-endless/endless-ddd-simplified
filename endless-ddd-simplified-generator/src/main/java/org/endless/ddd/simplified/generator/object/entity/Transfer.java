package org.endless.ddd.simplified.generator.object.entity;

import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.endless.ddd.simplified.generator.object.type.AdapterType;
import org.endless.ddd.simplified.generator.object.type.CQRSType;
import org.endless.ddd.simplified.generator.object.type.MessageType;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Transfer
 * <p>
 * create 2024/10/23 09:33
 * <p>
 * update 2024/10/23 09:33
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
@JSONType(orders = {"name", "adapterType", "cqrsType", "messageType", "description", "fields"})
public class Transfer {

    private final String name;

    private final AdapterType adapterType;

    private final CQRSType cqrsType;

    private final MessageType messageType;

    private final String description;

    private final List<Field> fields;

    public Transfer validate() {
        validateName();
        validateAdapterType();
        validateCQRSType();
        validateMessageType();
        validateDescription();
        validateFields();
        return this;
    }

    private void validateName() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("传输对象名称不能为空，当前值为: " + name);
        }
        if (adapterType == AdapterType.DRIVING) {
            if (cqrsType == CQRSType.QUERY && messageType == MessageType.REQUEST && !name.endsWith("ReqQTransfer")) {
                throw new IllegalArgumentException("查询请求传输对象名称必须以 ReqQTransfer 结尾, 输入的是: " + name);
            } else if (cqrsType == CQRSType.QUERY && messageType == MessageType.RESPONSE && !name.endsWith("RespQTransfer")) {
                throw new IllegalArgumentException("查询响应传输对象名称必须以 RespQTransfer 结尾, 输入的是: " + name);
            } else if (cqrsType == CQRSType.COMMAND && messageType == MessageType.REQUEST && !name.endsWith("ReqCTransfer")) {
                throw new IllegalArgumentException("命令请求传输对象名称必须以 ReqCTransfer 结尾, 输入的是: " + name);
            } else if (cqrsType == CQRSType.COMMAND && messageType == MessageType.RESPONSE && !name.endsWith("RespCTransfer")) {
                throw new IllegalArgumentException("命令响应传输对象名称必须以 RespCTransfer 结尾, 输入的是: " + name);
            }
        } else {
            if (messageType == MessageType.REQUEST && !name.endsWith("ReqDTransfer")) {
                throw new IllegalArgumentException("被动适配器请求传输对象名称必须以 ReqDTransfer 结尾, 输入的是: " + name);
            } else if (messageType == MessageType.RESPONSE && !name.endsWith("RespDTransfer")) {
                throw new IllegalArgumentException("被动适配器响应传输对象名称必须以 RespDTransfer 结尾, 输入的是: " + name);
            }
        }
    }

    private void validateAdapterType() {
        if (adapterType == null) {
            throw new IllegalArgumentException("传输对象适配器类型不能为空");
        }
    }

    private void validateCQRSType() {
        if (cqrsType == null) {
            throw new IllegalArgumentException("传输对象CQRS类型不能为空");
        }
    }

    private void validateMessageType() {
        if (messageType == null) {
            throw new IllegalArgumentException("传输对象消息类型不能为空");
        }
    }

    private void validateDescription() {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("传输对象描述不能为空，当前值为: " + description);
        }
        if (adapterType == AdapterType.DRIVING) {
            if (cqrsType == CQRSType.QUERY && messageType == MessageType.REQUEST && !description.endsWith("查询请求传输对象")) {
                throw new IllegalArgumentException("查询请求传输对象描述必须以 \"查询请求传输对象\" 结尾, 输入的是: " + description);
            } else if (cqrsType == CQRSType.QUERY && messageType == MessageType.RESPONSE && !description.endsWith("查询响应传输对象")) {
                throw new IllegalArgumentException("查询响应传输对象描述必须以 \"查询响应传输对象\" 结尾, 输入的是: " + description);
            } else if (cqrsType == CQRSType.COMMAND && messageType == MessageType.REQUEST && !description.endsWith("命令请求传输对象")) {
                throw new IllegalArgumentException("命令请求传输对象描述必须以 \"命令请求传输对象\" 结尾, 输入的是: " + description);
            } else if (cqrsType == CQRSType.COMMAND && messageType == MessageType.RESPONSE && !description.endsWith("命令响应传输对象")) {
                throw new IllegalArgumentException("命令响应传输对象描述必须以 \"命令响应传输对象\" 结尾, 输入的是: " + description);
    }
        } else {
            if (messageType == MessageType.REQUEST && !description.endsWith("被动请求传输对象")) {
                throw new IllegalArgumentException("被动适配器请求传输对象描述必须以 \"被动请求传输对象\" 结尾, 输入的是: " + description);
            } else if (messageType == MessageType.RESPONSE && !description.endsWith("被动响应传输对象")) {
                throw new IllegalArgumentException("被动适配器响应传输对象描述必须以 \"被动响应传输对象\" 结尾, 输入的是: " + description);
            }
        }
    }

    private void validateFields() {
        if (fields != null && !fields.isEmpty()) {
            fields.forEach(Field::validate);
        } else {
            throw new IllegalArgumentException("传输对象字段不能为空");
        }
    }
}
