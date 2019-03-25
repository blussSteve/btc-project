package com.btc.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;


public class DateUtil {
	/**
     *获取每月最后一天时间
     * @param sDate1
     * @return
     */
    public static Date getLastDayOfMonth(Date sDate1){
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(sDate1);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return  lastDate;
    }

    /*
    获取下一个月第一天凌晨1点
     */
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);   //设置为每月凌晨1点
        calendar.set(Calendar.DAY_OF_MONTH, 1);   //设置为每月1号
        calendar.add(Calendar.MONTH, 1);   // 月份加一，得到下个月的一号
//        calendar.add(Calendar.DATE, -1);     下一个月减一为本月最后一天
        return calendar.getTime();
    }

    /**
     * 获取第二天凌晨0点毫秒数
     * @return
     */
    public static Date nextDayFirstDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    //*********

    /**
     * 获取当前时间到下个月凌晨1点相差秒数
     * @return
     */
    public static Long getSecsToEndOfCurrentMonth(){

        Long secsOfNextMonth  = nextMonthFirstDate().getTime();
        //将当前时间转为毫秒数
        Long secsOfCurrentTime = new Date().getTime();
        //将时间转为秒数
        Long distance = (secsOfNextMonth - secsOfCurrentTime)/1000;
        if (distance > 0 && distance != null){
            return distance;
        }

        return new Long(0);

    }



    /**
     * 获取当前时间到明天凌晨0点相差秒数
     * @return
     */
    public static Long getSecsToEndOfCurrentDay() {

        Long secsOfNextDay  = nextDayFirstDate().getTime();
        //将当前时间转为毫秒数
        Long secsOfCurrentTime = new Date().getTime();
        //将时间转为秒数
        Long distance = (secsOfNextDay - secsOfCurrentTime)/1000;
        if (distance > 0 && distance != null){
            return distance;
        }

        return new Long(0);

    }
    
   /** 
    * 获取过去的天数
    * @param date
    * @return
    */
   public static long pastDays(Date date) {
       long t = date.getTime()-new Date().getTime();
       return t/(24*60*60*1000);
   }

    /**
     * 判断两个日期是否是同一天
     * @param d1
     * @param d2
     * @return
     */
    public static boolean  getDateDiffForDay(Date d1,Date d2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sd = sdf.format(d1);
            String ed = sdf.format(d2);
            if(sd.equals(ed)){
                return true;
            }
        }catch (Exception ce){
            ce.printStackTrace();
        }
        return false;
    }

    /**
     * Timestamp转为Date
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(Timestamp timestamp){
        Date date = timestamp;
        return date;
    }

    /**
     * String转为TimeStamp
     * @param str
     * @return
     */
    public static Timestamp stringToTimeStamp(String str){
        try {
            Timestamp ts = Timestamp.valueOf(str);
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String nowDate(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
    public static String nowStr(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }
    public static Date stringToDate(String str,String format){
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date beforeDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE,  day-1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        System.out.println(dayBefore);
        return c.getTime();
    }
    public static Date add(int n){
        Calendar c = Calendar.getInstance();
        int day = c.get((Calendar.DATE));
        c.set(Calendar.DATE,day+n);
        return c.getTime();
    }
    /**
     * 判断是否在有限期内
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean checkIsInValidity(Date startDate, Date endDate) {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        //比较Calendar对象的纪元、年份和所在年份的第几天
        boolean isStart = nowCalendar.get(0) >= startCalendar.get(0) && nowCalendar.get(1) >= startCalendar.get(1) && nowCalendar.get(6) >= startCalendar.get(6);
        boolean isEnd = nowCalendar.get(0) <= endCalendar.get(0) && nowCalendar.get(1) <= endCalendar.get(1) && nowCalendar.get(6) <= endCalendar.get(6);
        if (isStart & isEnd) {
            return true;
        }
        return false;
    }

    /**
     * 格式化商品有限期
     * @param startDate	开始时间
     * @param endDate	结束时间
     * @return
     */
    public static String formatValidity(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        StringBuffer sb = new StringBuffer(sdf.format(startDate));
        String validityStr = sb.append("至").append(sdf.format(endDate)).toString();
        return validityStr;
    }

    /**
     * 相隔天数
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static long apartDays(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(sdf.format(startDate));
            end = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
    }
    public static int daysBetween(String smdate,String bdate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try{
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        }catch(Exception e){
            e.printStackTrace();
        }
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
    public static String parsetYMD(Date date){
        if(date==null){
            return "--";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static String parsetHMS(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
    public static String nowHMS(){
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }
    public static String  getH(Date date){
        DateFormat sdf = new SimpleDateFormat("HH");
        String str = sdf.format(date);
        return str;
    }
    public static String getHM(Date date){
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        String str = sdf.format(date);
        return str;
    }
    public static String lastWeek(){
        Date mdate = new Date();
        int b = mdate.getDay();
        if(b==0){
            b=7;
        }
        int f=1-b;
        int l=7-b;
        Date fd = DateUtil.add(f);
        Date ld = DateUtil.add(l);
        String fds = new SimpleDateFormat("yyyy.MM.dd").format(fd);
        String lds = new SimpleDateFormat("yyyy.MM.dd").format(ld);
        return fds+"~"+lds;
    }
    
    public static Date addMonth(int i){
    	Calendar ca=Calendar.getInstance();
    	ca.add(Calendar.MONTH, i);
    	return ca.getTime();
    	
    }
    
    public static String getTalkTime(long org,long dest){
    	/**
    	 * 1.分钟内
    	 */
    	
    	
    	
//    	一天内的消息显示为：“昨天 时:分”

//    	二天至七天内显示为：“星期X 时:分”

//    	当大于7天时显示为：“YYYY年X月X日时:分”

//    	时、分不足二位时，前面用0补齐，月、日不足二位时不补位。
//    	如：2016年7月13日 09:22，注意计算天数是要算天，不能拿毫秒进行比较。
//    	否则昨天上午的消息，在今天下午看时将会变成“星期X 09:10”，正确的应该是“昨天 09:10”
    	
    	
    	return null;
    }
    public static void main(String[] args) {
//        System.out.println(DateUtil.parsetYMD(DateUtil.add(-1)));
//        Date date = new Date();
//        //9：30-11：30，13：00-15：00。
//        String hm=DateUtil.getHM(date);
//        String strs[] = hm.split(":");
//        System.out.println(Integer.parseInt(strs[0]));
//        System.out.println(Integer.parseInt(strs[1]));
//        System.out.println(hm);
//        System.out.println(DateUtil.parsetYMD(date));
//        System.out.println(DateUtil.parsetHMS(date));
//        String s1="2017-03-22";
//        String s2="2017-03-25";
//        System.out.println(DateUtil.daysBetween(s1,s2));
//        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//        for(String str:list)
//            System.out.println(str);

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(addMonth(1)));
        
        Date d=DateUtils.addDays(new Date(), -7);
        
       String dd= com.xiaoleilu.hutool.date.DateUtil.format(d, "yyyyMMdd");
       System.out.println(dd);
    }
}

