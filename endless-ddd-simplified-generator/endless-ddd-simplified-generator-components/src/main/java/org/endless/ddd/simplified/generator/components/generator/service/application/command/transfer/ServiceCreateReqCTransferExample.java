package org.endless.ddd.simplified.generator.components.generator.service.application.command.transfer;

import org.endless.ddd.simplified.starter.common.exception.model.application.command.transfer.CommandTransferValidateException;

import java.util.Arrays;
import java.util.Collections;

/**
 * ServiceCreateReqCTransfer 使用示例
 * 
 * @author Deng Haozhi
 * @since 0.0.1
 */
public class ServiceCreateReqCTransferExample {

    public static void main(String[] args) {
        // 示例1：正常情况
        System.out.println("=== 示例1：正常校验 ===");
        try {
            ServiceCreateReqCTransfer validTransfer = ServiceCreateReqCTransfer.builder()
                    .serviceArtifactId("user-service")
                    .projectArtifactId("my-project")
                    .name("用户服务")
                    .description("用户管理服务，提供用户注册、登录等功能")
                    .version("1.0.0")
                    .author("张三")
                    .rootPath("D:\\projects\\my-project")
                    .basePackage("com.example.userservice")
                    .type("REST")
                    .port(8080)
                    .contextNames(Arrays.asList("UserContext", "AuthContext"))
                    .build();

            validTransfer.validate();
            System.out.println("✓ 校验通过");
        } catch (CommandTransferValidateException e) {
            System.err.println("✗ 校验失败: " + e.getMessage());
        }

        // 示例2：服务构件ID为空
        System.out.println("\n=== 示例2：服务构件ID为空 ===");
        try {
            ServiceCreateReqCTransfer invalidTransfer = ServiceCreateReqCTransfer.builder()
                    .serviceArtifactId("")
                    .projectArtifactId("my-project")
                    .name("用户服务")
                    .description("用户管理服务")
                    .version("1.0.0")
                    .author("张三")
                    .rootPath("D:\\projects\\my-project")
                    .basePackage("com.example.userservice")
                    .type("REST")
                    .port(8080)
                    .contextNames(Arrays.asList("UserContext"))
                    .build();

            invalidTransfer.validate();
            System.out.println("✓ 校验通过");
        } catch (CommandTransferValidateException e) {
            System.err.println("✗ 校验失败: " + e.getMessage());
        }

        // 示例3：服务构件ID格式错误
        System.out.println("\n=== 示例3：服务构件ID格式错误 ===");
        try {
            ServiceCreateReqCTransfer invalidTransfer = ServiceCreateReqCTransfer.builder()
                    .serviceArtifactId("user@service")
                    .projectArtifactId("my-project")
                    .name("用户服务")
                    .description("用户管理服务")
                    .version("1.0.0")
                    .author("张三")
                    .rootPath("D:\\projects\\my-project")
                    .basePackage("com.example.userservice")
                    .type("REST")
                    .port(8080)
                    .contextNames(Arrays.asList("UserContext"))
                    .build();

            invalidTransfer.validate();
            System.out.println("✓ 校验通过");
        } catch (CommandTransferValidateException e) {
            System.err.println("✗ 校验失败: " + e.getMessage());
        }

        // 示例4：服务版本格式错误
        System.out.println("\n=== 示例4：服务版本格式错误 ===");
        try {
            ServiceCreateReqCTransfer invalidTransfer = ServiceCreateReqCTransfer.builder()
                    .serviceArtifactId("user-service")
                    .projectArtifactId("my-project")
                    .name("用户服务")
                    .description("用户管理服务")
                    .version("1.0")
                    .author("张三")
                    .rootPath("D:\\projects\\my-project")
                    .basePackage("com.example.userservice")
                    .type("REST")
                    .port(8080)
                    .contextNames(Arrays.asList("UserContext"))
                    .build();

            invalidTransfer.validate();
            System.out.println("✓ 校验通过");
        } catch (CommandTransferValidateException e) {
            System.err.println("✗ 校验失败: " + e.getMessage());
        }

        // 示例5：服务端口超出范围
        System.out.println("\n=== 示例5：服务端口超出范围 ===");
        try {
            ServiceCreateReqCTransfer invalidTransfer = ServiceCreateReqCTransfer.builder()
                    .serviceArtifactId("user-service")
                    .projectArtifactId("my-project")
                    .name("用户服务")
                    .description("用户管理服务")
                    .version("1.0.0")
                    .author("张三")
                    .rootPath("D:\\projects\\my-project")
                    .basePackage("com.example.userservice")
                    .type("REST")
                    .port(80)
                    .contextNames(Arrays.asList("UserContext"))
                    .build();

            invalidTransfer.validate();
            System.out.println("✓ 校验通过");
        } catch (CommandTransferValidateException e) {
            System.err.println("✗ 校验失败: " + e.getMessage());
        }

        // 示例6：限界上下文名称重复
        System.out.println("\n=== 示例6：限界上下文名称重复 ===");
        try {
            ServiceCreateReqCTransfer invalidTransfer = ServiceCreateReqCTransfer.builder()
                    .serviceArtifactId("user-service")
                    .projectArtifactId("my-project")
                    .name("用户服务")
                    .description("用户管理服务")
                    .version("1.0.0")
                    .author("张三")
                    .rootPath("D:\\projects\\my-project")
                    .basePackage("com.example.userservice")
                    .type("REST")
                    .port(8080)
                    .contextNames(Arrays.asList("UserContext", "UserContext"))
                    .build();

            invalidTransfer.validate();
            System.out.println("✓ 校验通过");
        } catch (CommandTransferValidateException e) {
            System.err.println("✗ 校验失败: " + e.getMessage());
        }

        // 示例7：所有有效服务类型
        System.out.println("\n=== 示例7：所有有效服务类型 ===");
        String[] validTypes = { "REST", "GRPC", "WEBSOCKET", "MESSAGE" };
        for (String type : validTypes) {
            try {
                ServiceCreateReqCTransfer validTransfer = ServiceCreateReqCTransfer.builder()
                        .serviceArtifactId("user-service")
                        .projectArtifactId("my-project")
                        .name("用户服务")
                        .description("用户管理服务")
                        .version("1.0.0")
                        .author("张三")
                        .rootPath("D:\\projects\\my-project")
                        .basePackage("com.example.userservice")
                        .type(type)
                        .port(8080)
                        .contextNames(Arrays.asList("UserContext"))
                        .build();

                validTransfer.validate();
                System.out.println("✓ " + type + " 类型校验通过");
            } catch (CommandTransferValidateException e) {
                System.err.println("✗ " + type + " 类型校验失败: " + e.getMessage());
            }
        }

        System.out.println("\n=== 校验方法功能总结 ===");
        System.out.println("1. 服务构件ID：必填，格式为字母数字连字符下划线，长度3-50字符");
        System.out.println("2. 项目构件ID：必填，格式为字母数字连字符下划线，长度3-50字符");
        System.out.println("3. 服务名称：必填，支持中文英文数字空格连字符下划线，长度2-100字符");
        System.out.println("4. 服务描述：必填，长度5-500字符");
        System.out.println("5. 服务版本：必填，格式为x.y.z或x.y.z-SNAPSHOT");
        System.out.println("6. 服务作者：必填，长度2-50字符");
        System.out.println("7. 服务根路径：必填，支持Windows和Unix路径格式");
        System.out.println("8. 服务基础包名：必填，小写字母数字点分隔，长度5-100字符");
        System.out.println("9. 服务类型：必填，支持REST/GRPC/WEBSOCKET/MESSAGE");
        System.out.println("10. 服务端口：必填，范围1024-65535");
        System.out.println("11. 限界上下文名称：必填列表，字母开头，字母数字下划线，长度2-50字符，不能重复");
    }
}