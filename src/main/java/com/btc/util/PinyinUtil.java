package com.btc.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinyinUtil {
	private static PinyinUtil instance;  
	  
    public static PinyinUtil getInstance() {  
        if (instance == null) {  
            instance = new PinyinUtil();  
        }  
        return instance;  
    }  
  
    private HanyuPinyinOutputFormat outputFormat = null;  
  
    private HanyuPinyinOutputFormat getOutputFormat() {  
        if (outputFormat == null) {  
            outputFormat = new HanyuPinyinOutputFormat();  
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
            outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        }  
        return outputFormat;  
    }  
  
    /** 
     * 閼惧嘲褰囩�妤冾儊娑撹弓鑵戦幍锟芥箒鐎涙顑佹＃鏍х摟濮ｏ拷
     *  
     * @param str 
     * @return 
     * @throws Exception 
     */  
    public String getStringInitial(String str) {  
        StringBuffer sbf = new StringBuffer();  
        if (str != null) {  
            for (int i = 0; i < str.length(); i++) { 
            	if(String.valueOf(str.charAt(i)).matches("^[a-zA-Z]*")){
            		 sbf.append(str.charAt(i));  
            	}else{
            		 sbf.append(getCharInitial(str.charAt(i)));  
            	}
               
            }  
        }   
        return sbf.toString();  
    }  
  
    /** 
     * 閼惧嘲褰囨稉顓熸瀮鐎涙顑佹＃鏍х摟濮ｏ拷
     *  
     * @param c 
     * @return 
     * @throws Exception 
     */  
    public String getCharInitial(char c) {  
        try {  
            String[] strs = PinyinHelper.toHanyuPinyinStringArray(c,  
                    getOutputFormat());  
            String initial = "";  
            if (strs != null && strs.length > 0) {  
                String str = strs[0];  
                if (str != null && str.length() > 0) {  
                    initial += str.charAt(0);  
                }  
            }  
            return initial;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return c + "";  
        }  
    }  
  
    public String getStringPinyin(String chines) {  
  
        char[] nameChar = chines.toCharArray();  
        String pinyinStr = "";  
        for (int i = 0; i < nameChar.length; i++) {  
            try {  
                char cha=nameChar[i];  
                if(String.valueOf(cha).matches("^[a-zA-Z]*")){
                    pinyinStr +=cha ;  
	           	}else{
	           	 if (nameChar[i] > 128) {  
	                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(cha, getOutputFormat())[0];  
	                }  
	           	}
               
            } catch (Exception ex) {  
                ex.printStackTrace();  
                pinyinStr += nameChar[i];  
            }  
        }  
        return pinyinStr;  
    }  
  
    /** 
     * 閼惧嘲褰囩�妤冾儊閸忋劍瀚�
     *  
     * @param c 
     * @return 
     */  
    public String getCharPinyin(char c) {  
  
        try {  
            String[] strs = PinyinHelper.toHanyuPinyinStringArray(c,  
                    getOutputFormat());  
            String str = "";  
            if (strs != null && strs.length > 0) {  
                str = strs[0];  
            }  
            return str;  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return c + "";  
        }  
    }  
    public static void main(String[] args) {  
        String stringInitial = PinyinUtil.getInstance().getStringInitial("sf");  
        System.out.println(stringInitial);  
        String stringPinyin = PinyinUtil.getInstance().getStringPinyin("sf");  
        System.out.println(stringPinyin); 
        
        System.out.println("sdsd".toUpperCase());
  
    }  
}
