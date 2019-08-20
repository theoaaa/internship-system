package org.scau.internshipsystem.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具，提供MD5,DES加密方式
 * */
@Slf4j
public class EncryptUtil {
    private static String desKey = "desDefaultKey";
    private static Cipher encryptCipher = null;
    private static Cipher decryptCipher = null;
    public final static String MD5_ENCRYPT = "md5";
    public final static String DES_ENCRYPT = "des";

    //DES初始化
    static {
        try {
            //加密对象
            Key key = getDesKey(desKey.getBytes());
            encryptCipher = Cipher.getInstance(DES_ENCRYPT);
            encryptCipher.init(Cipher.ENCRYPT_MODE,key);
            //解密对象
            decryptCipher = Cipher.getInstance(DES_ENCRYPT);
            decryptCipher.init(Cipher.DECRYPT_MODE,key);
        } catch (NoSuchAlgorithmException e) {
            log.error("DES加密失败，未找到DES算法...");
        } catch (NoSuchPaddingException e) {
            log.error("DES加密失败...");
        } catch (InvalidKeyException e) {
            log.error("DES加密失败，密钥无效...");
        }
    }

    /**
     * 字节数组转16仅进制串
     * **/
    public static String byteArr2HexStr(byte[] bytes){
        StringBuilder sb = new StringBuilder(bytes.length);
        for(byte b : bytes){
            String a = Integer.toHexString((int)b & 0xff);
            if(a.length() < 2){
                sb.append("0");
            }
            sb.append(a);
        }
        return sb.toString();
    }

    /***
     * 16进制串转字节数组
     * */
    public static byte[] hexStr2ByteArr(String str){
        byte[] bytes = new byte[str.length()/2];
        for(int i = 0; i < str.length(); i += 2){
            String tmp = str.substring(i,i+2);
            bytes[i/2] = (byte) Integer.parseInt(tmp,16);
        }
        return bytes;
    }

    /**
     * 根据不同加密方式对传入字符串加密
     * **/
    public static String encrypt(String mode,String str){
        //MD5加密
        if(StringUtils.equals(mode,MD5_ENCRYPT)){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(MD5_ENCRYPT);
                return byteArr2HexStr(messageDigest.digest(str.getBytes()));
            } catch (NoSuchAlgorithmException e) {
                log.error("MD5加密失败,未找到md5算法...{}",str);
            }
        }
        //DES加密
        else if(StringUtils.equals(mode,DES_ENCRYPT)){
            try {
                return byteArr2HexStr(encryptCipher.doFinal(str.getBytes()));
            } catch (IllegalBlockSizeException e) {
                log.error("DES加密失败...");
            } catch (BadPaddingException e) {
                log.error("DES加密失败...");
            }
        }
        return null;
    }

    /**
     * 对传入字符串进行DES解密
     * **/
    public static String decrpyt(String str){
        try {
            return new String(decryptCipher.doFinal(hexStr2ByteArr(str)));
        } catch (IllegalBlockSizeException e) {
            log.error("DES加密失败...");
        } catch (BadPaddingException e) {
            log.error("DES加密失败...");
        }
        return null;
    }

    /**
     * 获取DES密钥
     * **/
    public static Key getDesKey(byte[] key){
        byte[] bytes = new byte[8];
        for(int i = 0; i < key.length && i < bytes.length; i++){
            bytes[i] = key[i];
        }
        return new javax.crypto.spec.SecretKeySpec(bytes, "DES");
    }
}
