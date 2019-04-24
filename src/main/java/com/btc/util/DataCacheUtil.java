package com.btc.util;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.btc.global.enums.SysConfigEnum;
import com.btc.global.enums.UserEnum;
import com.btc.lbank.bean.LbankToken;
import com.btc.model.UserInfo;
import com.btc.service.RedisService;

@Component 
public class DataCacheUtil {
	@Autowired
	private RedisService redisService;
	
	private static DataCacheUtil instance;
	 
	@PostConstruct 
	public void init() {
		instance = this;
		instance.redisService = this.redisService;
	}
	/**
	 * 获取用户缓存
	 * @param userId
	 * @return
	 */
	public static UserInfo getUserInfo(String token){
		String str=instance.redisService.get(Constants.USER_TOKEN_CACHE+token);
		 
		if(StringUtils.isNotEmpty(str)){
			UserInfo vo=JSONObject.parseObject(str,UserInfo.class);
			return vo;
		}
		return null;
	}
	
	/**
	 * 获取token
	 * @param openId
	 * @return
	 */
	public static LbankToken getLbankToken(String openId){
		String str=instance.redisService.get(Constants.LBANK_TOKEN_CACHE+openId);
		 
		if(StringUtils.isNotEmpty(str)){
			LbankToken vo=JSONObject.parseObject(str,LbankToken.class);
			return vo;
		}
		return null;
	}
	
	
	/**
	 * 是否能进行交易
	 * @return
	 */
	public static boolean isCanTrade(){
		
		String value=instance.redisService.hget(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TRADE.getKey());
		
	    if(value.equals("1")){
	    	return true;
	    }
	    return false;
		
	}
	public static boolean checkTaskUserIsTrue(String userName,String password){
		String name=instance.redisService.hget(Constants.SYS_USER_CACHE+UserEnum.TASK_USER, "userName");
		String pwd=instance.redisService.hget(Constants.SYS_USER_CACHE+UserEnum.TASK_USER, "userPwd");
		if(userName.equals(name)&&password.equals(pwd)){
			return true;
		}
		return false;
	}
	
}
