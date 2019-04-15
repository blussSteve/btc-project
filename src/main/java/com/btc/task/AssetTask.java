package com.btc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.btc.service.AssetService;
import com.btc.util.Constants;
import com.btc.util.RedissonManager;

@Component
public class AssetTask {

	private final static Logger logger=LoggerFactory.getLogger(AssetTask.class);
	
	@Autowired
	private AssetService assetService;
	
    
	/**
	 * 系统自动计算收益任务
	 */
	@Scheduled(cron = "0 0 16 * * ?")
	public void countIncome(){
		try {
			if(RedissonManager.lock(Constants.ASSET_INCOME_TASK_KEY)){
				assetService.createUserIncomeVerifyTemp(-1, -1);
			}
		} catch (Exception e) {
			logger.error("{}",e);
		}finally{
			RedissonManager.unlock(Constants.ASSET_INCOME_TASK_KEY);
		}
	}
	/**
	 * 每天凌晨1点计算计息资产
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void countCanUseAsset(){
		try {
			if(RedissonManager.lock(Constants.ASSET_CAN_USE_COINSTASK_KEY)){
				assetService.countCanUseAsset();
			}
		} catch (Exception e) {
			logger.error("{}",e);
		}finally{
			RedissonManager.unlock(Constants.ASSET_CAN_USE_COINSTASK_KEY);
		}
	}
	
	
}
