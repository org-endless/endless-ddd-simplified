package org.endless.ddd.simplified.starter.common.utils.crypto.sm.sm2;

import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.*;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

/**
 * SM2Crypto
 * <p>
 * create 2024/11/16 01:25
 * <p>
 * update 2024/11/16 01:25
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
public class SM2Crypto {

    /**
     * 椭圆曲线参数
     */
    private static final String CURVE_NAME = "SM2P256V1";

    /**
     * SM2 EC椭圆曲线参数
     */
    private static final X9ECParameters EC_PARAMETERS = GMNamedCurves.getByName(CURVE_NAME);

    /**
     * EC 域参数
     */
    private static final ECDomainParameters EC_DOMAIN_PARAMETERS = new ECDomainParameters(EC_PARAMETERS.getCurve(), EC_PARAMETERS.getG(), EC_PARAMETERS.getN(), EC_PARAMETERS.getH());

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * SM2 公钥 (Base64编码)
     */
    private final String publicKey;

    /**
     * SM2 私钥 (Base64编码)
     */
    private final String privateKey;

    /**
     * 生成密钥对
     *
     * @return {@link KeyPair }
     */
    public static SM2Crypto keyPair() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(System.nanoTime()); // 确保种子每次不同
            // 生成BouncyCastle格式EC密钥对
            AsymmetricCipherKeyPair bouncyCastleKeyPair = asymmetricCipherKeyPair(secureRandom);
            ECPrivateKeyParameters privateKeyParameters = (ECPrivateKeyParameters) bouncyCastleKeyPair.getPrivate();
            ECPublicKeyParameters publicKeyParameters = (ECPublicKeyParameters) bouncyCastleKeyPair.getPublic();
            ProviderConfiguration providerConfig = BouncyCastleProvider.CONFIGURATION;
            BCECPrivateKey privateKey = new BCECPrivateKey("EC", privateKeyParameters, providerConfig);
            BCECPublicKey publicKey = new BCECPublicKey("EC", publicKeyParameters, providerConfig);
            ECPoint publicPoint = publicKey.getQ();
            byte[] compressedPublicKeyBytes = publicPoint.getEncoded(true);
            return SM2Crypto.builder()
                    .privateKey(Base64.getEncoder().encodeToString(privateKey.getD().toByteArray()))
                    .publicKey(Base64.getEncoder().encodeToString(compressedPublicKeyBytes))
                    .build();
        } catch (Exception e) {
            throw new Sm2KeyPairException(e.getMessage(), e);
        }
    }

    /**
     * 加密，默认使用 C1C3C2 模式
     *
     * @param plaintext 明文 (Base64编码)
     * @param publicKey 公钥 (Base64编码)
     * @return {@link String } 加密后的密文 (Base64编码)
     */
    public static String encrypt(String plaintext, String publicKey) {
        return encrypt(plaintext, publicKey, SM2Engine.Mode.C1C3C2);
    }

    /**
     * 加密
     *
     * @param plaintext 明文 (Base64编码)
     * @param publicKey 公钥 (Base64编码)
     * @return {@link String } 加密后的密文 (Base64编码)
     */
    public static String encrypt(String plaintext, String publicKey, SM2Engine.Mode mode) {
        try {
            // 生成 EC 公钥参数
            ECPoint ecPoint = EC_PARAMETERS.getCurve().decodePoint(Base64.getDecoder().decode(publicKey));
            ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(ecPoint, EC_DOMAIN_PARAMETERS);
            // 初始化 SM2 加解密器
            SM2Engine engine = new SM2Engine(mode);
            engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));
            // 加密
            byte[] plaintextBytes = Base64.getDecoder().decode(plaintext);
            byte[] encryptedBytes = engine.processBlock(plaintextBytes, 0, plaintextBytes.length);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new Sm2EncryptException(e.getMessage(), e);
        }
    }

    /**
     * 解密，默认使用 C1C3C2 模式
     *
     * @param ciphertext 密文 (Base64编码)
     * @param privateKey 私钥 (Base64编码)
     * @return {@link String } 解密后的明文 (Base64编码)
     */
    public static String decrypt(String ciphertext, String privateKey) {
        return decrypt(ciphertext, privateKey, SM2Engine.Mode.C1C3C2);
    }

    /**
     * 解密
     *
     * @param ciphertext 密文 (Base64编码)
     * @param privateKey 私钥 (Base64编码)
     * @return {@link String } 解密后的明文 (Base64编码)
     */
    public static String decrypt(String ciphertext, String privateKey, SM2Engine.Mode mode) {
        try {
            // 生成 EC 私钥参数
            BigInteger privateKeyBigInt = new BigInteger(1, Base64.getDecoder().decode(privateKey));
            ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyBigInt, EC_DOMAIN_PARAMETERS);
            // 初始化 SM2 加解密器
            SM2Engine engine = new SM2Engine(mode);
            engine.init(false, privateKeyParameters);
            // 解密
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            byte[] decryptedBytes = engine.processBlock(ciphertextBytes, 0, ciphertextBytes.length);
            return Base64.getEncoder().encodeToString(decryptedBytes);
        } catch (IllegalArgumentException | InvalidCipherTextException e) {
            throw new Sm2DecryptException("密文格式有误" + e.getMessage(), e);
        } catch (Exception e) {
            throw new Sm2DecryptException(e.getMessage(), e);
        }
    }

    /**
     * 签名
     *
     * @param message    待签名消息 (Base64编码)
     * @param privateKey 私钥 (Base64编码)
     * @return {@link String } 签名
     */
    public static String sign(String message, String privateKey) {
        try {
            BigInteger privateKeyBigInt = new BigInteger(1, Base64.getDecoder().decode(privateKey));
            ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyBigInt, EC_DOMAIN_PARAMETERS);
            // 初始化 SM2 签名器
            SM2Signer signer = new SM2Signer();
            signer.init(true, privateKeyParameters);
            // 生成签名，并编码为 Base64 格式
            byte[] messageBytes = Base64.getDecoder().decode(message);
            signer.update(messageBytes, 0, messageBytes.length);
            return Base64.getEncoder().encodeToString(signer.generateSignature());
        } catch (Exception e) {
            throw new Sm2SignException(e.getMessage(), e);
        }
    }

    /**
     * 验签
     *
     * @param message   待验签消息 (Base64编码)
     * @param signature 签名 (Base64编码)
     * @param publicKey 公钥 (Base64编码)
     * @return boolean
     */
    public static Boolean verify(String message, String signature, String publicKey) {
        try {
            // 生成 EC 公钥参数
            ECPoint ecPoint = EC_PARAMETERS.getCurve().decodePoint(Base64.getDecoder().decode(publicKey));
            ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(ecPoint, EC_DOMAIN_PARAMETERS);
            // 初始化 SM2 签名器
            SM2Signer signer = new SM2Signer();
            signer.init(false, publicKeyParameters);
            // 验证签名
            byte[] messageBytes = Base64.getDecoder().decode(message);
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            signer.update(messageBytes, 0, messageBytes.length);
            return signer.verifySignature(signatureBytes);
        } catch (IllegalArgumentException e) {
            throw new Sm2VerifyException("签名的长度或格式有误" + e.getMessage(), e);
        } catch (Exception e) {
            throw new Sm2VerifyException(e.getMessage(), e);
        }
    }

    /**
     * 生成BouncyCastle格式EC密钥对
     *
     * @return {@link AsymmetricCipherKeyPair }
     */
    private static AsymmetricCipherKeyPair asymmetricCipherKeyPair(SecureRandom secureRandom) {
        // 这里根据 SM2 的实现方式来生成密钥对
        // 例如，使用 BouncyCastle 提供的 ECDSA 密钥生成器
        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(EC_DOMAIN_PARAMETERS, secureRandom);
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        keyPairGenerator.init(keyGenParams);
        return keyPairGenerator.generateKeyPair();
    }
}
