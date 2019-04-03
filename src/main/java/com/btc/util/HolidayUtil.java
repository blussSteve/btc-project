package com.btc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.btc.global.enums.SysConfigEnum;
import com.btc.service.RedisService;

@Component
public class HolidayUtil {

	@Autowired
	private RedisService redisService;
	
	private static HolidayUtil instance;
	
	private final static String HOLIDAY="holiday";
	
	 @PostConstruct
    public void init() {
		 instance = this;
		 instance.redisService = this.redisService;
    }
	 
 /**
  * 是否节假日
  * @return
  */
	public static boolean isHoliday(){
		return isHoliday(new Date());
	} 
	/**
	 * 是否节假日 
	 * @param date
	 * @return
	 */
	public static boolean isHoliday(Date date){
		
		//判断是否周六日
		Calendar current = Calendar.getInstance();
		current.setTime(date);
		if(current.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
			return true;
		}
		if(current.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			return true;
		}
		String dateStr=new SimpleDateFormat("yyyyMMdd").format(date);
		
		if(instance.redisService.hexists(HOLIDAY, dateStr)){
			return true;
		}
//		
		return false;
	} 
	
	/**
	 * 假期设置
	 * @param list
	 */
	public static void setHoliday(List<String> list){
		
		for(String str:list){
			instance.redisService.hset(HOLIDAY, str,str);
		}
		
	} 
	
	/**
	 * 是否委托
	 */
	public static boolean isCanEntrust(){
		
		if(isHoliday()){
			return false;
		}
		Calendar current = Calendar.getInstance();
		int hour=current.get(Calendar.HOUR_OF_DAY);
		if(hour<9||hour>14){
			return false;
		} 
		return true;
	}

	/**
	 * 是否可以交易
	 * @return
	 */
	public static boolean isCanTran(){
		
//		if(isHoliday()){
//			return false;
//		}
		String beginDate=instance.redisService.hget(Constants.SYS_DIC_CACHE, SysConfigEnum.COUNT_INCOME_BEGIN_DATE.getKey());
		String endDate=instance.redisService.hget(Constants.SYS_DIC_CACHE, SysConfigEnum.COUNT_INCOME_END_DATE.getKey());
		Calendar current = Calendar.getInstance();
		int hour=current.get(Calendar.HOUR_OF_DAY);
		if(hour>Integer.parseInt(beginDate)&&hour<Integer.parseInt(endDate)){
			return false;
		} 
		return true;
	}
	
	/**
	 * 是否展示今日收益
	 * @return
	 */
	public static boolean isShowDayIncome(){
		
		Calendar current = Calendar.getInstance();
		int hour=current.get(Calendar.HOUR_OF_DAY);
		if(hour<1&&hour>=9){
			return true;
		} 
		return false;
	}
}
