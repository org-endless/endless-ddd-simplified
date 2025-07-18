package org.endless.ddd.simplified.starter.common.utils.crypto.cert;

import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.PFXCertException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * PFXCert
 * <p>
 * create 2025/03/02 20:07
 * <p>
 * update 2025/03/02 20:07
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
@Getter
@Builder
public class PFXCert {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * PFX 证书公钥 (Base64编码)
     */
    private final String publicKey;

    /**
     * PFX 证书私钥 (Base64编码)
     */
    private final String privateKey;

    /**
     * @param path     证书路径
     * @param password Base64编码后的密码
     * @return {@link String } Base64编码后的公钥
     */
    public static PFXCert keyPair(String path, String password, Charset charset) {
        try (InputStream inputStream = new FileInputStream(path)) {
            char[] passwordChars = new String(Base64.getDecoder().decode(password), charset).toCharArray();
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, passwordChars);
            String alias = keyStore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, passwordChars);
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
            PublicKey publicKey = cert.getPublicKey();
            return PFXCert.builder()
                    .privateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()))
                    .publicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
                    .build();
        } catch (Exception e) {
            throw new PFXCertException(e.getMessage(), e);
        }
    }
}
