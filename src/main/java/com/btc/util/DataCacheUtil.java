package com.btc.util;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
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
	
	
	
}
