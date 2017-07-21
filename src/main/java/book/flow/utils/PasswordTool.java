package book.flow.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具类.
 * User: huang
 * Date: 17-7-21
 */
public class PasswordTool {
    private PasswordTool() {

    }

    /**
     * 使用MD5加密.
     * @param str 要加密的字符串
     * @return 加密后的结果
     */
    public static String encryptionMD5(String str) {
        String result = str;
        try {
            // 初始化MessageDigest,并指定为MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 加密
            byte[] a = md.digest(str.getBytes());
            result = new String(a);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return result;
        }
    }
}