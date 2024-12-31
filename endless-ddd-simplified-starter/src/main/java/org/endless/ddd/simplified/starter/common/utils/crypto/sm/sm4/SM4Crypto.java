package org.endless.ddd.simplified.starter.common.utils.crypto.sm.sm4;

import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.Sm4CryptoException;
import org.endless.ddd.simplified.starter.common.utils.crypto.pkcs7.Pkcs7;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * SM4Crypto
 * <p>
 * create 2024/11/16 01:25
 * <p>
 * update 2024/11/16 01:25
 *
 * @author Deng Haozhi
 * @since 2.0.0
 */
@Getter
@Builder
public class SM4Crypto {

    /**
     * SM4 密钥长度为 16 字节
     */
    private static final Integer SM4_KEY_SIZE = 16;

    /**
     * SM4 分组长度为 16 字节
     */
    private static final Integer SM4_BLOCK_SIZE = 16;

    /**
     * SM4 秘钥 (Base64编码)
     */
    private final String key;

    /**
     * 生成 SM4 密钥
     *
     * @return Base64 编码的密钥
     */
    public static SM4Crypto key() {
        try {
            byte[] keyBytes = new byte[SM4_KEY_SIZE];
            SecureRandom random = new SecureRandom();
            random.nextBytes(keyBytes);
            return SM4Crypto.builder()
                    .key(Base64.getEncoder().encodeToString(keyBytes))
                    .build();
        } catch (Exception e) {
            throw new Sm4CryptoException("密钥生成异常" + e.getMessage(), e);
        }
    }

    /**
     * 加密
     *
     * @param plaintext 明文 (Base64 编码)
     * @param key       密钥 (Base64 编码)
     * @return 加密后的密文 (Base64 编码)
     */
    public static String encrypt(String plaintext, String key) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] plaintextBytes = Pkcs7.padding(Base64.getDecoder().decode(plaintext), SM4_BLOCK_SIZE);
            byte[] encryptedBytes = crypto(true, plaintextBytes, keyBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new Sm4CryptoException("加密异常" + e.getMessage(), e);
        }
    }

    /**
     * 解密
     *
     * @param ciphertext 密文 (Base64 编码)
     * @param key        密钥 (Base64 编码)
     * @return 解密后的明文 (Base64 编码)
     */
    public static String decrypt(String ciphertext, String key) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            byte[] decryptedBytes = crypto(false, ciphertextBytes, keyBytes);
            return Base64.getEncoder().encodeToString(Pkcs7.remove(decryptedBytes));
        } catch (Sm4CryptoException e) {
            throw e;
        } catch (Exception e) {
            throw new Sm4CryptoException("解密异常" + e.getMessage(), e);
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
    private static byte[] crypto(Boolean isEncrypt, byte[] data, byte[] key) {
        try {
            SM4Engine engine = new SM4Engine();
            engine.init(isEncrypt, new KeyParameter(key));
            if (key.length != SM4_KEY_SIZE) {
                if (isEncrypt) {
                    throw new Sm4CryptoException("加密的秘钥长度必须为 16 字节");
                } else {
                    throw new Sm4CryptoException("解密的秘钥长度必须为 16 字节");
                }
            }
            if (data.length % SM4_BLOCK_SIZE != 0) {
                if (isEncrypt) {
                    throw new Sm4CryptoException("加密的数据长度必须为 16 的倍数");
                } else {
                    throw new Sm4CryptoException("解密的数据长度必须为 16 的倍数");
                }
            }
            byte[] result = new byte[data.length];
            for (int i = 0; i < data.length; i += SM4_BLOCK_SIZE) {
                engine.processBlock(data, i, result, i);
            }
            return result;
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new Sm4CryptoException("秘钥或数据格式有误" + e.getMessage(), e);
        } catch (Sm4CryptoException e) {
            throw e;
        } catch (Exception e) {
            if (isEncrypt) {
                throw new Sm4CryptoException("加密异常" + e.getMessage(), e);
            } else {
                throw new Sm4CryptoException("解密异常" + e.getMessage(), e);
            }
        }
    }


}
