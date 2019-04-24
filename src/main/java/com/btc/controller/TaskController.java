package com.btc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btc.global.enums.TaskTypeEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.service.AssetService;
import com.btc.util.Constants;
import com.btc.util.DataCacheUtil;
import com.btc.util.RedissonManager;

@RequestMapping("/task")
@RestController
public class TaskController {
	
	private final static Logger logger=LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private AssetService assetService;
	
	/**
	 * 系统自动计算收益任务
	 */
	@RequestMapping(value="/countIncome")
	public JsonResult countIncome(){
		try {
			if(RedissonManager.lock(Constants.ASSET_INCOME_TASK_KEY)){
				return assetService.createUserIncomeVerifyTemp(-1, -1);
			}
			return JsonResultHelp.buildFail(RspCodeEnum.$0005);
		} catch (Exception e) {
			logger.error("{}",e);
		}finally{
			RedissonManager.unlock(Constants.ASSET_INCOME_TASK_KEY);
		}
		return JsonResultHelp.buildSucc();
	}
	/**
	 * 每天凌晨1点计算计息资产
	 */
	@RequestMapping(value="/countCanUseAsset")
	public JsonResult countCanUseAsset(String datestr){
		try {
			if(RedissonManager.lock(Constants.ASSET_CAN_USE_COINSTASK_KEY)){
				Date date=new Date();
				if(!StringUtils.isEmpty(datestr)){
					date=new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
				}
				
				return assetService.countCanUseAsset(date);
			}
			return JsonResultHelp.buildFail(RspCodeEnum.$0005);
		} catch (Exception e) {
			logger.error("{}",e);
		}finally{
			RedissonManager.unlock(Constants.ASSET_CAN_USE_COINSTASK_KEY);
		}
		return JsonResultHelp.buildSucc();
	}
	
	/**
	 * 手动执行定时任务
	 * @param req
	 * @param type[1.计算收益 2.计算计息资产]
	 * @param userName
	 * @param pwd
	 * @param datestr
	 * @return
	 */
	@RequestMapping(value="/run")
	public JsonResult run(HttpServletRequest req, int type,String userName,String pwd,String datestr){
		
		if(StringUtils.isEmpty(userName)){
			return JsonResultHelp.buildFail(RspCodeEnum.FAIL,"用户名不能为空");
		}
		if(StringUtils.isEmpty(pwd)){
			return JsonResultHelp.buildFail(RspCodeEnum.FAIL,"密码不能为空");
		}
		if(!DataCacheUtil.checkTaskUserIsTrue(userName, pwd)){
			return JsonResultHelp.buildFail(RspCodeEnum.FAIL,"用户名或密码错误");
		}
		
		try {
			if(RedissonManager.lock(Constants.TASK_CACHE_KEY)){
				
				if(TaskTypeEnum.COUNT_INCOME.getType()==type){
					return assetService.createUserIncomeVerifyTemp(-1, -1);
				}
				if(TaskTypeEnum.COUNT_USE_CONINS.getType()==type){
					Date date=new Date();
					if(!StringUtils.isEmpty(datestr)){
						date=new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
					}
					return assetService.countCanUseAsset(date);
				}
				return JsonResultHelp.buildFail(RspCodeEnum.$0003);
			}
			return JsonResultHelp.buildFail(RspCodeEnum.$0005);
		} catch (Exception e) {
			logger.error("{}",e);
		}finally{
			RedissonManager.unlock(Constants.TASK_CACHE_KEY);
		}
		return JsonResultHelp.buildSucc();
		
	}
	
}
