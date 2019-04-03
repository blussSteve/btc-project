package com.btc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.btc.model.UserInfo;
import com.btc.service.RedisService;
import com.btc.util.Constants;

public class BaseController {
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 获取用户缓存
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfo(HttpServletRequest req){
		String token=(String)req.getAttribute(Constants.AUTH_TOKEN_NAME_DEFAULT);
		String str=redisService.get(Constants.USER_TOKEN_CACHE+token);
		if(StringUtils.isNotEmpty(str)){
			UserInfo vo=JSONObject.parseObject(str,UserInfo.class);
			return vo;
		}
		return null;
	}
	
	/**
	 * 获取用户缓存
	 * @param userId
	 * @return
	 */
	public long getUserId(HttpServletRequest req){
		String token=(String)req.getAttribute(Constants.AUTH_TOKEN_NAME_DEFAULT);
		String str=redisService.get(Constants.USER_TOKEN_CACHE+token);
		UserInfo vo=JSONObject.parseObject(str,UserInfo.class);
		return vo.getId();
	}
}
