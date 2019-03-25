package com.btc.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.FitzpatrickAction;

/**
 * Created by Think on 2016/11/27.
 */
public class StringUtil {
    public static boolean isNull(String str){
        if(str==null){
            return true;
        }
        if(str.trim().equals("")){
            return true;
        }
        return false;
    }
    public static final String EMPTY_STRING = "";

    public StringUtil() {
    }
    public static String replaceIcon(String str){
        if(isEmpty(str)){
            return str;
        }
        return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
    }
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isBlank(String str) {
        int length;
        if(str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isBlank(String... srts) {
        String[] var1 = srts;
        int var2 = srts.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String srt = var1[var3];
            if(isBlank(srt)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotBlank(String str) {
        int length;
        if(str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        if(str == null) {
            return false;
        } else {
            int length = str.length();

            for(int i = 0; i < length; ++i) {
                if(!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
    /**
     * ${}替换
     * @param str
     * @param map
     * @return SHARE_STOCK_GRAIL_CONTENT_LEVEL1
     */
    public static  String replaceJstlMap(String str,Map<String, String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str=str.replaceAll("\\{["+entry.getKey()+"}]*\\}", entry.getValue());
        }
        return str;
    }
    /**
	 * ${}替换

	 * @return SHARE_STOCK_GRAIL_CONTENT_LEVEL1
	 */
	public static String replaceJstl(String smsModal,String content){
			return smsModal.replaceAll("\\$\\{[content}]*\\}",content);
	}
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
	 }
	
}
