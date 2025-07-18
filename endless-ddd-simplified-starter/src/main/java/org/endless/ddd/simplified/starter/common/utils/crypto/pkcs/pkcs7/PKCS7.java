package org.endless.ddd.simplified.starter.common.utils.crypto.pkcs.pkcs7;

import java.util.Arrays;

/**
 * PKCS7Padding
 * <p>
 * create 2024/11/18 20:22
 * <p>
 * update 2024/11/18 20:22
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class PKCS7 {

    /**
     * PKCS7Padding 填充
     *
     * @param data      原始数据
     * @param blockSize 分组大小 (通常为 16 字节)
     * @return 补齐后的数据
     */
    public static byte[] padding(byte[] data, Integer blockSize) {
        int paddingLength = blockSize - (data.length % blockSize);
        byte[] padding = new byte[paddingLength];
        Arrays.fill(padding, (byte) paddingLength); // 填充的字节值为填充长度
        byte[] result = new byte[data.length + paddingLength];
        System.arraycopy(data, 0, result, 0, data.length);
        System.arraycopy(padding, 0, result, data.length, paddingLength);
        return result;
    }

    /**
     * 去除 PKCS7Padding 填充
     *
     * @param data 含填充的数据
     * @return 去除填充后的数据
     */
    public static byte[] remove(byte[] data) {
        int paddingLength = data[data.length - 1];
        return Arrays.copyOf(data, data.length - paddingLength);
    }
}
