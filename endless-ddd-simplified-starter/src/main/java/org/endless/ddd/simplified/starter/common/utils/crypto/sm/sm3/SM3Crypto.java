package org.endless.ddd.simplified.starter.common.utils.crypto.sm.sm3;

import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.Sm3CryptoException;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * SM3Crypto
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
public class SM3Crypto {

    /**
     * 盐值长度
     */
    private static final Integer SALT_LENGTH = 16;

    /**
     * 盐值 (Base64 编码)
     */
    private final String salt;

    /**
     * 生成盐值
     *
     * @return 随机盐值 (Base64 编码)
     */
    public static SM3Crypto salt() {
        try {
            byte[] saltBytes = new byte[SALT_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(saltBytes);
            return SM3Crypto.builder()
                    .salt(Base64.getEncoder().encodeToString(saltBytes))
                    .build();
        } catch (Exception e) {
            throw new Sm3CryptoException("盐值生成异常", e);
        }
    }

    /**
     * 生成带盐值的 SM3 哈希
     *
     * @param plaintext 明文 (Base64 编码)
     * @return SM3 哈希值 (Base64 编码)
     */
    public static String hash(String plaintext, String salt) {
        try {
            byte[] plaintextBytes = Base64.getDecoder().decode(plaintext);
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            SM3Digest digest = new SM3Digest();
            byte[] combinedBytes = new byte[plaintextBytes.length + saltBytes.length];
            System.arraycopy(plaintextBytes, 0, combinedBytes, 0, plaintextBytes.length);
            System.arraycopy(saltBytes, 0, combinedBytes, plaintextBytes.length, saltBytes.length);
            digest.update(combinedBytes, 0, combinedBytes.length);
            byte[] resultBytes = new byte[digest.getDigestSize()];
            digest.doFinal(resultBytes, 0);
            return Base64.getEncoder().encodeToString(resultBytes) + ":" + Base64.getEncoder().encodeToString(saltBytes);
        } catch (Exception e) {
            throw new Sm3CryptoException("哈希生成异常", e);
        }
    }

    /**
     * 验证带盐值的 SM3 哈希
     *
     * @param plaintext  明文 (Base64 编码)
     * @param hashedText 哈希值 (Base64 编码)
     * @return 是否验证通过
     */
    public static Boolean verify(String plaintext, String hashedText) {
        try {
            String[] hashedParts = hashedText.split(":");
            if (hashedParts.length != 2) {
                throw new Sm3CryptoException("无效的哈希值格式");
            }
            return hash(plaintext, hashedParts[1]).equals(hashedText);
        } catch (Sm3CryptoException e) {
            throw e;
        } catch (Exception e) {
            throw new Sm3CryptoException("哈希验证异常", e);
        }
    }
}
