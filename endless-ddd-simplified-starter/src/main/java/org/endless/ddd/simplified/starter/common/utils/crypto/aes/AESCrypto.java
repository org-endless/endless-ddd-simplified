package org.endless.ddd.simplified.starter.common.utils.crypto.aes;

import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.AESDecryptException;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.AESEncryptException;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.AESKeyPairException;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * AESCrypto
 * <p>
 * create 2025/03/01 20:14
 * <p>
 * update 2025/03/01 20:14
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
public class AESCrypto {

    /**
     * 秘钥字符集
     */
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@$&*()_=+[]{};:'\",.<>?/";

    /**
     * AES 密钥长度为 16 字节
     */
    private static final Integer AES_KEY_SIZE = 16;

    /**
     * AES 分组长度为 16 字节
     */
    private static final Integer AES_BLOCK_SIZE = 16;

    /**
     * AES 秘钥 (Base64编码)
     */
    private final String key;

    /**
     * 生成 AES 密钥
     *
     * @return Base64 编码的密钥
     */
    public static AESCrypto key() {
        try {
            byte[] keyBytes = new byte[AES_KEY_SIZE];
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            secureRandom.nextBytes(keyBytes);
            return AESCrypto.builder()
                    .key(Base64.getEncoder().encodeToString(keyBytes))
                    .build();
        } catch (Exception e) {
            throw new AESKeyPairException(e.getMessage(), e);
        }
    }

    public static AESCrypto stringKey() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            StringBuilder keyBuilder = new StringBuilder(AES_KEY_SIZE);

            for (int i = 0; i < AES_KEY_SIZE; i++) {
                int index = secureRandom.nextInt(CHARSET.length());
                keyBuilder.append(CHARSET.charAt(index));
            }
            return AESCrypto.builder()
                    .key(Base64.getEncoder().encodeToString(keyBuilder.toString().getBytes()))
                    .build();
        } catch (Exception e) {
            throw new AESKeyPairException(e.getMessage(), e);
        }
    }

    /**
     * 加密
     *
     * @param plaintext 明文 (Base64 编码)
     * @param key       密钥 (Base64 编码)
     * @param iv        初始向量 (Base64 编码)
     * @param isPadding 是否需要填充
     * @return 加密后的密文 (Base64 编码)
     */
    public static String encrypt(String plaintext, String key, String iv, Boolean isPadding) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] ivBytes = Base64.getDecoder().decode(iv);
            byte[] plaintextBytes = Base64.getDecoder().decode(plaintext);
            byte[] encryptedBytes = crypto(true, plaintextBytes, keyBytes, ivBytes, isPadding);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (AESEncryptException e) {
            throw e;
        } catch (Exception e) {
            throw new AESEncryptException(e.getMessage(), e);
        }
    }

    /**
     * 解密
     *
     * @param ciphertext 密文 (Base64 编码)
     * @param key        密钥 (Base64 编码)
     * @param iv         初始向量 (Base64 编码)
     * @param isPadding  是否需要去除填充
     * @return 解密后的明文 (Base64 编码)
     */
    public static String decrypt(String ciphertext, String key, String iv, Boolean isPadding) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] ivBytes = Base64.getDecoder().decode(iv);
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            byte[] decryptedBytes = crypto(false, ciphertextBytes, keyBytes, ivBytes, isPadding);
            return Base64.getEncoder().encodeToString(decryptedBytes);
        } catch (AESDecryptException e) {
            throw e;
        } catch (Exception e) {
            throw new AESDecryptException(e.getMessage(), e);
        }
    }

    /**
     * 加解密
     *
     * @param isEncrypt 是否加密
     * @param data      数据
     * @param key       密钥
     * @return 加解密后的结果
     */
    private static byte[] crypto(Boolean isEncrypt, byte[] data, byte[] key, byte[] iv, Boolean isPadding) throws InvalidCipherTextException {
        if (key.length != AES_KEY_SIZE) {
            if (isEncrypt) {
                throw new AESEncryptException("加密的秘钥长度必须为 " + AES_KEY_SIZE + " 字节");
            } else {
                throw new AESDecryptException("解密的秘钥长度必须为 " + AES_KEY_SIZE + " 字节");
            }
        }
        // 处理CBC
        BlockCipher blockCipher;
        CipherParameters parameters;
        if (iv.length == AES_KEY_SIZE) {
            blockCipher = CBCBlockCipher.newInstance(AESEngine.newInstance());
            parameters = new ParametersWithIV(new KeyParameter(key), iv);
        } else {
            blockCipher = AESEngine.newInstance();
            parameters = new KeyParameter(key);
        }
        // 处理填充
        BufferedBlockCipher cipher;
        if (isPadding) {
            cipher = new PaddedBufferedBlockCipher(blockCipher);
        } else if (data.length % AES_BLOCK_SIZE != 0) {
            cipher = new PaddedBufferedBlockCipher(blockCipher, new ZeroBytePadding());
        } else {
            cipher = new DefaultBufferedBlockCipher(blockCipher);
        }

        cipher.init(isEncrypt, parameters);
        byte[] result = new byte[cipher.getOutputSize(data.length)];
        int len = cipher.processBytes(data, 0, data.length, result, 0);
        len += cipher.doFinal(result, len);
        byte[] finalResult = new byte[len];
        System.arraycopy(result, 0, finalResult, 0, len);
        return finalResult;
    }
}
