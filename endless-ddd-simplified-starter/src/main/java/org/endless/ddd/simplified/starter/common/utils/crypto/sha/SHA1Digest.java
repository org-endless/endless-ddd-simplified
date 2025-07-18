package org.endless.ddd.simplified.starter.common.utils.crypto.sha;

import org.bouncycastle.crypto.Digest;

import java.util.Base64;

/**
 * SHA1Digest
 * <p>
 * create 2025/03/03 00:41
 * <p>
 * update 2025/03/03 00:41
 *
 * @author Deng Haozhi
 * @since 1.0.0
 */
public class SHA1Digest {

    public static String generate(String message) {
        byte[] messageBytes = Base64.getDecoder().decode(message);
        Digest digest = new org.bouncycastle.crypto.digests.SHA1Digest();
        digest.update(messageBytes, 0, messageBytes.length);
        byte[] hashed = new byte[digest.getDigestSize()];
        digest.doFinal(hashed, 0);
        return Base64.getEncoder().encodeToString(hashed);
    }
}
