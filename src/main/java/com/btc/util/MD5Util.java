package com.btc.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SignatureException;

/**
 * Created by Think on 2016/11/27.
 */
public class MD5Util {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MD5Util.class);

    private final static String KEY="e10adc3949ba59abbe56e057f20f883e";
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if(mysign.equals(sign)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    /***
     * MD5加密 生成32位md5码
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            LOGGER.error("MD5Util error=" + e.getMessage(), e);
            return "";
        }
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    public static void main(String args[]) throws  Exception {
        /**
         * sign=8F2037D8BE14EE3E7CDB04601F42DAC2


         secretKey=2b0ca8b8-3a18-42e5-a728-7d78c1937e87
         token=ef3f20cb-fe68-4e9b-978b-d4803b454112
         timestamp=1469771831750

         */
//        Long t =1469771831750l;
//        String token="ef3f20cb-fe68-4e9b-978b-d4803b454112";
//        String sec="2b0ca8b8-3a18-42e5-a728-7d78c1937e87";
//        System.out.println(MD5Util.md5Encode(t+token+sec));
//        String path ="/default/default2.jpg";
//        int i=path.indexOf("/");
//        if(i==0){
//            path = path.substring(1);
//        }
//        System.out.println(path);
        System.out.println(MD5Util.md5Encode("1580037963612345678901"));
        
        System.out.println((int)(100000*Math.random())+"");
    }
}
