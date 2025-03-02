// package org.endless.ddd.simplified.starter.common.utils.crypto.aes;
//
// import cn.hutool.core.util.HexUtil;
// import org.endless.ddd.simplified.starter.common.exception.utils.crypto.Sha1EncryptException;
//
// import java.nio.charset.Charset;
// import java.security.MessageDigest;
//
// public class SecurityUtil {
//
//     public static String sha1X16(String data, Charset charset) {
//         byte[] bytes = sha1(data.getBytes(charset));
//         return HexUtil.encodeHexStr(bytes);
//     }
//
//     public static byte[] sha1(byte[] data) {
//         try {
//             MessageDigest md = MessageDigest.getInstance("SHA-1");
//             md.update(data);
//             return md.digest();
//         } catch (Exception e) {
//             // 使用 RuntimeException 包装原始异常，这样可以避免返回 null
//             throw new Sha1EncryptException("Failed to calculate SHA-1 hash", e);
//         }
//     }
//
//
// }
