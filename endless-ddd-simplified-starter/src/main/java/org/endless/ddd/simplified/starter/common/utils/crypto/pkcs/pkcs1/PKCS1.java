package org.endless.ddd.simplified.starter.common.utils.crypto.pkcs.pkcs1;

import org.endless.ddd.simplified.starter.common.exception.utils.crypto.PKCS1PaddingException;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * PKCS1
 * <p>
 * create 2024/11/18 20:22
 * <p>
 * update 2024/11/18 20:22
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class PKCS1 {

    private static final SecureRandom random = new SecureRandom();

    /**
     * PKCS#1 填充
     *
     * @param data      原始数据
     * @param blockSize 分组大小（通常为 256 字节，即 2048 位的密钥）
     */
    public static byte[] padding(byte[] data, Integer blockSize) {

        if (blockSize % 32 != 0) {
            throw new PKCS1PaddingException("分组大小必须为32的倍数");
        }
        // 原始数据长度
        int dataLength = data.length;
        // 填充后的总长度必须与密钥大小（blockSize）一致
        if (dataLength >= blockSize - 11) {
            throw new IllegalArgumentException("Data is too long to fit in the block size.");
        }

        int paddingLength = blockSize - dataLength - 3;
        byte[] padding = new byte[paddingLength + 3];

        padding[0] = 0x00; // 开始字节
        padding[1] = 0x02; // 填充类型字节

        // 填充随机字节
        random.nextBytes(padding);

        // 最后加上 0x00 字节分隔数据和填充内容
        padding[paddingLength + 2] = 0x00;

        // 创建最终的填充数据
        byte[] result = new byte[blockSize];
        System.arraycopy(padding, 0, result, 0, padding.length);
        System.arraycopy(data, 0, result, padding.length, dataLength);
        System.out.println(data.length);
        System.out.println(result.length);
        return result;
    }

    /**
     * 去除 PKCS#1 填充
     *
     * @param data 含填充的数据
     * @return 去除填充后的数据
     */
    public static byte[] remove(byte[] data) {
        int index = -1;
        // 查找填充数据的结束位置（0x00 字节）
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] == 0x00) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("Invalid PKCS#1 padding.");
        }
        // 返回去掉填充字节后的数据
        return Arrays.copyOfRange(data, index + 1, data.length);
    }
}
