package com.btc.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>Description: uuid工具类</p>
 * @version V1.0
 */
public class UUIDUtil {

    /**
     *
     * <p>Description:uuid生成</p>
     * @return
     * @author lixingxing
     * @date 2017年6月7日 上午10:42:27
     */
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        String uuidStr =  uuid.toString();
        return uuidStr;
    }

    /**
     *
     * <p>Description:uuid生成（去‘-’）</p>
     * @return
     * @author lixingxing
     * @date 2017年6月7日 上午10:42:27
     */
    public static String getUuidTrim(){
        UUID uuid = UUID.randomUUID();
        String uuidStr =  uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }

    /**
     * <p>Description:uuid生成 yyyyMMdd${key}${uuid}</p>
     * @param key
     * @author hao.feng@cedaotech.com
     * @return java.lang.String
     * @throws
     * @since v1.8.0
     * @date 2017/6/8 13:56
     */
    public static String getDateKeyUUID(String key) {
        if(StringUtil.isBlank(key)){
            key = "";
        }
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        int startLeng = key.length()+ dateStr.length();
        String uuidSub = uuid.substring(startLeng);
        StringBuffer res = new StringBuffer();
        res.append(dateStr).append(key).append(uuidSub);
        return res.toString();
    }
}
