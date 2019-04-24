package com.btc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.btc.global.enums.SysConfigEnum;
import com.btc.global.enums.UserEnum;
import com.btc.service.RedisService;
import com.btc.util.Constants;

@Component
@Order(4)
public class DataInit implements CommandLineRunner{
	
	@Autowired
	private RedisService redisService;
	@Override
	public void run(String... args) throws Exception {
		setUserDicCache();
		initUser();
	}
	
	/**
	 * 初始化缓存信息
	 */
	private void setUserDicCache(){
		
		if(!redisService.hexists(Constants.SYS_DIC_CACHE, SysConfigEnum.COUNT_INCOME_BEGIN_DATE.getKey())){
			redisService.hset(Constants.SYS_DIC_CACHE, SysConfigEnum.COUNT_INCOME_BEGIN_DATE.getKey(),SysConfigEnum.COUNT_INCOME_BEGIN_DATE.getValue());
		}
		if(!redisService.hexists(Constants.SYS_DIC_CACHE, SysConfigEnum.COUNT_INCOME_END_DATE.getKey())){
			redisService.hset(Constants.SYS_DIC_CACHE, SysConfigEnum.COUNT_INCOME_END_DATE.getKey(),SysConfigEnum.COUNT_INCOME_END_DATE.getValue());
		}
		if(!redisService.hexists(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TURN_OUT.getKey())){
			redisService.hset(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TURN_OUT.getKey(),SysConfigEnum.IS_OPEN_ASSET_TURN_OUT.getValue());
		}
		if(!redisService.hexists(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TRADE.getKey())){
			redisService.hset(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TRADE.getKey(),SysConfigEnum.IS_OPEN_ASSET_TRADE.getValue());
		}
	}
	
	private void initUser(){
		
		redisService.hset(Constants.SYS_USER_CACHE+UserEnum.TASK_USER, "userName", UserEnum.TASK_USER.getUserName());
		redisService.hset(Constants.SYS_USER_CACHE+UserEnum.TASK_USER, "userPwd", UserEnum.TASK_USER.getUserPwd());
		
	}
	
}
