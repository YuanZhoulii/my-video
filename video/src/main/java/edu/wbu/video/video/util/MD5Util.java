package edu.wbu.video.video.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author yuanzhouli
 * @date 2020/12/8 - 22:04
 */
public class MD5Util {
    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String code(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            //使用MD5算法加密后将生成一个字节数组
            byte[] byteDigest = md.digest();
            //将字节数组转换为16进制字符串
            return new BigInteger(1,byteDigest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(code(code("123")));
    }
}
