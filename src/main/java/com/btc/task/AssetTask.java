package com.btc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.btc.service.AssetService;
import com.btc.util.Constants;
import com.btc.util.RedissonManager;

@Component
public class AssetTask {

	private final static Logger logger=LoggerFactory.getLogger(AssetTask.class);
	
	@Autowired
	private AssetService assetService;
	
    
//	@Scheduled(cron = "0 0 17 * * ?")
	public void countIncome(){
		try {
			if(RedissonManager.lock(Constants.ASSET_TASK_KEY)){
				assetService.createUserIncomeVerifyTemp(-1, -1);
			}
		} catch (Exception e) {
			logger.error("{}",e);
		}finally{
			RedissonManager.unlock(Constants.ASSET_TASK_KEY);
		}
		
		
	}
}
