// package org.endless.ddd.simplified.starter.common.utils.crypto.aes;
//
// import cn.hutool.core.util.HexUtil;
// import org.apache.commons.lang3.StringUtils;
//
// import javax.crypto.Cipher;
// import javax.crypto.spec.IvParameterSpec;
// import javax.crypto.spec.SecretKeySpec;
// import java.nio.charset.StandardCharsets;
//
// public class AesSecurityUtil {
//
//     public static String aesDecrypt(String encryptContent, String password) {
//         if (StringUtils.isEmpty(password) || password.length() != 16) {
//             throw new AesDecryptException("密钥长度必须为16位");
//         }
//
//         try {
//             byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8); // 指定字符编码
//             byte[] ivBytes = password.getBytes(StandardCharsets.UTF_8);
//             byte[] encryptedBytes = HexUtil.decodeHex(encryptContent);
//
//             Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//             SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//             IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//
//             cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
//             byte[] originalBytes = cipher.doFinal(encryptedBytes);
//
//             return new String(originalBytes, StandardCharsets.UTF_8).trim();
//         } catch (javax.crypto.BadPaddingException e) {
//             throw new AesDecryptException("解密时填充错误", e);
//         } catch (javax.crypto.IllegalBlockSizeException e) {
//             throw new AesDecryptException("解密时块大小错误", e);
//         } catch (java.security.InvalidKeyException e) {
//             throw new AesDecryptException("无效的密钥", e);
//         } catch (java.security.InvalidAlgorithmParameterException e) {
//             throw new AesDecryptException("无效的算法参数", e);
//         } catch (java.security.NoSuchAlgorithmException e) {
//             throw new AesDecryptException("不支持的算法", e);
//         } catch (javax.crypto.NoSuchPaddingException e) {
//             throw new AesDecryptException("不支持的填充方式", e);
//         } catch (Exception e) {
//             throw new AesDecryptException("解密时发生未知错误", e);
//         }
//     }
//
//
//     public static String AesEncrypt(String content, String key) {
//         if (StringUtils.isEmpty(key) || key.length() != 16) {
//             throw new AesEncryptException("密钥长度必须为16位");
//         }
//
//         try {
//             byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
//             byte[] dataBytes = content.getBytes(StandardCharsets.UTF_8);
//             Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//             SecretKeySpec keyspec = new SecretKeySpec(keyBytes, "AES");
//             IvParameterSpec ivspec = new IvParameterSpec(keyBytes); // 使用密钥作为IV，保持一致性
//
//             cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
//             byte[] encrypted = cipher.doFinal(dataBytes); // 直接使用数据字节数组，减少不必要的复制
//
//             return HexUtil.encodeHexStr(encrypted);
//
//         } catch (javax.crypto.BadPaddingException e) {
//             throw new AesEncryptException("加密时填充错误", e);
//         } catch (javax.crypto.IllegalBlockSizeException e) {
//             throw new AesEncryptException("加密时块大小错误", e);
//         } catch (java.security.InvalidKeyException e) {
//             throw new AesEncryptException("无效的密钥", e);
//         } catch (java.security.InvalidAlgorithmParameterException e) {
//             throw new AesEncryptException("无效的算法参数", e);
//         } catch (java.security.NoSuchAlgorithmException e) {
//             throw new AesEncryptException("不支持的算法", e);
//         } catch (javax.crypto.NoSuchPaddingException e) {
//             throw new AesEncryptException("不支持的填充方式", e);
//         } catch (Exception e) {
//             throw new AesEncryptException("aes加密发生未知错误", e);
//         }
//     }
//
// }
