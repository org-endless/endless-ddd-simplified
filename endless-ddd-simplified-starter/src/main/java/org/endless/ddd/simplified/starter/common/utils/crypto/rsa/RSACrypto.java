package org.endless.ddd.simplified.starter.common.utils.crypto.rsa;

import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.RSADigestSigner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.*;

import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * RSACrypto
 * <p>
 * create 2025/03/01 20:07
 * <p>
 * update 2025/03/01 20:07
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
public class RSACrypto {

    private static final String RSA_ALGORITHM = "RSA";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * RSA 公钥 (Base64编码)
     */
    private final String publicKey;

    /**
     * RSA 私钥 (Base64编码)
     */
    private final String privateKey;

    /**
     * 生成密钥对
     *
     * @return {@link RSACrypto }
     */
    public static RSACrypto keyPair(Integer keySize) {
        try {
            if (keySize % 256 != 0) {
                throw new RSAEncryptException("秘钥长度必须为256的倍数");
            }
            // 生成密钥对
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(System.nanoTime());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGenerator.initialize(keySize, secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return RSACrypto.builder()
                    .privateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()))
                    .publicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
                    .build();
        } catch (Exception e) {
            throw new RSAKeyPairException(e.getMessage(), e);
        }
    }

    /**
     * 加密
     *
     * @param plaintext 明文 (Base64编码)
     * @param publicKey 公钥 (Base64编码)
     * @return {@link String } 加密后的密文 (Base64编码)
     */
    public static String encrypt(String plaintext, String publicKey) {
        try {
            // 生成 RSA 公钥参数
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            RSAKeyParameters publicKeyParameters = new RSAKeyParameters(false, rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            // 初始化 RSA 加解密引擎
            RSAEngine engine = new RSAEngine();
            engine.init(true, publicKeyParameters);
            // 加密
            byte[] plaintextBytes = Base64.getDecoder().decode(plaintext);
            int inputBlockSize = engine.getOutputBlockSize() - 11;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                for (int i = 0; i < plaintextBytes.length; i += inputBlockSize) {
                    int chunkSize = Math.min(inputBlockSize, plaintextBytes.length - i);
                    byte[] chunk = Arrays.copyOfRange(plaintextBytes, i, i + chunkSize);
                    byte[] encryptedBlock = engine.processBlock(chunk, 0, chunk.length);
                    outputStream.write(encryptedBlock);
                }
                return Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }
        } catch (Exception e) {
            throw new RSAEncryptException(e.getMessage(), e);
        }
    }

    /**
     * 解密
     *
     * @param ciphertext 密文 (Base64编码)
     * @param privateKey 私钥 (Base64编码)
     * @return {@link String } 解密后的明文 (Base64编码)
     */
    public static String decrypt(String ciphertext, String privateKey) {
        try {
            // 生成 RSA 私钥参数
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            RSAKeyParameters privateKeyParameters = new RSAKeyParameters(true, rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            // 初始化 RSA 加解密引擎
            RSAEngine engine = new RSAEngine();
            engine.init(false, privateKeyParameters);
            // 解密
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            int inputBlockSize = engine.getInputBlockSize();
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                for (int i = 0; i < ciphertextBytes.length; i += inputBlockSize) {
                    byte[] chunk = Arrays.copyOfRange(ciphertextBytes, i, i + inputBlockSize);
                    byte[] decryptedBlock = engine.processBlock(chunk, 0, chunk.length);
                    outputStream.write(decryptedBlock);
                }
                return Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }
        } catch (Exception e) {
            throw new RSADecryptException(e.getMessage(), e);
        }
    }

    /**
     * 签名，默认使用 SHA-1 摘要算法
     *
     * @param message    待签名消息 (Base64编码)
     * @param privateKey 私钥 (Base64编码)
     * @return {@link String } 签名
     */
    public static String sign(String message, String privateKey) {
        return sign(message, privateKey, "SHA-1");
    }

    /**
     * 签名
     *
     * @param message         待签名消息 (Base64编码)
     * @param privateKey      私钥 (Base64编码)
     * @param digestAlgorithm 摘要算法
     * @return {@link String } 签名
     */
    public static String sign(String message, String privateKey, String digestAlgorithm) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            RSAKeyParameters privateKeyParameters = new RSAKeyParameters(true, rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            // 初始化签名器
            RSADigestSigner signer = new RSADigestSigner(digest(true, digestAlgorithm));
            signer.init(true, privateKeyParameters);
            // 生成签名，并编码为 Base64 格式
            byte[] messageBytes = Base64.getDecoder().decode(message);
            signer.update(messageBytes, 0, messageBytes.length);
            return Base64.getEncoder().encodeToString(signer.generateSignature());
        } catch (RSASignException e) {
            throw e;
        } catch (Exception e) {
            throw new RSASignException(e.getMessage(), e);
        }
    }

    /**
     * 验签，默认使用 SHA-1 摘要算法
     *
     * @param message   待验签消息 (Base64编码)
     * @param signature 签名 (Base64编码)
     * @param publicKey 公钥 (Base64编码)
     * @return Boolean
     */
    public static Boolean verify(String message, String signature, String publicKey) {
        return verify(message, signature, publicKey, "SHA-1");
    }

    /**
     * 验签
     *
     * @param message         待验签消息 (Base64编码)
     * @param signature       签名 (Base64编码)
     * @param publicKey       公钥 (Base64编码)
     * @param digestAlgorithm 摘要算法
     * @return Boolean
     */
    public static Boolean verify(String message, String signature, String publicKey, String digestAlgorithm) {
        try {
            // 生成 EC 公钥参数
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            RSAKeyParameters publicKeyParameters = new RSAKeyParameters(false, rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            // 初始化签名器
            RSADigestSigner signer = new RSADigestSigner(digest(false, digestAlgorithm));
            signer.init(false, publicKeyParameters);
            // 验证签名
            byte[] messageBytes = Base64.getDecoder().decode(message);
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            signer.update(messageBytes, 0, messageBytes.length);
            return signer.verifySignature(signatureBytes);
        } catch (RSAVerifyException e) {
            throw e;
        } catch (Exception e) {
            throw new RSAVerifyException(e.getMessage(), e);
        }
    }

    private static Digest digest(Boolean forSigning, String algorithm) {
        return switch (algorithm) {
            case "SHA-1" -> new SHA1Digest();
            case "SHA-256" -> new SHA256Digest();
            case "SHA-512" -> new SHA512Digest();
            default -> {
                if (forSigning) {
                    throw new RSASignException("不支持的摘要算法: " + algorithm);
                } else {
                    throw new RSAVerifyException("不支持的摘要算法: " + algorithm);
                }
            }
        };
    }
}
