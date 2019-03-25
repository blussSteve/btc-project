package com.btc.util;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.btc.pojo.UserInfo;
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
	public static UserInfo getUserInfo(long userId){
		String str=instance.redisService.hget(Constants.USER_CACHE+userId, "userInfo");
		 
		if(StringUtils.isNotEmpty(str)){
			UserInfo vo=JSONObject.parseObject(str,UserInfo.class);
			return vo;
		}
		return null;
	}
	
	
}
