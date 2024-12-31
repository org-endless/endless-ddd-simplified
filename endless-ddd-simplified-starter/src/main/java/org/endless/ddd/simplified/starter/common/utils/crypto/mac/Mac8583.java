package org.endless.ddd.simplified.starter.common.utils.crypto.mac;

/**
 * Mac8583
 * <p>
 * create 2024/11/18 15:05
 * <p>
 * update 2024/11/18 15:05
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
public class Mac8583 {

    /**
     * 生成Mac值 (Base64编码)
     *
     * @param message    待生成Mac值的消息 (Base64编码)
     * @param privateKey 私钥 (Base64编码)
     * @return {@link String } Mac值 (Base64编码)
     */
    public static String generate(String message, String privateKey) {
        return "mac8583";
    }
}
