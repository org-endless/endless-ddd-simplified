package org.endless.ddd.simplified.starter.common.utils.crypto.cert;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.endless.ddd.simplified.starter.common.exception.utils.crypto.PEMCertException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * PEMCert
 * <p>
 * create 2025/03/02 18:46
 * <p>
 * update 2025/03/02 18:46
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class PEMCert {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * @param path 证书路径
     * @return {@link String } Base64编码后的公钥
     */
    public static String publicKey(String path) {
        try (InputStream inputStream = new FileInputStream(path)) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            PublicKey publicKey = certificate.getPublicKey();
            return Base64.getEncoder().encodeToString(publicKey.getEncoded());
        } catch (Exception e) {
            throw new PEMCertException(e.getMessage(), e);
        }
    }
}
